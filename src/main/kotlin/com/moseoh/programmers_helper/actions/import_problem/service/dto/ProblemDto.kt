package com.moseoh.programmers_helper.actions.import_problem.service.dto

import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

data class ProblemDto(
    val title: String,
    val content: String,
    val testCases: List<Map<String, String>>
) {
    private val settings = ProgrammersHelperSettings.instance

    fun getDirectoryName(): String {
        val replaceChar = if (settings.useNameSpacing) "_" else ""
        val invalidChars = "[\\/:;*?\"<>|\\^\\[\\]\\-]".toRegex()
        val replacedTitle = this.title.replace(" ", replaceChar).replace(invalidChars, "")
        return if (replacedTitle[0].isDigit()) "_$replacedTitle" else replacedTitle
    }

    fun getClassName(): String {
        return if (settings.useFolder) "Solution"
        else getDirectoryName()
    }

    fun getFileName(): String {
        return getClassName() + settings.language.extension
    }
}