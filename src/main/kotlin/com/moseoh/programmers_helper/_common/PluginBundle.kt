package com.moseoh.programmers_helper._common

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier


object PluginBundle : DynamicPluginBundle("messages.messages") {
    @NotNull
    operator fun get(
        @NotNull @PropertyKey(resourceBundle = "messages.messages") key: String,
        vararg params: @NotNull Any?
    ): String {
        return this.getMessage(key, *params)
    }

    @NotNull
    fun lazy(
        @NotNull @PropertyKey(resourceBundle = "messages.messages") key: String,
        vararg params: @NotNull Any?
    ): Supplier<String> {
        return this.getLazyMessage(key, *params)
    }
}