package com.moseoh.programmers_helper.conversion.action.service

import org.junit.jupiter.api.Assertions.assertEquals

class KotlinConversionServiceTest {

    @org.junit.Test
    fun convert() {
        // given
        val kotlinConversionService = KotlinConversionService()

        // when
        val actual = kotlinConversionService.convert(code)

        // then
        assertEquals(expected, actual)
    }

    companion object {
        val code = """
            package _6주차.타겟넘버.kotlin
            
            import java.util.Arrays;
            
            fun main() {
                val numbers1 = intArrayOf(1, 1, 1, 1, 1)
                val target1 = 3
                val answer1 = 5
                val result1 = Solution().solution(numbers1, target1)
                println(result1)
                check(result1 == answer1) { "오답" }
            
                val numbers2 = intArrayOf(4, 1, 2, 1)
                val target2 = 4
                val answer2 = 2
                val result2 = Solution().solution(numbers2, target2)
                println(result2)
                check(result2 == answer2) { "오답" }
            }
            
            class Solution {
                fun solution(numbers: IntArray, target: Int): Int {
                    return numbers.fold(listOf(0)) { acc, i ->
                        acc.run {
                            map { it + i } + map { it - 1 }
                        }
                    }.count { it == target }
                }
            
                fun solution2(numbers: IntArray, target: Int): Int {
                    val prevList = mutableListOf(0)
                    for (i in numbers.indices) {
                        val list = mutableListOf<Int>()
                        prevList.forEach {
                            list.add(it + numbers[i])
                            list.add(it - numbers[i])
                        }
                        prevList.clear()
                        prevList.addAll(list)
                    }
                    return prevList.count { it == target }
                }
            
            }
        """.trimIndent()

        val expected = """
            import java.util.Arrays;
            
            class Solution {
                fun solution(numbers: IntArray, target: Int): Int {
                    return numbers.fold(listOf(0)) { acc, i ->
                        acc.run {
                            map { it + i } + map { it - 1 }
                        }
                    }.count { it == target }
                }
            
                fun solution2(numbers: IntArray, target: Int): Int {
                    val prevList = mutableListOf(0)
                    for (i in numbers.indices) {
                        val list = mutableListOf<Int>()
                        prevList.forEach {
                            list.add(it + numbers[i])
                            list.add(it - numbers[i])
                        }
                        prevList.clear()
                        prevList.addAll(list)
                    }
                    return prevList.count { it == target }
                }
            
            }
        """.trimIndent()
    }
}