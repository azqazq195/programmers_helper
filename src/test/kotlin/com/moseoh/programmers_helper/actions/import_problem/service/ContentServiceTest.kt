package com.moseoh.programmers_helper.actions.import_problem.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.TestData
import com.moseoh.programmers_helper.actions.import_problem.service.dto.JavaTemplateMapper
import com.moseoh.programmers_helper.actions.import_problem.service.dto.KotlinTemplateMapper
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class ContentServiceTest {
    private lateinit var javaTemplateMapper: JavaTemplateMapper
    private lateinit var kotlinTemplateMapper: KotlinTemplateMapper
    private lateinit var contentService: ContentService
    private val project = mockk<Project>()
    private val directory = mockk<VirtualFile>()

    @Test
    fun `get java primitive 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_java_returnPrimitive()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example;
                
                class n2배열자르기 {
                    public static void main(String[] args) {
                        int i1 = 3;
                        int result1 = 1;
                        int answer1 = new n2배열자르기().solution(i1);
                        PRINT_RESULT(1, result1, answer1);
                
                        int i2 = 12;
                        int result2 = 40;
                        int answer2 = new n2배열자르기().solution(i2);
                        PRINT_RESULT(2, result2, answer2);
                    }
                
                    public static void PRINT_RESULT(int index, int result, int answer) {
                        boolean correct = result == answer;
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
                        sb.append(correct ? "정답" : "오답").append("\n");
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n");
                        sb.append("\t- 기댓값: \t").append(result).append("\n");
                        if (correct) System.out.println(sb);
                        else throw new RuntimeException(sb.toString());
                    }
                
                    public int solution(int i) {
                        int answer = 0;
                        return answer;
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get java String 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_java_returnString()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example;
                
                class n2배열자르기 {
                    public static void main(String[] args) {
                        int i1 = 3;
                        String result1 = "str";
                        String answer1 = new n2배열자르기().solution(i1);
                        PRINT_RESULT(1, result1, answer1);
                
                        int i2 = 12;
                        String result2 = "str22";
                        String answer2 = new n2배열자르기().solution(i2);
                        PRINT_RESULT(2, result2, answer2);
                    }
                
                    public static void PRINT_RESULT(int index, String result, String answer) {
                        boolean correct = result.equals(answer);
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
                        sb.append(correct ? "정답" : "오답").append("\n");
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n");
                        sb.append("\t- 기댓값: \t").append(result).append("\n");
                        if (correct) System.out.println(sb);
                        else throw new RuntimeException(sb.toString());
                    }
                
                    public String solution(int i) {
                        String answer = "";
                        return answer;
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get java 배열 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_java_returnArray()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example;
                
                import java.util.Arrays;
                
                class n2배열자르기 {
                    public static void main(String[] args) {
                        int i1 = 3;
                        int[] result1 = new int[]{1, 2};
                        int[] answer1 = new n2배열자르기().solution(i1);
                        PRINT_RESULT(1, result1, answer1);
                
                        int i2 = 12;
                        int[] result2 = new int[]{10, 42};
                        int[] answer2 = new n2배열자르기().solution(i2);
                        PRINT_RESULT(2, result2, answer2);
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
                
                    public int[] solution(int i) {
                        int[] answer = {};
                        return answer;
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get java 2차원 배열 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Java
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_java_returnArrayArray()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example;
                
                import java.util.Arrays;
                
                class n2배열자르기 {
                    public static void main(String[] args) {
                        int i1 = 3;
                        int[][] result1 = new int[][]{{"str1", "str2"}, {"str3"}};
                        int[][] answer1 = new n2배열자르기().solution(i1);
                        PRINT_RESULT(1, result1, answer1);
                
                        int i2 = 12;
                        int[][] result2 = new int[][]{{"str1", "str2", "str3"}, {"str4"}};
                        int[][] answer2 = new n2배열자르기().solution(i2);
                        PRINT_RESULT(2, result2, answer2);
                    }
                
                    public static void PRINT_RESULT(int index, int[][] result, int[][] answer) {
                        boolean correct = Arrays.deepEquals(result, answer);
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
                        sb.append(correct ? "정답" : "오답").append("\n");
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n");
                        sb.append("\t- 기댓값: \t").append(result).append("\n");
                        if (correct) System.out.println(sb);
                        else throw new RuntimeException(sb.toString());
                    }
                
                    public int[][] solution(int i) {
                        int[][] answer = {};
                        return answer;
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get kotlin 일반 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Kotlin
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_kotlin_returnSingle()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example

                fun main() {
                    fun printResult(index: Int, result: Int, answer: Int) {
                        val correct = result == answer
                        val sb = StringBuilder()
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ")
                        sb.append(if (correct) "정답" else "오답").append("\n")
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n")
                        sb.append("\t- 기댓값: \t").append(result).append("\n")
                        if (correct) println(sb) else throw RuntimeException(sb.toString())
                    }

                    val i1 = 3
                    val result1 = 1
                    val answer1 = n2배열자르기().solution(i1)
                    printResult(1, result1, answer1)

                    val i2 = 12
                    val result2 = 40
                    val answer2 = n2배열자르기().solution(i2)
                    printResult(2, result2, answer2)
                }

                class n2배열자르기 {
                    fun solution(i: Int): Int {
                        var answer: Int = 0
                        return answer
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get kotlin 배열 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Kotlin
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_kotlin_returnArray()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example
                
                fun main() {
                    fun printResult(index: Int, result: IntArray, answer: IntArray) {
                        val correct = result.contentEquals(answer)
                        val sb = StringBuilder()
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ")
                        sb.append(if (correct) "정답" else "오답").append("\n")
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n")
                        sb.append("\t- 기댓값: \t").append(result).append("\n")
                        if (correct) println(sb) else throw RuntimeException(sb.toString())
                    }
                
                    val i1 = 3
                    val result1 = intArrayOf(1, 2)
                    val answer1 = n2배열자르기().solution(i1)
                    printResult(1, result1, answer1)
                
                    val i2 = 12
                    val result2 = intArrayOf(10, 42)
                    val answer2 = n2배열자르기().solution(i2)
                    printResult(2, result2, answer2)
                }
                
                class n2배열자르기 {
                    fun solution(i: Int): IntArray {
                        var answer: IntArray = intArrayOf()
                        return answer
                    }
                }
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `get kotlin 2차원 배열 타입 반환`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.language } returns Language.Kotlin
        every { ProgrammersHelperSettings.instance.useFolder } returns false
        every { ProgrammersHelperSettings.instance.useNameSpacing } returns false
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        every { project.basePath } returns "src"
        every { directory.path } returns "src/com/moseoh/example"

        javaTemplateMapper = JavaTemplateMapper(ProgrammersHelperSettings.instance)
        kotlinTemplateMapper = KotlinTemplateMapper(ProgrammersHelperSettings.instance)
        contentService = ContentService(ProgrammersHelperSettings.instance, javaTemplateMapper, kotlinTemplateMapper)
        val problem = TestData.problemDto_kotlin_returnArrayArray()

        // when
        val result = contentService.get(project, directory, problem)

        // then
        assertEquals(
            """
                package com.moseoh.example
                
                fun main() {
                    fun printResult(index: Int, result: Array<Array<String>>, answer: Array<Array<String>>) {
                        val correct = result.contentDeepEquals(answer)
                        val sb = StringBuilder()
                        sb.append("\n\n테스트 케이스 ").append(index).append(": ")
                        sb.append(if (correct) "정답" else "오답").append("\n")
                        sb.append("\t- 실행 결과: \t").append(answer).append("\n")
                        sb.append("\t- 기댓값: \t").append(result).append("\n")
                        if (correct) println(sb) else throw RuntimeException(sb.toString())
                    }
                
                    val i1 = 3
                    val result1 = arrayOf(arrayOf("str1", "str2"), arrayOf("str3"))
                    val answer1 = n2배열자르기().solution(i1)
                    printResult(1, result1, answer1)
                
                    val i2 = 12
                    val result2 = arrayOf(arrayOf("str1", "str2", "str3"), arrayOf("str4"))
                    val answer2 = n2배열자르기().solution(i2)
                    printResult(2, result2, answer2)
                }
                
                class n2배열자르기 {
                    fun solution(i: Int): Array<Array<String>> {
                        var answer: Array<Array<String>> = arrayOf(arrayOf())
                        return answer
                    }
                }
            """.trimIndent(),
            result
        )
    }
}