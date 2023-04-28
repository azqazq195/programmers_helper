package com.moseoh.programmers_helper.actions.copy_answer

import com.moseoh.programmers_helper.actions.copy_answer.service.JavaCopyAnswerService
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class JavaCopyAnswerServiceTest {

    @Test
    fun convert() {
        // given
        val javaCopyAnswerService = JavaCopyAnswerService()

        // when
        val actual = javaCopyAnswerService.convert(code)

        // then
        assertEquals(expected, actual)
    }

    companion object {
        val code = """
            package _6주차.타겟넘버.java;
            
            import java.util.Arrays;

            class Solution {
                int answer = 0;
            
                public static void main(String[] args) {
                    int[] numbers1 = new int[]{1, 1, 1, 1, 1};
                    int target1 = 3;
                    int answer1 = 5;
                    int result1 = new Solution().solution(numbers1, target1);
                    System.out.println(result1);
                    System.out.println(result1 == answer1 ? "정답" : "오답");
            
                    int[] numbers2 = new int[]{4, 1, 2, 1};
                    int target2 = 4;
                    int answer2 = 2;
                    int result2 = new Solution().solution(numbers2, target2);
                    System.out.println(result2);
                    System.out.println(result2 == answer2 ? "정답" : "오답");
                }
            
                public int solution(int[] numbers, int target) {
                    calc(numbers, target, 0, 0);
                    return answer;
                }
            
                public void calc(int[] numbers, int target, int index, int num) {
                    if (index == numbers.length) {
                        if (num == target) answer++;
                        return;
                    }
                    calc(numbers, target, index + 1, num + numbers[index]);
                    calc(numbers, target, index + 1, num - numbers[index]);
                }
            }
        """.trimIndent()

        val expected = """
            import java.util.Arrays;

            class Solution {
                int answer = 0;
            
                public int solution(int[] numbers, int target) {
                    calc(numbers, target, 0, 0);
                    return answer;
                }
            
                public void calc(int[] numbers, int target, int index, int num) {
                    if (index == numbers.length) {
                        if (num == target) answer++;
                        return;
                    }
                    calc(numbers, target, index + 1, num + numbers[index]);
                    calc(numbers, target, index + 1, num - numbers[index]);
                }
            }
        """.trimIndent()
    }
}