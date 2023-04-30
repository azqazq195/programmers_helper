package com.moseoh.programmers_helper.actions.import_problem.service

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.actions.import_problem.model.Problem
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import java.io.File
import java.io.IOException

@Service
class FileService {
    private val settings = ProgrammersHelperSettings.instance
//    private val kotlinContentService = service<KotlinContentService>()
//    private val javaContentService = service<JavaContentService>()

    fun createFile(project: Project, parentDirectory: VirtualFile, problem: Problem): File? {
        val directory = getOrCreateDirectory(parentDirectory, problem) ?: return null
        val resultFile = File(directory.path, getFileName(problem))
        val content = ""
//        val content = when (settings.language) {
//            Language.Kotlin -> kotlinContentService.get(project, directory, solutionDto)
//            Language.Java -> javaContentService.get(project, directory, solutionDto)
//        }.replace("\n", System.lineSeparator())
        resultFile.writeText(content)
        return resultFile
    }

    private fun getOrCreateDirectory(directory: VirtualFile, problem: Problem): VirtualFile? {
        return if (settings.useFolder) {
            val directoryName = getDirectoryName(problem)
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

    private fun getDirectoryName(problem: Problem): String {
        val replaceChar = if (ProgrammersHelperSettings.instance.useNameSpacing) "_" else ""
        val invalidChars = "[\\/:;*?\"<>|\\^\\[\\]\\-]".toRegex()
        val replacedTitle = problem.title.replace(" ", replaceChar).replace(invalidChars, "")
        return if (replacedTitle[0].isDigit()) "_$replacedTitle" else replacedTitle
    }

    private fun getClassName(problem: Problem): String {
        return if (ProgrammersHelperSettings.instance.useFolder) "Solution"
        else getDirectoryName(problem)
    }

    private fun getFileName(problem: Problem): String {
        return getClassName(problem) + ProgrammersHelperSettings.instance.language.extension
    }
}