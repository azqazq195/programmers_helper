package com.moseoh.programmers_helper.settings.ui

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.CheckBox
import com.intellij.ui.dsl.builder.*
import com.moseoh.programmers_helper._common.PluginBundle.get
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class LanguageConfigurable : Configurable {
    private val settings = ProgrammersHelperSettings.instance

    private val languageComboBox = ComboBox(Language.values())
    private val useClipboardCheckBox = CheckBox(get("useClipboardCheckBox"))
    private val useFolderCheckBox = CheckBox(get("useFolderCheckBox"))
    private val useNameSpacingCheckBox = CheckBox(get("useNameSpacingCheckBox"))
    private val useMainFunctionCheckbox = CheckBox(get("useMainFunctionCheckbox"))

    override fun createComponent(): JComponent {
        return panel {
            row("${get("language")}: ") {
                cell(languageComboBox).comment(get("languageDesc"))
            }
            row("${get("copyMode")}: ") {
                cell(useClipboardCheckBox).comment(get("copyModeDesc"))
            }
            row("${get("createMode")}: ") {
                cell(useFolderCheckBox).comment(get("createModeDesc"))
            }
            row("${get("naming")}: ") {
                cell(useNameSpacingCheckBox).comment(get("namingDesc"))
            }
            row("${get("mainFunction")}: ") {
                cell(useMainFunctionCheckbox).comment(get("mainFunctionDesc"))
            }
            row {
                link("${get("github")} ") { BrowserUtil.browse("https://github.com/azqazq195/programmers_helper") }
            }
        }
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return get("displayName")
    }

    override fun isModified(): Boolean {
        return settings.language != languageComboBox.selectedItem ||
                settings.useClipboard != useClipboardCheckBox.isSelected ||
                settings.useFolder != useFolderCheckBox.isSelected ||
                settings.useNameSpacing != useNameSpacingCheckBox.isSelected ||
                settings.useMainFunction != useMainFunctionCheckbox.isSelected
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val selectedLanguage = languageComboBox.selectedItem as Language

        settings.language = selectedLanguage
        settings.useClipboard = useClipboardCheckBox.isSelected
        settings.useFolder = useFolderCheckBox.isSelected
        settings.useNameSpacing = useNameSpacingCheckBox.isSelected
        settings.useMainFunction = useMainFunctionCheckbox.isSelected
    }

    override fun reset() {
        languageComboBox.selectedItem = settings.language
        useClipboardCheckBox.isSelected = settings.useClipboard
        useFolderCheckBox.isSelected = settings.useFolder
        useNameSpacingCheckBox.isSelected = settings.useNameSpacing
        useMainFunctionCheckbox.isSelected = settings.useMainFunction
    }
}