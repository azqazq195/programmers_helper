package com.moseoh.programmers_helper.settings.ui

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.CheckBox
import com.intellij.ui.dsl.builder.*
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class LanguageConfigurable : Configurable {
    private val settings = ProgrammersHelperSettings.instance

    private val languageComboBox = ComboBox(Language.values())
    private val useClipboardCheckBox = CheckBox("클립보드 사용")
    private val useFolderCheckBox = CheckBox("폴더 생성")
    private val useNameSpacingCheckBox = CheckBox("띄어쓰기 구분")
    private val useMainFunctionCheckbox = CheckBox("사용")

    override fun createComponent(): JComponent {
        return panel {
            row("사용 언어:") {
                cell(languageComboBox).comment("지원 언어<br> - Java<br> - Kotlin")
            }
            row("복사 모드:") {
                cell(useClipboardCheckBox).comment("url 입력창 스킵하고 클립보드에 복사되어있는 값으로 대체합니다.")
            }
            row("생성 모드:") {
                cell(useFolderCheckBox).comment("폴더[문제_제목] > 파일[Solution]<br>파일[문제_제목]")
            }
            row("폴더 및 파일 이름:") {
                cell(useNameSpacingCheckBox).comment("문제_제목<br>문제제목")
            }
            row("main 함수 생성:") {
                cell(useMainFunctionCheckbox).comment("실행 함수를 함께 생성합니다.")
            }
            row {
                link("Github") { BrowserUtil.browse("https://github.com/azqazq195/programmers_helper") }
            }
        }
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Programmers Helper"
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