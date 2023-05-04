package com.moseoh.programmers_helper.actions.import_problem.service

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.actions.import_problem.service.dto.ProblemDto
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import java.io.File
import java.io.IOException

@Service
class FileService {
    private val settings = ProgrammersHelperSettings.state
    private val contentService = service<ContentService>()

    fun createFile(project: Project, parentDirectory: VirtualFile, problemDto: ProblemDto): File? {
        val directory = getOrCreateDirectory(parentDirectory, problemDto) ?: return null
        val resultFile = File(directory.path, problemDto.getFileName())
        val content = contentService.get(project, directory, problemDto)
        resultFile.writeText(content)
        return resultFile
    }

    private fun getOrCreateDirectory(directory: VirtualFile, problemDto: ProblemDto): VirtualFile? {
        return if (settings.useFolder) {
            val directoryName = problemDto.getDirectoryName()
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