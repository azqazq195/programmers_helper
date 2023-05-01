package com.moseoh.programmers_helper

import com.moseoh.programmers_helper.actions.import_problem.service.dto.ProblemDto

class TestData {
    companion object {
        fun problemDto_kotlin_returnSingle(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        fun solution(i: Int): Int {
                            var answer: Int = 0
                            return answer
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "1",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "40",
                    ),
                )
            )
        }

        fun problemDto_kotlin_returnArray(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        fun solution(i: Int): IntArray {
                            var answer: IntArray = intArrayOf()
                            return answer
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "[1, 2]",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "[10, 42]",
                    ),
                )
            )
        }

        fun problemDto_kotlin_returnArrayArray(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        fun solution(i: Int): Array<Array<String>> {
                            var answer: Array<Array<String>> = arrayOf(arrayOf())
                            return answer
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "[[\"str1\", \"str2\"], [\"str3\"]]",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "[[\"str1\", \"str2\", \"str3\"], [\"str4\"]]",
                    ),
                )
            )
        }

        fun problemDto_java_returnPrimitive(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        public int solution(int i) {
                            int answer = 0;
                            return answer;
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "1",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "40",
                    ),
                )
            )
        }

        fun problemDto_java_returnString(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        public String solution(int i) {
                            String answer = "";
                            return answer;
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "str",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "str22",
                    ),
                )
            )
        }

        fun problemDto_java_returnArray(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        public int[] solution(int i) {
                            int[] answer = {};
                            return answer;
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "[1, 2]",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "[10, 42]",
                    ),
                )
            )
        }

        fun problemDto_java_returnArrayArray(): ProblemDto {
            return ProblemDto(
                title = "n^2 배열 자르기",
                content = """
                    class Solution {
                        public int[][] solution(int i) {
                            int[][] answer = {};
                            return answer;
                        }
                    }
                """.trimIndent(),
                testCases = listOf(
                    mapOf(
                        "i" to "3",
                        "result" to "[[\"str1\", \"str2\"], [\"str3\"]]",
                    ),
                    mapOf(
                        "i" to "12",
                        "result" to "[[\"str1\", \"str2\", \"str3\"], [\"str4\"]]",
                    ),
                )
            )
        }
    }
}
