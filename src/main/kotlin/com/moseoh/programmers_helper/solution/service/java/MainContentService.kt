package com.moseoh.programmers_helper.solution.service.java

import com.moseoh.programmers_helper.common.Utils
import com.moseoh.programmers_helper.solution.model.ReturnType
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.model.dto.TestCaseDto

class MainContentService {
    companion object {
        val MAIN_TEMPLATE = """
            public static void main(String[] args) {
            ${'$'}{mainContent}
            }
        """.trimIndent()

        val MAIN_CONTENT_TEMPLATE = """
            ${'$'}{values}
            ${'$'}{println}
            ${'$'}{assert}
        """.trimIndent()

        val VALUE_TEMPLATE = """
            ${'$'}{valueType} ${'$'}{valueName} = ${'$'}{value};
        """.trimIndent()

        val PRINTLN_TEMPLATE = """
            System.out.println(${'$'}{result});
        """.trimIndent()

        val ASSERTION_TEMPLATE = """
            System.out.println(${'$'}{comparison} ? "정답" : "오답");
        """.trimIndent()
    }

    fun get(solutionDto: SolutionDto): String {
        val values = hashMapOf<String, String>()
        values["${'$'}{mainContent}"] = mainContent(solutionDto)
        return Utils.convert(MAIN_TEMPLATE, values)
    }

    private fun mainContent(solutionDto: SolutionDto): String {
        return solutionDto.testCaseDtos.mapIndexed { index, testCase ->
            val values = hashMapOf<String, String>()
            values["${'$'}{values}"] = valueContents(index, solutionDto, testCase)
            values["${'$'}{println}"] = printlnContent(index, testCase)
            values["${'$'}{assert}"] = assertionContent(index, testCase)
            Utils.convert(MAIN_CONTENT_TEMPLATE, values).trimIndent().prependIndent("\t")
        }.joinToString("\n\n")
    }

    private fun valueContents(index: Int, solutionDto: SolutionDto, testCase: TestCaseDto): String {
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


    private fun printlnContent(index: Int, testCase: TestCaseDto): String {
        val value = when (testCase.resultType()) {
            ReturnType.Single -> "result${index + 1}"
            ReturnType.Array -> "Arrays.toString(result${index + 1})"
            ReturnType.Array2D -> "Arrays.deepToString(result${index + 1})"
        }
        val values = hashMapOf<String, String>()
        values["${'$'}{result}"] = value
        return Utils.convert(PRINTLN_TEMPLATE, values)
    }

    private fun assertionContent(index: Int, testCase: TestCaseDto): String {
        val num = index + 1
        val value = when (testCase.resultType()) {
            ReturnType.Single -> "result$num == answer$num"
            ReturnType.Array -> "Arrays.equals(result$num, answer$num)"
            ReturnType.Array2D -> "Arrays.deepEquals(result$num, answer$num)"
        }

        val values = hashMapOf<String, String>()
        values["${'$'}{comparison}"] = value
        return Utils.convert(ASSERTION_TEMPLATE, values)
    }

    private fun valueType(value: Any): String = when (value) {
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
        is Int, is Long, is Float, is Double -> value.toString()
        is Array<*> -> {
            val sb = StringBuilder()
            sb.append("new ${valueType(value)}")
            sb.append(value.joinToString(prefix = "{", postfix = "}") { value(it!!) })
            sb.toString()
        }

        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${value.javaClass}")
    }
}