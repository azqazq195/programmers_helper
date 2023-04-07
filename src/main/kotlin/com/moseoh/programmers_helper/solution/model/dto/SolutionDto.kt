package com.moseoh.programmers_helper.solution.model.dto

import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.ReturnType
import com.moseoh.programmers_helper.solution.model.Solution

data class SolutionDto(
    val directoryName: String,
    val fileName: String,
    val className: String,
    val classContent: String,
    val testCaseDtos: Array<TestCaseDto>,
) {
    fun isImportArrays(): Boolean = testCaseDtos.any {
        it.resultType() == ReturnType.Array || it.resultType() == ReturnType.Array2D
    }

    companion object {
        fun of(solution: Solution): SolutionDto {
            fun getDirectoryName(solution: Solution): String {
                val replaceChar = if (ProgrammersHelperSettings.instance.useNameSpacing) "_" else ""
                val invalidChars = "[\\/:;*?\"<>|\\^\\[\\]]".toRegex()
                val replacedTitle = solution.title.replace(" ", replaceChar).replace(invalidChars, "")
                return if (replacedTitle[0].isDigit()) "_$replacedTitle" else replacedTitle
            }

            fun getClassName(solution: Solution): String {
                return if (ProgrammersHelperSettings.instance.useFolder) "Solution"
                else getDirectoryName(solution)
            }

            fun getFileName(solution: Solution): String {
                return getClassName(solution) + ProgrammersHelperSettings.instance.language.extension
            }


            fun getClassContent(solution: Solution): String {
                return Regex("""class\s+(\w+)\s*""").replace(solution.content) { "class ${getClassName(solution)}" }
            }

            return SolutionDto(
                directoryName = getDirectoryName(solution),
                fileName = getFileName(solution),
                className = getClassName(solution),
                classContent = getClassContent(solution),
                testCaseDtos = solution.testCases.map { TestCaseDto.of(it) }.toTypedArray()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SolutionDto

        if (directoryName != other.directoryName) return false
        if (fileName != other.fileName) return false
        if (className != other.className) return false
        if (classContent != other.classContent) return false
        return testCaseDtos.contentEquals(other.testCaseDtos)
    }

    override fun hashCode(): Int {
        var result = directoryName.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + className.hashCode()
        result = 31 * result + classContent.hashCode()
        result = 31 * result + testCaseDtos.contentHashCode()
        return result
    }
}