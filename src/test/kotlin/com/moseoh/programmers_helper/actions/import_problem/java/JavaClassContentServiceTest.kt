package com.moseoh.programmers_helper.actions.import_problem.java

import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaClassContentService
import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaMainContentService
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


class JavaClassContentServiceTest {
    private lateinit var classContentService: JavaClassContentService
    private val solutionDto = mockk<SolutionDto>()
    private val mainContentService = mockk<JavaMainContentService>()
    private val mainContent = """
        public static void main(String[] args) {
            내용
        }
    """.trimIndent()
    private val classContent = """
        class Solution{
            public void solution() {
                내용
            }
        }
    """.trimIndent()

    @Before
    fun setUp() {
        every { mainContentService.get(any()) } returns mainContent
        every { solutionDto.classContent } returns classContent
    }

    @Test
    fun `get 메인함수 포함`() {
        // given
        mockkObject(ProgrammersHelperSettings.Companion)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        classContentService = JavaClassContentService(ProgrammersHelperSettings.instance, mainContentService)

        // when
        val classContent = classContentService.get(solutionDto)

        // then
        assertEquals(
            """
            class Solution{
            	public static void main(String[] args) {
            	    내용
            	}
                public void solution() {
                    내용
                }
            }
        """.trimIndent(), classContent
        )
    }

    @Test
    fun `get 메인함수 미포함`() {
        // given
        mockkObject(ProgrammersHelperSettings.Companion)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.useMainFunction } returns false
        classContentService = JavaClassContentService(ProgrammersHelperSettings.instance, mainContentService)

        // when
        val classContent = classContentService.get(solutionDto)

        // then
        assertEquals(
            """
            class Solution{
                public void solution() {
                    내용
                }
            }
        """.trimIndent(), classContent
        )
    }
}