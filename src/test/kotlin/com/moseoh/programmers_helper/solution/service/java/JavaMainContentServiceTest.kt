package com.moseoh.programmers_helper.solution.service.java

import com.moseoh.programmers_helper.TestData
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


class JavaMainContentServiceTest {
    private lateinit var mainContentService: JavaMainContentService

    @Before
    fun setUp() {
        mainContentService = JavaMainContentService()
    }

    @Test
    fun get() {
        // given
        val solutionDto = TestData.solutionDto()

        // when
        val content = mainContentService.get(solutionDto)

        // then
        assertEquals(
            """
                public static void main(String[] args) {
                    int number1 = 2;
                    long ll1 = 2L;
                    String string1 = "string";
                    int[] intArray1 = new int[]{1, 2, 3};
                    String[] array1 = new String[]{"a", "b", "c"};
                    int[][] arrayIntArray1 = new int[][]{new int[]{1, 2}, new int[]{3, 4}};
                    int answer1 = 1;
                    int result1 = new Solution().solution(number1, ll1, string1, intArray1, array1, arrayIntArray1);
                    System.out.printf(
                            "테스트 케이스 1: %s\n" +
                                    "\t- 실행 결과: %s\n" +
                                    "\t- 기댓값: %s\n\n",
                            result1 == answer1 ? "정답" : "**오답**", result1, answer1
                    );

                    int number2 = 5;
                    long ll2 = 5L;
                    String string2 = "example";
                    int[] intArray2 = new int[]{5, 6, 8};
                    String[] array2 = new String[]{"E", "d", "A"};
                    int[][] arrayIntArray2 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                    int[] answer2 = new int[]{-1};
                    int[] result2 = new Solution().solution(number2, ll2, string2, intArray2, array2, arrayIntArray2);
                    System.out.printf(
                            "테스트 케이스 2: %s\n" +
                                    "\t- 실행 결과: %s\n" +
                                    "\t- 기댓값: %s\n\n",
                            Arrays.equals(result2, answer2) ? "정답" : "**오답**", Arrays.toString(result2), Arrays.toString(answer2)
                    );

                    int number3 = 5;
                    long ll3 = 5L;
                    String string3 = "example";
                    int[] intArray3 = new int[]{5, 6, 8};
                    String[] array3 = new String[]{"E", "d", "A"};
                    int[][] arrayIntArray3 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                    int[][] answer3 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                    int[][] result3 = new Solution().solution(number3, ll3, string3, intArray3, array3, arrayIntArray3);
                    System.out.printf(
                            "테스트 케이스 3: %s\n" +
                                    "\t- 실행 결과: %s\n" +
                                    "\t- 기댓값: %s\n\n",
                            Arrays.deepEquals(result3, answer3) ? "정답" : "**오답**", Arrays.deepToString(result3), Arrays.deepToString(answer3)
                    );

                    int number4 = 5;
                    long ll4 = 5L;
                    String string4 = "example";
                    int[] intArray4 = new int[]{5, 6, 8};
                    String[] array4 = new String[]{"E", "d", "A"};
                    int[][] arrayIntArray4 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                    String answer4 = "answer";
                    String result4 = new Solution().solution(number4, ll4, string4, intArray4, array4, arrayIntArray4);
                    System.out.printf(
                            "테스트 케이스 4: %s\n" +
                                    "\t- 실행 결과: %s\n" +
                                    "\t- 기댓값: %s\n\n",
                            result4.equals(answer4) ? "정답" : "**오답**", result4, answer4
                    );
                }
            """.trimIndent(), content
        )
    }

}