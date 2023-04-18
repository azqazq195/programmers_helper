package com.moseoh.programmers_helper.solution.service.kotlin

import com.moseoh.programmers_helper.TestData
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class KotlinMainContentServiceTest {

    private lateinit var mainContentService: KotlinMainContentService

    @Before
    fun setUp() {
        mainContentService = KotlinMainContentService()
    }

    @Test
    fun get() {
        // given
        val solutionDto = TestData.solutionDto()

        // when
        val content = mainContentService.get(solutionDto)

        assertEquals(
            """
                fun main() {
                    val number1 = 2
                    val ll1 = 2L
                    val string1 = "string"
                    val intArray1 = intArrayOf(1, 2, 3)
                    val array1 = arrayOf("a", "b", "c")
                    val arrayIntArray1 = arrayOf(intArrayOf(1, 2), intArrayOf(3, 4))
                    val answer1 = 1
                    val result1 = Solution().solution(number1, ll1, string1, intArray1, array1, arrayIntArray1)
                    check(result1 == answer1) {
                        "\n\n테스트 케이스 1\n" +
                                "\t- 실행 결과: ${'$'}result1\n" +
                                "\t- 기댓값: ${'$'}answer1\n"
                    }

                    val number2 = 5
                    val ll2 = 5L
                    val string2 = "example"
                    val intArray2 = intArrayOf(5, 6, 8)
                    val array2 = arrayOf("E", "d", "A")
                    val arrayIntArray2 = arrayOf(intArrayOf(11, 55), intArrayOf(4, 3))
                    val answer2 = intArrayOf(-1)
                    val result2 = Solution().solution(number2, ll2, string2, intArray2, array2, arrayIntArray2)
                    check(result2.contentEquals(answer2)) {
                        "\n\n테스트 케이스 2\n" +
                                "\t- 실행 결과: ${'$'}{result2.contentToString()}\n" +
                                "\t- 기댓값: ${'$'}{answer2.contentToString()}\n"
                    }

                    val number3 = 5
                    val ll3 = 5L
                    val string3 = "example"
                    val intArray3 = intArrayOf(5, 6, 8)
                    val array3 = arrayOf("E", "d", "A")
                    val arrayIntArray3 = arrayOf(intArrayOf(11, 55), intArrayOf(4, 3))
                    val answer3 = arrayOf(intArrayOf(11, 55), intArrayOf(4, 3))
                    val result3 = Solution().solution(number3, ll3, string3, intArray3, array3, arrayIntArray3)
                    check(result3.contentDeepEquals(answer3)) {
                        "\n\n테스트 케이스 3\n" +
                                "\t- 실행 결과: ${'$'}{result3.contentDeepToString()}\n" +
                                "\t- 기댓값: ${'$'}{answer3.contentDeepToString()}\n"
                    }
                }
            """.trimIndent(), content
        )
    }
}