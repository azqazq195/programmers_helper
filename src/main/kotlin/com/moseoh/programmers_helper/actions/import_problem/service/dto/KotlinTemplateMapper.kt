package com.moseoh.programmers_helper.actions.import_problem.service.dto

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.PluginBundle.get
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

@Service
class KotlinTemplateMapper(
    private val settings: ProgrammersHelperSettings.State = ProgrammersHelperSettings.state
) {
    fun toDto(projectPath: String, directoryPath: String, problemDto: ProblemDto): KotlinTemplateDto {
        return KotlinTemplateDto(
            packagePath = getPackagePath(projectPath, directoryPath),
            useMain = settings.useMainFunction,
            helpComment = getHelpComment(),
            className = problemDto.getClassName(),
            classContent = getClassContent(problemDto),
            testCaseDtos = getTestCaseDtos(problemDto),
        )
    }

    private fun getPackagePath(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return packagePath.replace('/', '.')
    }

    private fun getHelpComment(): String? {
        if (!settings.useHelpComment) return null
        return get("helpCommentKotlinFile")
    }

    private fun getClassContent(problemDto: ProblemDto): String {
        val content = problemDto.content
        val classDefinitionRegex = Regex("^class Solution \\{")
        val closingBraceRegex = Regex("\\}$")

        return content
            .replaceFirst(classDefinitionRegex, "")
            .replace(closingBraceRegex, "").trim()
    }

    private fun getTestCaseDtos(problemDto: ProblemDto): List<KotlinTemplateDto.TestCaseDto> {
        val valueTypeMap = extractParamTypes(problemDto.content).toMutableMap()
        val returnType = extractReturnType(problemDto.content)

        return problemDto.testCases.map { getTestCaseDto(it, valueTypeMap, returnType, problemDto.answerName) }.toList()
    }

    private fun getTestCaseDto(
        testCase: Map<String, String>,
        valueTypeMap: Map<String, String>,
        returnType: String,
        answerName: String
    ): KotlinTemplateDto.TestCaseDto {
        val values = mutableListOf<KotlinTemplateDto.Value>()
        testCase.forEach {
            if (it.key != answerName) {
                values.add(
                    KotlinTemplateDto.Value(
                        valueTypeMap[it.key]!!,
                        it.key,
                        getValue(valueTypeMap[it.key]!!, it.value)
                    )
                )
            }
        }

        return KotlinTemplateDto.TestCaseDto(
            values,
            KotlinTemplateDto.Value(
                returnType,
                answerName,
                getValue(returnType, testCase[answerName]!!)
            )
        )
    }

    private fun extractReturnType(content: String): String {
        val pattern = Regex("fun\\s+solution\\(.*\\)\\s*:\\s*(\\S+)\\s*\\{")
        val funcSignature = pattern.find(content)?.value ?: return "" // TODO Exception
        return funcSignature.substringAfterLast(":").substringBefore("{").trim()
    }

    private fun extractParamTypes(content: String): Map<String, String> {
        val valueTypeMap = mutableMapOf<String, String>()
        val pattern = Regex("fun\\s+solution\\((.*)\\)\\s*:\\s*\\S+\\s*\\{")
        val funcSignature = pattern.find(content)?.groupValues?.getOrNull(1) ?: return valueTypeMap // TODO Exception
        val params = funcSignature.split(", ")

        params.forEach { param ->
            val (name, type) = param.trim().split(":")
            valueTypeMap[name.trim()] = type.trim()
        }

        return valueTypeMap
    }

    fun getValue(type: String, value: String): String {
        if (type == "String") {
            return if (value.matches(Regex("^\".*\"$"))) {
                "\"${value.replace(Regex("^\"(.*)\"$"), "$1")}\""
            } else {
                "\"$value\""
            }
        }
        if (type == "Char") {
            return if (value.matches(Regex("^\'.*\'$"))) {
                "\'${value.replace(Regex("^\'(.*)\'$"), "$1")}\'"
            } else {
                "\'$value\'"
            }
        }
        if (type == "Long") return "${value}L"

        // primitive type array
        if (type.matches(Regex("^.*Array$"))) {
            val arrayType = type.replace(Regex("^(.*)Array$"), "$1")
            val items = value.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "").split(",")
            return "${arrayType.lowercase()}ArrayOf(${items.joinToString(", ") { getValue(arrayType, it.trim()) }})"
        }

        // string array
        if (type.matches(Regex("^Array<String>$"))) {
            val arrayType = type.replace(Regex("^Array<(.*)>$"), "$1")
            val items = value.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "").split(",")
            return "arrayOf(${items.joinToString(", ") { getValue(arrayType, it.trim()) }})"
        }

        // 2차원 배열
        if (type.matches(Regex("^Array<.*>$"))) {
            val arrayType = type.replace(Regex("^Array<(.*)>$"), "$1")
            val valueWithOutBrackets = value.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "")
            val matcher = Regex("(\\[.*?\\])").toPattern().matcher(valueWithOutBrackets)
            val matches = generateSequence { if (matcher.find()) matcher.group() else null }.toList()
            return "arrayOf(${matches.joinToString(", ") { getValue(arrayType, it) }})"
        }

        return value
    }

}