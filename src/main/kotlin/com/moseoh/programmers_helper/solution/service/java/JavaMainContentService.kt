package com.moseoh.programmers_helper.solution.service.java

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.common.Utils
import com.moseoh.programmers_helper.solution.model.ReturnType
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.model.dto.TestCaseDto
import com.moseoh.programmers_helper.solution.service.impl.IMainContentService

@Service
class JavaMainContentService : IMainContentService {
    companion object {
        val MAIN_TEMPLATE = """
            public static void main(String[] args) {
            ${'$'}{mainContent}
            }
        """.trimIndent()

        val MAIN_CONTENT_TEMPLATE = """
            ${'$'}{values}
            ${'$'}{assert}
        """.trimIndent()

        val VALUE_TEMPLATE = """
            ${'$'}{valueType} ${'$'}{valueName} = ${'$'}{value};
        """.trimIndent()

        val ASSERTION_TEMPLATE = """
            System.out.printf(
                    "테스트 케이스 ${'$'}{index}: %s\n" +
                            "\t- 실행 결과: %s\n" +
                            "\t- 기댓값: %s\n\n",
                    ${'$'}{comparison} ? "정답" : "**오답**", ${'$'}{result}, ${'$'}{answer}
            );
        """.trimIndent().trimMargin()
    }

    override fun get(solutionDto: SolutionDto): String {
        val values = hashMapOf<String, String>()
        values["${'$'}{mainContent}"] = mainContent(solutionDto)
        return Utils.convert(MAIN_TEMPLATE, values)
    }

    override fun mainContent(solutionDto: SolutionDto): String {
        return solutionDto.testCaseDtos.mapIndexed { index, testCase ->
            val values = hashMapOf<String, String>()
            values["${'$'}{values}"] = valueContents(index, solutionDto, testCase)
            values["${'$'}{assert}"] = assertionContent(index, testCase)
            Utils.convert(MAIN_CONTENT_TEMPLATE, values).trimIndent().prependIndent("    ")
        }.joinToString("\n\n")
    }

    override fun valueContents(index: Int, solutionDto: SolutionDto, testCase: TestCaseDto): String {
        fun valueContent(type: String, name: String, value: String): String {
            val values = hashMapOf<String, String>()
            values["${'$'}{valueType}"] = type
            values["${'$'}{valueName}"] = name
            values["${'$'}{value}"] = value
            return Utils.convert(VALUE_TEMPLATE, values)
        }

        val names = mutableListOf<String>()
        // properties
        return testCase.values.map { (key, value) ->
            val name = "$key${index + 1}"
            names.add(name)
            valueContent(valueType(value), name, value(value))
        }.toMutableList()
            .apply {
                // answer
                add(valueContent(valueType(testCase.answer), "answer${index + 1}", value(testCase.answer)))
            }
            .apply {
                // result
                add(
                    valueContent(
                        valueType(testCase.answer),
                        "result${index + 1}",
                        "new ${solutionDto.className}().solution(${names.joinToString()})"
                    )
                )
            }.joinToString("\n")
    }

    override fun assertionContent(index: Int, testCase: TestCaseDto): String {
        val num = index + 1
        val comparison = when (testCase.resultType()) {
            ReturnType.Single -> "result$num == answer$num"
            ReturnType.Array -> "Arrays.equals(result$num, answer$num)"
            ReturnType.Array2D -> "Arrays.deepEquals(result$num, answer$num)"
        }

        val values = hashMapOf<String, String>()
        values["${'$'}{comparison}"] = comparison
        values["${'$'}{index}"] = num.toString()
        values["${'$'}{result}"] = valueToString(index, testCase, "result")
        values["${'$'}{answer}"] = valueToString(index, testCase, "answer")
        return Utils.convert(ASSERTION_TEMPLATE, values)
    }

    private fun valueToString(index: Int, testCase: TestCaseDto, valueName: String): String {
        val num = index + 1
        return when (testCase.resultType()) {
            ReturnType.Single -> "$valueName$num"
            ReturnType.Array -> "Arrays.toString($valueName$num)"
            ReturnType.Array2D -> "Arrays.deepToString($valueName$num)"
        }
    }

    private fun valueType(value: Any): String = when (value) {
        is Boolean -> "boolean"
        is String -> "String"
        is Char -> "char"
        is Int -> "int"
        is Long -> "long"
        is Double -> "double"
        is Float -> "float"
        is Array<*> -> valueType(value[0]!!) + "[]"
        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }

    private fun value(value: Any): String = when (value) {
        is String -> "\"$value\""
        is Char -> "\'$value\'"
        is Int, is Float, is Double, is Boolean -> value.toString()
        is Long -> "${value}L"
        is Array<*> -> {
            val sb = StringBuilder()
            sb.append("new ${valueType(value)}")
            sb.append(value.joinToString(prefix = "{", postfix = "}") { value(it!!) })
            sb.toString()
        }

        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }
}