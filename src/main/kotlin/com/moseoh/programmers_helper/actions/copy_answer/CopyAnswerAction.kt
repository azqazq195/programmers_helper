package com.moseoh.programmers_helper.actions.copy_answer

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.components.service
import com.moseoh.programmers_helper._common.PluginBundle.get
import com.moseoh.programmers_helper._common.PluginBundle.lazy
import com.moseoh.programmers_helper.actions.copy_answer.service.JavaCopyAnswerService
import com.moseoh.programmers_helper.actions.copy_answer.service.KotlinCopyAnswerService
import com.moseoh.programmers_helper.settings.model.Language
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CopyAnswerAction : AnAction(
    lazy("copyAnswer"),
    lazy("copyAnswerDescription"),
    null
) {
    private val settings = ProgrammersHelperSettings.instance
    private val javaCopyAnswerService = service<JavaCopyAnswerService>()
    private val kotlinCopyAnswerService = service<KotlinCopyAnswerService>()

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
                get("copyAnswerNotificationTitle"),
                get("copyAnswerNotificationContent"),
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
        Language.Kotlin -> kotlinCopyAnswerService.convert(inputCode)
        Language.Java -> javaCopyAnswerService.convert(inputCode)
    }

}