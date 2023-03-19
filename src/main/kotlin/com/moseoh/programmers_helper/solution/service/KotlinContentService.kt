package com.moseoh.programmers_helper.solution.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.Solution
import com.moseoh.programmers_helper.solution.service.`interface`.ContentService

@Service
class KotlinContentService : ContentService {
    private val settings = ProgrammersHelperSettings.instance

    override fun getContent(project: Project, directory: VirtualFile, solution: Solution): String {
        var content = CONTENT.trimIndent()
        content = content.replace("${'$'}{package}", getPackage(project, directory) + "\n")
        content = content.replace("${'$'}{main}", getMain(solution))
        content = content.replace("${'$'}{class}", getClass(solution))
        return content + "\n"
    }

    override fun getPackage(project: Project, directory: VirtualFile): String {
        val projectPath = project.basePath!!
        val directoryPath = directory.path
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return packagePath.replace('/', '.')
    }

    override fun getMain(solution: Solution): String {
        if (!settings.useMainFunction) return ""
        val mainTemplate = "fun main() {\n\t${'$'}{mainContent}\n}"
        return mainTemplate.replace("${'$'}{mainContent}", getMainContent(solution))
    }

    override fun getMainContent(solution: Solution): String {
        val mainContentTemplate = """${'$'}{values}
    check(${solution.getClassName()}().solution(${'$'}{properties}) == ${'$'}{result}) { "오답" }"""
        var index = 1
        return solution.testCases.joinToString(separator = "\n\n\t") { (values, result) ->
            val valueNames = mutableListOf<String>()
            val convertedValues = values.map { (key, value) ->
                val valueName = "$key$index"
                valueNames.add(valueName)
                "val $valueName = ${convertValue(value)}"
            }.joinToString("\n\t")
            index++
            mainContentTemplate
                .replace("${'$'}{values}", convertedValues)
                .replace("${'$'}{properties}", valueNames.joinToString())
                .replace("${'$'}{result}", convertValue(result))
        }
    }

    override fun convertValue(value: Any): String = when (value) {
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
            value.joinToString(prefix = prefix, postfix = ")") { convertValue(it!!) }
        }

        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }


    override fun getClass(solution: Solution): String {
        return if (settings.useFolder) {
            solution.content
        } else {
            val regex = Regex("""class\s+(\w+)\s*""")
            regex.replace(solution.content) { _ ->
                "class ${solution.getClassName()} "
            }
        }
    }

    companion object {
        const val CONTENT = """
package ${'$'}{package}
${'$'}{main}
${'$'}{class}
"""
    }
}