package com.moseoh.programmers_helper.actions.import_problem

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper._common.PluginBundle
import com.moseoh.programmers_helper.actions.import_problem.model.Solution
import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.service.ParseService
import com.moseoh.programmers_helper.actions.import_problem.service.SolutionService
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor


class ImportProblemAction : AnAction(
    PluginBundle.lazy("importProblem"),
    PluginBundle.lazy("importProblemDescription"),
    null
) {
    private val parseService = service<ParseService>()
    private val solutionService = service<SolutionService>()

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val directory = getDirectory(event) ?: return
        val solution = getSolution(event) ?: return
        val solutionDto = SolutionDto.of(solution)
        val file = solutionService.createFile(project, directory, solutionDto) ?: return
        val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file)
        virtualFile?.let {
            FileEditorManager.getInstance(project).openTextEditor(OpenFileDescriptor(project, virtualFile), true)
        }
    }

    private fun getSolution(
        event: AnActionEvent,
        useClipboard: Boolean = ProgrammersHelperSettings.instance.useClipboard
    ): Solution? {
        val urlInput = urlInput(event, useClipboard) ?: return null
        val url = parseService.getUrl(urlInput)

        try {
            val document = Jsoup.connect(url).get()
            return parseService.parseHtml(document)
        } catch (e: HttpStatusException) {
            Messages.showErrorDialog("Url을 확인해 주세요.\nstatus code: ${e.statusCode}", "에러")
        } catch (e: Exception) {
            Messages.showErrorDialog(
                buildString {
                    append("해당 url 에서 정보를 읽을 수 없습니다.\n")
                    append("\t- 오래된 문제의 경우 양식이 달라 읽을 수 없음\n")
                    append("\t- 기출 문제의 경우 양식이 달라 읽을 수 없음\n")
                    append("이외의 경우 제보해 주세요.")
                },
                "에러"
            )
            throw e
        }
        return getSolution(event, false)
    }


    private fun urlInput(event: AnActionEvent, useClipboard: Boolean): String? {
        if (useClipboard) {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val urlInput = clipboard.getData(DataFlavor.stringFlavor) as String?
            if (isValid(urlInput)) return urlInput!!
            else Messages.showErrorDialog("clipboard에 올바르지 않는 데이터가 있습니다.", "에러")
        }

        return Messages.showInputDialog(
            event.project,
            "문제 URL 입력:",
            "문제 가져오기",
            null,
            "",
            object : InputValidatorEx {
                override fun checkInput(inputString: String): Boolean {
                    return isValid(inputString)
                }

                override fun canClose(inputString: String): Boolean {
                    return checkInput(inputString)
                }

                override fun getErrorText(inputString: String): String? {
                    if (inputString.isNotEmpty() && !isValid(inputString)) {
                        return "올바른 프로그래머스 url이 아닙니다."
                    }
                    return null
                }
            }
        )
    }

    private fun getDirectory(event: AnActionEvent): VirtualFile? {
        val directory = event.getData(PlatformDataKeys.VIRTUAL_FILE)
        if (directory == null || !directory.isDirectory) {
            Messages.showErrorDialog("디렉토리를 선택해 주세요.", "에러")
            return null
        }
        return directory
    }

    private fun isValid(inputString: String?): Boolean {
        if (inputString == null) return false
        if (inputString.isEmpty()) return false
        return inputString.startsWith("https://school.programmers.co.kr")
    }
}