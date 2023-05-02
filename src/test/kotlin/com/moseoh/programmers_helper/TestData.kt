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

        fun code_java(): String {
            return """
                package _8주차.주식가격.java;
    
                import java.util.Arrays;
    
                /**
                 * 주석
                 */
                class Solution {
                    public static void main(String[] args) {
                        // 주석
                        int[] prices1 = new int[]{1, 2, 3, 2, 3};
                        int[] answer1 = new int[]{4, 3, 1, 1, 0};
                        int[] result1 = new Solution().solution(prices1);
                        PRINT_RESULT(1, result1, answer1);
                    }
                    
                    public static void PRINT_RESULT(int index, int[] result, int[] answer) {
                        boolean correct = Arrays.equals(result, answer);
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
                        sb.append(correct ? "정답" : "오답").append("\n");
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n");
                        sb.append("\t- 기댓값: \t").append(result).append("\n");
                        if (correct) System.out.println(sb);
                        else throw new RuntimeException(sb.toString());
                    }
    
                    /**
                     * 주석
                     */
                    public int[] solution(int[] prices) {
                        int[] answer = new int[prices.length];
                        // 주석
                        for (int i = 0; i < answer.length - 1; i++) {
                            for (int j = i + 1; j < prices.length; j++) {
                                answer[i]++;
                                if (prices[i] > prices[j]) break;
                            }
                        }
                        /*
                            주석
                        */
                        return answer;
                    }
                }
        """.trimIndent()
        }

        fun code_kotlin(): String {
            return """
                    package _5주차.튜플
                    /**
                     * 주석
                     */
                    
                    import java.util.regex.Pattern
                    
                    /**
                     * 주석
                     */
                    fun main() {
                        val s2 = "{{1,2,3},{2,1},{1,2,4,3},{2}}"
                        val answer2 = intArrayOf(2, 1, 3, 4)
                        val result2 = Solution().solution(s2)
                        println(result2.contentToString())
                        check(result2.contentEquals(answer2)) { "오답" }
                    
                        /**
                         * 주석
                         */
                        val s5 = "{{4,2,3},{3},{2,3,4,1},{2,3}}"
                        val answer5 = intArrayOf(3, 2, 4, 1)
                        val result5 = Solution().solution(s5)
                        println(result5.contentToString())
                        check(result5.contentEquals(answer5)) { "오답" }
                    }
                    
                    class Solution {
                        // 주석
                        // 주석
                        // 주석
                        fun solution(s: String): IntArray {
                            return s.substring(2 until s.length - 2)
                                .split("},{")
                                .map { it.split(",").map { num -> num.toInt() } }
                                .toList().sortedBy { it.size }
                                .fold(setOf<Int>()) { acc, list -> acc.union(list) }.toIntArray()
                        }
                        /*
                            주석
                        */
                    }
        """.trimIndent()
        }
    }
}
