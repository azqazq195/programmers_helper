package com.moseoh.programmers_helper.solution.service.kotlin

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.common.Utils
import com.moseoh.programmers_helper.solution.model.ReturnType
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.model.dto.TestCaseDto
import com.moseoh.programmers_helper.solution.service.impl.IMainContentService

@Service
class KotlinMainContentService : IMainContentService {
    companion object {
        val MAIN_TEMPLATE = """
            fun main() {
            ${'$'}{mainContent}
            }
        """.trimIndent()

        val MAIN_CONTENT_TEMPLATE = """
            ${'$'}{values}
            ${'$'}{println}
            ${'$'}{assert}
        """.trimIndent()

        val VALUE_TEMPLATE = """
            val ${'$'}{valueName} = ${'$'}{value};
        """.trimIndent()

        val PRINTLN_TEMPLATE = """
            println(${'$'}{result});
        """.trimIndent()

        val ASSERTION_TEMPLATE = """
            check(${'$'}{comparison}) { "오답" };
        """.trimIndent()
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
            values["${'$'}{println}"] = printlnContent(index, testCase)
            values["${'$'}{assert}"] = assertionContent(index, testCase)
            Utils.convert(MAIN_CONTENT_TEMPLATE, values).trimIndent().prependIndent("\t")
        }.joinToString("\n\n")
    }

    override fun valueContents(index: Int, solutionDto: SolutionDto, testCase: TestCaseDto): String {
        fun valueContent(name: String, value: String): String {
            val values = hashMapOf<String, String>()
            values["${'$'}{valueName}"] = name
            values["${'$'}{value}"] = value
            return Utils.convert(VALUE_TEMPLATE, values)
        }

        val names = mutableListOf<String>()
        // properties
        return testCase.values.map { (key, value) ->
            val name = "$key${index + 1}"
            names.add(name)
            valueContent(name, value(value))
        }.toMutableList()
            .apply {
                // answer
                add(valueContent("answer${index + 1}", value(testCase.answer)))
            }
            .apply {
                // result
                add(
                    valueContent(
                        "result${index + 1}",
                        "${solutionDto.className}().solution(${names.joinToString()})"
                    )
                )
            }.joinToString("\n")
    }

    override fun printlnContent(index: Int, testCase: TestCaseDto): String {
        val num = index + 1
        val value = when (testCase.resultType()) {
            ReturnType.Single -> "result$num"
            ReturnType.Array -> "result$num.contentToString()"
            ReturnType.Array2D -> "result$num.contentDeepToString()"
        }
        val values = hashMapOf<String, String>()
        values["${'$'}{result}"] = value
        return Utils.convert(PRINTLN_TEMPLATE, values)
    }

    override fun assertionContent(index: Int, testCase: TestCaseDto): String {
        val num = index + 1
        val value = when (testCase.resultType()) {
            ReturnType.Single -> "result$num == answer$num"
            ReturnType.Array -> "result$num.contentEquals(answer$num))"
            ReturnType.Array2D -> "result$num.contentDeepEquals(answer$num))"
        }

        val values = hashMapOf<String, String>()
        values["${'$'}{comparison}"] = value
        return Utils.convert(ASSERTION_TEMPLATE, values)
    }

    private fun value(value: Any): String = when (value) {
        is String -> "\"$value\""
        is Char -> "\'$value\'"
        is Int, is Float, is Double -> value.toString()
        is Long -> "${value}L"
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
}