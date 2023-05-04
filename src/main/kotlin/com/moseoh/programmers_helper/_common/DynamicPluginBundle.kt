package com.moseoh.programmers_helper._common

import com.intellij.AbstractBundle
import com.intellij.lang.LangBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method
import java.util.*

/**
 * Abstract class to support loading localized messages which match the installed language pack.
 */
abstract class DynamicPluginBundle(@NotNull pathToBundle: @NonNls String) :
    AbstractBundle(pathToBundle) {
    override fun findBundle(
        @NotNull pathToBundle: @NonNls String,
        @NotNull loader: ClassLoader,
        control: @NotNull ResourceBundle.Control
    ): ResourceBundle {
        val base = super.findBundle(pathToBundle, loader, control)
        val ideLocale = LangBundle.getLocale()
        if (ideLocale != Locale.ENGLISH) {
            // load your bundle from baseName_<language>.properties, e.g. "baseName_zh.properties"
            val localizedPath = pathToBundle + "_" + ideLocale.language
            val localeBundle = super.findBundle(
                localizedPath,
                DynamicPluginBundle::class.java.classLoader, control
            )
            if (base != localeBundle) {
                setParent(localeBundle, base)
                return localeBundle
            }
        }
        return base
    }

    companion object {
        /**
         * Borrows code from `com.intellij.DynamicBundle` to set the parent bundle using reflection.
         */
        private fun setParent(localeBundle: ResourceBundle, base: ResourceBundle) {
            try {
                val method: Method =
                    ResourceBundle::class.java.getDeclaredMethod("setParent", ResourceBundle::class.java)
                method.isAccessible = true
                MethodHandles.lookup().unreflect(method).bindTo(localeBundle).invoke(base)
            } catch (e: Throwable) {
                // ignored, better handle this in production code
            }
        }
    }
}