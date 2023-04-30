package com.moseoh.programmers_helper

import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.model.dto.TestCaseDto

class TestData {
    companion object {
        fun solutionDto_resultTypeInt(): SolutionDto {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
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
                )
            )
        }

        fun solutionDto_resultTypeLong(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public long solution(int n, int s) {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        1L
                    ),
                )
            )
        }

        fun solutionDto_resultTypeDouble(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public double solution(int n, int s) {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        12.6
                    ),
                )
            )
        }

        fun solutionDto_resultTypeString(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public String solution(int n, int s) {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        "String"
                    ),
                )
            )
        }

        fun solutionDto_resultTypeArray(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public String[] solution(int n, int s) {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        arrayOf("element1", "element2")
                    ),
                )
            )
        }

        fun solutionDto_resultTypeArray2D(): SolutionDto {
            return SolutionDto(
                "최고의집합",
                "Solution.java",
                "Solution",
                """
                    class Solution{
                        public int[][] solution(int n, int s) {
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
                            Pair("doubleArray", doubleArrayOf(0.1, 0.0, 2.3).toTypedArray()),
                            Pair("array", arrayOf("a", "b", "c")),
                            Pair(
                                "arrayIntArray",
                                arrayOf(
                                    intArrayOf(1, 2).toTypedArray(),
                                    intArrayOf(3, 4).toTypedArray()
                                )
                            )
                        ),
                        arrayOf(
                            intArrayOf(11, 55).toTypedArray(),
                            intArrayOf(4, 3).toTypedArray()
                        )
                    ),
                )
            )
        }
    }
}
