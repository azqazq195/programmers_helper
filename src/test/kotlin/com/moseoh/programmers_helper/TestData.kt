package com.moseoh.programmers_helper

import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.model.dto.TestCaseDto

class TestData {
    companion object {
        fun solutionDto(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public int[] solution(int n, int s) {
                            int[] answer = {};
                            return answer;
                        }
                    }
                """.trimIndent(),
                arrayOf(
                    TestCaseDto(
                        mapOf(
                            Pair("number", 2),
                            Pair("ll", 2L),
                            Pair("string", "string"),
                            Pair("intArray", intArrayOf(1, 2, 3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        1
                    ),
                    TestCaseDto(
                        mapOf(
                            Pair("number", 5),
                            Pair("ll", 5L),
                            Pair("string", "example"),
                            Pair("intArray", intArrayOf(5, 6, 8).toTypedArray()),
                            Pair("array", arrayOf("E", "d", "A")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(11, 55).toTypedArray(),
                                    intArrayOf(4, 3).toTypedArray()
                                )
                            )
                        ),
                        intArrayOf(-1).toTypedArray()
                    ),
                    TestCaseDto(
                        mapOf(
                            Pair("number", 5),
                            Pair("ll", 5L),
                            Pair("string", "example"),
                            Pair("intArray", intArrayOf(5, 6, 8).toTypedArray()),
                            Pair("array", arrayOf("E", "d", "A")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(11, 55).toTypedArray(),
                                    intArrayOf(4, 3).toTypedArray()
                                )
                            )
                        ),
                        arrayOf(
                            intArrayOf(11, 55).toTypedArray(),
                            intArrayOf(4, 3).toTypedArray()
                        )
                    )
                )
            )
        }
    }
}