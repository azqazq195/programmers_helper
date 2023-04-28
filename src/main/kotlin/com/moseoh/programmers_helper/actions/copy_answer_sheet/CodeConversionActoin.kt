package com.moseoh.programmers_helper.actions.copy_answer_sheet

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.components.service
import com.moseoh.programmers_helper.actions.copy_answer_sheet.service.JavaConversionService
import com.moseoh.programmers_helper.actions.copy_answer_sheet.service.KotlinConversionService
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CopyAnswerSheetAction : AnAction(
    "Copy Answer Sheet"
) {
    private val settings = ProgrammersHelperSettings.instance
    private val javaConversionService = service<JavaConversionService>()
    private val kotlinConversionService = service<KotlinConversionService>()

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
            val notification = Notification(
                "Programmers Helper.Notification",
                "클립보드 복사 완료.",
                "이제 프로그래머스 답안지에 붙여넣기 하세요.",
                NotificationType.INFORMATION
            )
            Notifications.Bus.notify(notification, project)

            // 2초 후 알람 사라지게 하기
            val scheduler = Executors.newSingleThreadScheduledExecutor()
            scheduler.schedule({ notification.expire() }, 2, TimeUnit.SECONDS)
            scheduler.shutdown()
        }
    }

    private fun convert(inputCode: String): String = when (settings.language) {
        Language.Kotlin -> kotlinConversionService.convert(inputCode)
        Language.Java -> javaConversionService.convert(inputCode)
    }

}