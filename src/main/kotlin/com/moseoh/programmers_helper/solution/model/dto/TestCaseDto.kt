package com.moseoh.programmers_helper.solution.model.dto

import com.moseoh.programmers_helper.solution.model.ReturnType
import com.moseoh.programmers_helper.solution.model.TestCase

data class TestCaseDto(
    val values: Map<String, Any>,
    val answer: Any,
) {
    companion object {
        fun of(testCase: TestCase): TestCaseDto {
            return TestCaseDto(
                values = testCase.values,
                answer = testCase.answer
            )
        }
    }

    fun resultType(): ReturnType = when (answer) {
        is String, is Char, is Int, is Long, is Double, is Float -> ReturnType.Single
        is Array<*> -> {
            if (answer[0] is Array<*>) ReturnType.Array2D
            else ReturnType.Array
        }

        else -> throw IllegalArgumentException("입출력 데이터를 파싱하지 못 했습니다. 추가요청 부탁드려요. ${answer.javaClass}")
    }
}
