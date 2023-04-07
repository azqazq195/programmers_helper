package com.moseoh.programmers_helper.solution.service.kotlin

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class KotlinContentServiceTest {
    private lateinit var contentService: KotlinContentService
    private val packageContentService = mockk<KotlinPackageContentService>()
    private val classContentService = mockk<KotlinClassContentService>()
    private val mainContentService = mockk<KotlinMainContentService>()
    private val project = mockk<Project>()
    private val directory = mockk<VirtualFile>()
    private val solutionDto = mockk<SolutionDto>()

    @Before
    fun setUp() {
        every { packageContentService.get(any(), any()) } returns "package 프로젝트 경로"
        every { classContentService.get(any()) } returns "class 클래스 내용"
        every { mainContentService.get(any()) } returns "main 메인 내용"
        every { project.basePath } returns String()
        every { directory.path } returns String()
    }

    @Test
    fun `get main 포함`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.useMainFunction } returns true
        contentService = KotlinContentService(
            ProgrammersHelperSettings.instance,
            packageContentService,
            mainContentService,
            classContentService
        )

        // when
        val content = contentService.get(project, directory, solutionDto)

        // then
        assertEquals(
            """
                package 프로젝트 경로
                
                main 메인 내용
                
                class 클래스 내용
        """.trimIndent(), content
        )
    }

    @Test
    fun `get main 미포함`() {
        // given
        mockkObject(ProgrammersHelperSettings)
        every { ProgrammersHelperSettings.instance } returns ProgrammersHelperSettings()
        every { ProgrammersHelperSettings.instance.useMainFunction } returns false
        contentService = KotlinContentService(
            ProgrammersHelperSettings.instance,
            packageContentService,
            mainContentService,
            classContentService
        )

        // when
        val content = contentService.get(project, directory, solutionDto)

        // then
        assertEquals(
            """
                package 프로젝트 경로
                
                class 클래스 내용
        """.trimIndent(), content
        )
    }
}