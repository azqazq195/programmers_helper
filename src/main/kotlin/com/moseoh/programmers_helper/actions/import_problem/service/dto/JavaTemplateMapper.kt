package com.moseoh.programmers_helper.actions.import_problem.service.dto

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.PluginBundle.get
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

@Service
class JavaTemplateMapper(
    private val settings: ProgrammersHelperSettings.State = ProgrammersHelperSettings.state
) {
    fun toDto(projectPath: String, directoryPath: String, problemDto: ProblemDto): JavaTemplateDto {
        return JavaTemplateDto(
            packagePath = getPackagePath(projectPath, directoryPath),
            useImportArray = useImportArray(problemDto),
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

    private fun useImportArray(problemDto: ProblemDto): Boolean {
        return extractReturnType(problemDto.content).contains("[]")
    }

    private fun getHelpComment(): String? {
        if (!settings.useHelpComment) return null
        return get("helpCommentJavaFile")
    }

    private fun getClassContent(problemDto: ProblemDto): String {
        val content = problemDto.content
        val classDefinitionRegex = Regex("^class Solution \\{")
        val closingBraceRegex = Regex("\\}$")

        return content
            .replaceFirst(classDefinitionRegex, "")
            .replace(closingBraceRegex, "").trim()
    }


    private fun getTestCaseDtos(problemDto: ProblemDto): List<JavaTemplateDto.TestCaseDto> {
        val valueTypeMap = extractParamTypes(problemDto.content).toMutableMap()
        val returnType = extractReturnType(problemDto.content)

        return problemDto.testCases.map { getTestCaseDto(it, valueTypeMap, returnType, problemDto.answerName) }.toList()
    }

    private fun getTestCaseDto(
        testCase: Map<String, String>,
        valueTypeMap: Map<String, String>,
        returnType: String,
        answerName: String,
    ): JavaTemplateDto.TestCaseDto {
        val values = mutableListOf<JavaTemplateDto.Value>()
        testCase.forEach {
            if (it.key != answerName) {
                values.add(
                    JavaTemplateDto.Value(
                        valueTypeMap[it.key]!!,
                        it.key,
                        getValue(valueTypeMap[it.key]!!, it.value)
                    )
                )
            }
        }

        return JavaTemplateDto.TestCaseDto(
            values,
            JavaTemplateDto.Value(
                returnType,
                answerName,
                getValue(returnType, testCase[answerName]!!)
            )
        )
    }

    private fun extractReturnType(content: String): String {
        val pattern = Regex(".* solution\\(.*\\).*")
        val funcSignature = pattern.find(content)?.value ?: return "" // TODO Exception
        return funcSignature.substringBefore(" solution(").substringAfter("public ").trim()
    }

    private fun extractParamTypes(content: String): Map<String, String> {
        val valueTypeMap = mutableMapOf<String, String>()
        val pattern = Regex(".* solution\\(.*\\).*")
        val funcSignature = pattern.find(content)?.value ?: return valueTypeMap // TODO Exception
        val params = funcSignature.substringAfter("(").substringBeforeLast(")").split(", ")

        params.forEach { param ->
            val (type, name) = param.trim().split(" ")
            valueTypeMap[name.trim()] = type.trim()
        }

        return valueTypeMap
    }

    fun getValue(type: String, value: String): String {
        // 2차원 배열
        if (type.contains("[][]")) {
            val arrayType = type.replace(Regex("^(.*)\\[\\]\\[\\]$"), "$1")
            val valueWithOutBrackets = value.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "")
            val matcher = Regex("(\\[.*?\\])").toPattern().matcher(valueWithOutBrackets)
            val matches = generateSequence { if (matcher.find()) matcher.group() else null }.toList()
            return "new ${arrayType}[][]{${
                matches.joinToString(", ") { array ->
                    val items = array.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "").split(",")
                    "{${items.joinToString(", ") { getValue(arrayType, it.trim()) }}}"
                }
            }}"
        }

        // 1차원 배열
        if (type.contains("[]")) {
            val arrayType = type.replace(Regex("^(.*)\\[\\]$"), "$1")
            val items = value.trim().replace(Regex("^\\["), "").replace(Regex("\\]$"), "").split(",")
            return "new ${arrayType}[]{${items.joinToString(", ") { getValue(arrayType, it.trim()) }}}"
        }

        if (type == "String") {
            return if (value.matches(Regex("^\".*\"$"))) {
                "\"${value.replace(Regex("^\"(.*)\"$"), "$1")}\""
            } else {
                "\"$value\""
            }
        }
        if (type == "char") {
            return if (value.matches(Regex("^\'.*\'$"))) {
                "\'${value.replace(Regex("^\'(.*)\'$"), "$1")}\'"
            } else {
                "\'$value\'"
            }
        }
        if (type == "long") return "${value}L"
        return value
    }
}