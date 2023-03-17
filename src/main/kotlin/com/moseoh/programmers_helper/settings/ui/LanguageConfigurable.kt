package com.moseoh.programmers_helper.settings.ui

import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.Messages
import com.intellij.ui.dsl.builder.*
import com.moseoh.programmers_helper.settings.model.Language
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class LanguageConfigurable : Configurable {
    private val languageComboBox = ComboBox(Language.values())

    override fun createComponent(): JComponent {
        return panel {
            row("사용 언어:") {
                cell(languageComboBox).gap(RightGap.SMALL)
            }
        }
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Programmers Helper"
    }

    override fun isModified(): Boolean {
        return ProgrammersHelperSettings.instance.selectedLanguage != languageComboBox.selectedItem
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val selectedLanguage = languageComboBox.selectedItem as Language
        if (selectedLanguage != Language.Kotlin) {
            Messages.showErrorDialog("현재 Kotlin 언어만 사용 가능합니다.", "에러")
        } else {
            ProgrammersHelperSettings.instance.selectedLanguage = selectedLanguage
        }
    }

    override fun reset() {
        languageComboBox.selectedItem = ProgrammersHelperSettings.instance.selectedLanguage
    }
}