package com.moseoh.programmers_helper.solution.service

import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import org.jsoup.Jsoup
import org.junit.Test

class ParseServiceTest {
    private lateinit var parseService: ParseService

    @Test
    fun getUrl() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        parseService = ParseService(ProgrammersHelperSettings.instance)

        // when
        val url = parseService.getUrl(urlInput)

        // then
        assertEquals(
            "$urlInput?language=${ProgrammersHelperSettings.instance.language.name.lowercase()}".trimIndent(),
            url
        )
    }

    @Test
    fun parseHtml_Java() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        parseService = ParseService(ProgrammersHelperSettings.instance)

        // when
        val solution = parseService.parseHtml(Jsoup.parse(javaDocument))

        // then
        println(solution)
    }

    @Test
    fun parseHtml_Kotlin() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Kotlin
        parseService = ParseService(ProgrammersHelperSettings.instance)

        // when
        val solution = parseService.parseHtml(Jsoup.parse(kotlinDocument))

        // then
        println(solution)
    }

    @Test
    fun parseParamType_Java() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        parseService = ParseService(ProgrammersHelperSettings.instance)

        // when
        val valueTypeMap = parseService.parseParamType(javaDocument)

        // then
        assertEquals(
            mapOf(
                "n" to "int",
                "left" to "long",
                "right" to "long"
            ),
            valueTypeMap
        )
    }

    @Test
    fun parseParamType_Kotlin() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Kotlin
        parseService = ParseService(ProgrammersHelperSettings.instance)

        // when
        val valueTypeMap = parseService.parseParamType(kotlinDocument)

        // then
        assertEquals(
            mapOf(
                "n" to "Int",
                "left" to "Long",
                "right" to "Long"
            ),
            valueTypeMap
        )
    }


    companion object {
        const val urlInput = "https://school.programmers.co.kr/learn/courses/30/lessons/87390"
        const val javaDocument = """
            <li class="nav-item algorithm-nav-link algorithm-title">n^2 배열 자르기</li>
            <textarea hidden id="code" name="code">class Solution {
                public int[] solution(int n, long left, long right) {
                    int[] answer = {};
                    return answer;
                }
            }</textarea>
            <h5>입출력 예</h5>
             <table class="table">
              <thead>
               <tr>
                <th>n</th>
                <th>left</th>
                <th>right</th>
                <th>result</th>
               </tr>
              </thead>
              <tbody>
               <tr>
                <td>3</td>
                <td>2</td>
                <td>5</td>
                <td><code>[3,2,2,3]</code></td>
               </tr>
               <tr>
                <td>4</td>
                <td>7</td>
                <td>14</td>
                <td><code>[4,3,3,3,4,4,4,4]</code></td>
               </tr>
              </tbody>
             </table>
             <hr>
        """
        const val kotlinDocument = """
            <li class="nav-item algorithm-nav-link algorithm-title">n^2 배열 자르기</li>
            <textarea hidden id="code" name="code">class Solution {
                fun solution(n: Int, left: Long, right: Long): IntArray {
                    return (left..right).map {
                        maxOf((it / n + 1).toInt(), (it % n + 1).toInt())
                    }.toIntArray()
                }
            }</textarea>
            <h5>입출력 예</h5>
             <table class="table">
              <thead>
               <tr>
                <th>n</th>
                <th>left</th>
                <th>right</th>
                <th>result</th>
               </tr>
              </thead>
              <tbody>
               <tr>
                <td>3</td>
                <td>2</td>
                <td>5</td>
                <td><code>[3,2,2,3]</code></td>
               </tr>
               <tr>
                <td>4</td>
                <td>7</td>
                <td>14</td>
                <td><code>[4,3,3,3,4,4,4,4]</code></td>
               </tr>
              </tbody>
             </table>
             <hr>
        """
    }
}