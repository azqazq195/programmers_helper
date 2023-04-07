package com.moseoh.programmers_helper.solution.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto

@Service
class KotlinContentService {
    private val settings = ProgrammersHelperSettings.instance

    fun getContent(project: Project, directory: VirtualFile, solution: SolutionDto): String {
        var content = CONTENT.trimIndent()
        content = content.replace("${'$'}{package}", getPackage(project.basePath!!, directory.path) + "\n")
        content = content.replace("${'$'}{main}", getMain(solution))
        content = content.replace("${'$'}{class}", solution.classContent)
        return content + "\n"
    }

    fun getPackage(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return packagePath.replace('/', '.')
    }

    fun getMain(solution: SolutionDto): String {
        if (!settings.useMainFunction) return ""
        val mainTemplate = "fun main() {\n\t${'$'}{mainContent}\n}"
        return mainTemplate.replace("${'$'}{mainContent}", getMainContent(solution))
    }

    fun getMainContent(solution: SolutionDto): String {
        val mainContentTemplate = """${'$'}{values}
    check(${solution.className}().solution(${'$'}{properties}) == ${'$'}{result}) { "오답" }"""
        var index = 1
        return solution.testCaseDtos.joinToString(separator = "\n\n\t") { (values, result) ->
            val valueNames = mutableListOf<String>()
            val convertedValues = values.map { (key, value) ->
                val valueName = "$key$index"
                valueNames.add(valueName)
                "val $valueName = ${value(value)}"
            }.joinToString("\n\t")
            index++
            mainContentTemplate
                .replace("${'$'}{values}", convertedValues)
                .replace("${'$'}{properties}", valueNames.joinToString())
                .replace("${'$'}{result}", value(result))
        }
    }

    fun value(value: Any): String = when (value) {
        is String -> "\"$value\""
        is Char -> "\'$value\'"
        is Int, is Long, is Float, is Double -> value.toString()
        is Array<*> -> {
            val prefix = when (value.firstOrNull()) {
                is Int -> "intArrayOf("
                is Long -> "longArrayOf("
                is Float -> "floatArrayOf("
                is Double -> "doubleArrayOf("
                is String -> "arrayOf("
                is Array<*> -> "arrayOf("
                else -> throw RuntimeException(value.toString())
            }
            value.joinToString(prefix = prefix, postfix = ")") { value(it!!) }
        }

        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }

    companion object {
        const val CONTENT = """
package ${'$'}{package}
${'$'}{main}
${'$'}{class}
"""
    }
}