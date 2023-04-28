package com.moseoh.programmers_helper.actions.import_problem.java

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaClassContentService
import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaContentService
import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaPackageContentService
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class JavaContentServiceTest {
    private lateinit var javaContentService: JavaContentService

    @Before
    fun setUp() {
        val packageContentService = mockk<JavaPackageContentService>()
        val classContentService = mockk<JavaClassContentService>()
        every { packageContentService.get(any(), any()) } returns "package 프로젝트 경로"
        every { classContentService.get(any()) } returns "class 클래스 내용"
        javaContentService = JavaContentService(packageContentService, classContentService)
    }

    @Test
    fun `get import 포함`() {
        // given
        val project = mockk<Project>()
        val directory = mockk<VirtualFile>()
        val solutionDto = mockk<SolutionDto>()
        every { project.basePath } returns String()
        every { directory.path } returns String()
        every { solutionDto.isImportArrays() } returns true

        // when
        val content = javaContentService.get(project, directory, solutionDto)

        // then
        assertEquals(
            """
            package 프로젝트 경로
            
            import java.util.Arrays;

            class 클래스 내용
        """.trimIndent(), content
        )
    }

    @Test
    fun `get import 미포함`() {
        // given
        val project = mockk<Project>()
        val directory = mockk<VirtualFile>()
        val solutionDto = mockk<SolutionDto>()
        every { project.basePath } returns String()
        every { directory.path } returns String()
        every { solutionDto.isImportArrays() } returns false

        // when
        val content = javaContentService.get(project, directory, solutionDto)

        // then
        assertEquals(
            """
            package 프로젝트 경로

            class 클래스 내용
        """.trimIndent(), content
        )
    }
}