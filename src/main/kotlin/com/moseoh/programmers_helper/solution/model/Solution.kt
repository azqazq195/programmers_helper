package com.moseoh.programmers_helper.solution.model

import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

data class Solution(
    val title: String,
    val content: String,
    val testCases: Array<TestCase>
) {
    fun getDirectoryName(): String {
        val replaceChar = if (ProgrammersHelperSettings.instance.useNameSpacing) "_" else ""
        val invalidChars = "[\\/:;*?\"<>|\\^\\[\\]]".toRegex()
        val replacedTitle = title.replace(" ", replaceChar).replace(invalidChars, "")
        return if (replacedTitle[0].isDigit()) "_$replacedTitle" else replacedTitle
    }

    fun getFileName(): String = getClassName() + ProgrammersHelperSettings.instance.language.extension

    fun getClassName(): String =
        if (ProgrammersHelperSettings.instance.useFolder) {
            "Solution"
        } else {
            getDirectoryName()
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Solution

        if (title != other.title) return false
        if (content != other.content) return false
        if (!testCases.contentEquals(other.testCases)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + testCases.contentHashCode()
        return result
    }
}
