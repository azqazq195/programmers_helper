package com.moseoh.programmers_helper.solution.service

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.service.java.ContentService
import java.io.File
import java.io.IOException

@Service
class SolutionService {
    private val settings = ProgrammersHelperSettings.instance
    private val kotlinContentService = service<KotlinContentService>()
    private val javaContentService = service<ContentService>()

    fun createFile(project: Project, parentDirectory: VirtualFile, solutionDto: SolutionDto): File? {
        val directory = getOrCreateDirectory(parentDirectory, solutionDto) ?: return null
        val resultFile = File(directory.path, solutionDto.fileName)
        val content = when (settings.language) {
            Language.Kotlin -> kotlinContentService.getContent(project, directory, solutionDto)
            Language.Java -> javaContentService.get(project, directory, solutionDto)
        }
        resultFile.writeText(content)
        return resultFile
    }

    private fun getOrCreateDirectory(directory: VirtualFile, solutionDto: SolutionDto): VirtualFile? {
        return if (settings.useFolder) {
            val directoryName = solutionDto.directoryName
            val existingDirectory = directory.findChild(directoryName)
            if (existingDirectory != null) {
                Messages.showErrorDialog("해당 폴더가 이미 존재합니다.\n폴더 이름: $directoryName", "에러")
                return null
            }

            WriteAction.compute<VirtualFile, IOException> { directory.createChildDirectory(this, directoryName) }
        } else {
            directory
        }
    }
}