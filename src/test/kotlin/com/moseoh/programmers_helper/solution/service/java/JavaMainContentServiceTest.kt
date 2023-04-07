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
                	System.out.println(result1);
                	System.out.println(result1 == answer1 ? "정답" : "오답");

                	int number2 = 5;
                	long ll2 = 5L;
                	String string2 = "example";
                	int[] intArray2 = new int[]{5, 6, 8};
                	String[] array2 = new String[]{"E", "d", "A"};
                	int[][] arrayIntArray2 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                	int[] answer2 = new int[]{-1};
                	int[] result2 = new Solution().solution(number2, ll2, string2, intArray2, array2, arrayIntArray2);
                	System.out.println(Arrays.toString(result2));
                	System.out.println(Arrays.equals(result2, answer2) ? "정답" : "오답");

                	int number3 = 5;
                	long ll3 = 5L;
                	String string3 = "example";
                	int[] intArray3 = new int[]{5, 6, 8};
                	String[] array3 = new String[]{"E", "d", "A"};
                	int[][] arrayIntArray3 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                	int[][] answer3 = new int[][]{new int[]{11, 55}, new int[]{4, 3}};
                	int[][] result3 = new Solution().solution(number3, ll3, string3, intArray3, array3, arrayIntArray3);
                	System.out.println(Arrays.deepToString(result3));
                	System.out.println(Arrays.deepEquals(result3, answer3) ? "정답" : "오답");
                }
            """.trimIndent(), content
        )
    }

}