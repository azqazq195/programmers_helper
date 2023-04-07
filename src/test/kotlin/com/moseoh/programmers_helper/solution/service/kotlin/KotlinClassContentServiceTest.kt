package com.moseoh.programmers_helper.solution.service.kotlin

import com.moseoh.programmers_helper.TestData
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


class KotlinClassContentServiceTest {
    private lateinit var classContentService: KotlinClassContentService

    @Before
    fun setUp() {
        classContentService = KotlinClassContentService()
    }


    @Test
    fun get() {
        // given
        val solutionDto = TestData.solutionDto()

        // when
        val content = classContentService.get(solutionDto)

        // then
        assertEquals(
            """
                class Solution{
                    public int[] solution(int n, int s) {
                        int[] answer = {};
                        return answer;
                    }
                }
            """.trimIndent(), content
        )
    }
}