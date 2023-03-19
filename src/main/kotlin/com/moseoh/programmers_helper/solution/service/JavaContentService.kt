package com.moseoh.programmers_helper.solution.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.Solution
import com.moseoh.programmers_helper.solution.service.`interface`.ContentService

@Service
class JavaContentService : ContentService {
    private val settings = ProgrammersHelperSettings.instance

    override fun getContent(project: Project, directory: VirtualFile, solution: Solution): String {
        var content = CONTENT.trimIndent()
        content = content.replace("${'$'}{package}", getPackage(project, directory) + "\n")
        content = content.replace("${'$'}{class}", getClass(solution))
        return content + "\n"
    }

    override fun getPackage(project: Project, directory: VirtualFile): String {
        val projectPath = project.basePath!!
        val directoryPath = directory.path
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return packagePath.replace('/', '.') + ";"
    }

    override fun getMain(solution: Solution): String {
        if (!settings.useMainFunction) return ""
        val mainTemplate = "public static void main(String[] args) {\n\t${'$'}{mainContent}\n}"
        return mainTemplate.replace("${'$'}{mainContent}", getMainContent(solution))
    }

    override fun getMainContent(solution: Solution): String {
        val mainContentTemplate = """${'$'}{values}
    assert new ${solution.getClassName()}().solution(${'$'}{properties}) == ${'$'}{result} : "실패";"""
        var index = 1
        return solution.testCases.joinToString(separator = "\n\n\t") { (values, result) ->
            val valueNames = mutableListOf<String>()
            val convertedValues = values.map { (key, value) ->
                val valueName = "$key$index"
                val convertedValue = convertValue(value)
                val valueType = getValueType(value)

                valueNames.add(valueName)
                "$valueType $valueName = $convertedValue;"
            }.joinToString("\n\t")
            index++
            mainContentTemplate
                .replace("${'$'}{values}", convertedValues)
                .replace("${'$'}{properties}", valueNames.joinToString())
                .replace("${'$'}{result}", convertValue(result))
        }
    }

    private fun getValueType(value: Any): String = when (value) {
        is String -> "String"
        is Char -> "char"
        is Int -> "int"
        is Long -> "long"
        is Double -> "double"
        is Float -> "float"
        is Array<*> -> getValueType(value[0]!!) + "[]"
        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }

    override fun convertValue(value: Any): String = when (value) {
        is String -> "\"$value\""
        is Char -> "\'$value\'"
        is Int, is Long, is Float, is Double -> value.toString()
        is Array<*> -> value.joinToString(prefix = "{", postfix = "}") { convertValue(it!!) }
        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }

    override fun getClass(solution: Solution): String {
        val classContent = if (settings.useFolder) {
            solution.content
        } else {
            val regex = Regex("""class\s+(\w+)\s*""")
            regex.replace(solution.content) { _ ->
                "class ${solution.getClassName()} "
            }
        }

        return if (settings.useMainFunction) {
            addMainToClass(classContent, getMain(solution))
        } else classContent
    }

    private fun addMainToClass(classContent: String, mainContent: String): String {
        val classLines = classContent.lines() as MutableList<String>
        val mainLines = mainContent.lines() as MutableList<String>

        for (mainLine in mainLines.asReversed()) {
            classLines.add(1, "\t$mainLine")
        }
        return classLines.joinToString(separator = "\n")
    }

    companion object {
        const val CONTENT = """
package ${'$'}{package}
${'$'}{class}
"""
    }
}