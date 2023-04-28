package com.moseoh.programmers_helper.actions.import_problem.service.kotlin

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.Utils
import com.moseoh.programmers_helper.actions.import_problem.model.ReturnType
import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.model.dto.TestCaseDto
import com.moseoh.programmers_helper.actions.import_problem.service.impl.IMainContentService

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
            ${'$'}{assert}
        """.trimIndent()

        val VALUE_TEMPLATE = """
            val ${'$'}{valueName} = ${'$'}{value}
        """.trimIndent()

        val ASSERTION_TEMPLATE = """
            check(${'$'}{comparison}) {
                "\n\n테스트 케이스 ${'$'}{index}\n" +
                        "\t- 실행 결과: ${'$'}{result}\n" +
                        "\t- 기댓값: ${'$'}{answer}\n"
            }
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

    override fun assertionContent(index: Int, testCase: TestCaseDto): String {
        val num = index + 1
        val comparison = when (testCase.resultType()) {
            ReturnType.Single -> "result$num == answer$num"
            ReturnType.Array -> "result$num.contentEquals(answer$num)"
            ReturnType.Array2D -> "result$num.contentDeepEquals(answer$num)"
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
            ReturnType.Single -> "${'$'}$valueName$num"
            ReturnType.Array -> "${'$'}{$valueName$num.contentToString()}"
            ReturnType.Array2D -> "${'$'}{$valueName$num.contentDeepToString()}"
        }
    }

    private fun value(value: Any): String = when (value) {
        is String -> "\"$value\""
        is Char -> "\'$value\'"
        is Int, is Float, is Double, is Boolean -> value.toString()
        is Long -> "${value}L"
        is Array<*> -> {
            val prefix = when (value.firstOrNull()) {
                is Boolean -> "booleanArrayOf("
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