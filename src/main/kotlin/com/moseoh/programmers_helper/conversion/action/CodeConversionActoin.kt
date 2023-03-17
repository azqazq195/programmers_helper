package com.moseoh.programmers_helper.conversion.action

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CodeConversionAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val editor = event.getData(CommonDataKeys.EDITOR)

        if (editor != null) {
            val document = editor.document
            val inputCode = document.text

            val extractedCode = convert(inputCode)

            // 결과를 클립보드에 넣기
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val stringSelection = StringSelection(extractedCode)
            clipboard.setContents(stringSelection, stringSelection)

            // 복사 완료 알람
            val notification = Notification("Programmers Helper.Notification", "클립보드 복사 완료.", "이제 프로그래머스 답안지에 붙여넣기 하세요.", NotificationType.INFORMATION)
            Notifications.Bus.notify(notification, project)

            // 2초 후 알람 사라지게 하기
            val scheduler = Executors.newSingleThreadScheduledExecutor()
            scheduler.schedule({ notification.expire() }, 2, TimeUnit.SECONDS)
            scheduler.shutdown()
        }
    }

    private fun convert(code: String): String {
        val lines = code.lines()
        val importLines = lines.filter { it.startsWith("import") }
        val classStartIndex = lines.indexOfFirst { it.startsWith("class Solution") }
        val classEndIndex = lines.indexOfLast { it == "}" }

        val classLines = lines.subList(classStartIndex, classEndIndex + 1)
        val extractedLines = importLines + listOf("") + classLines

        return extractedLines.joinToString(separator = "\n")
    }
}