package com.moseoh.programmers_helper.actions.copy_answer.service


import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.Utils.removeAll
import com.moseoh.programmers_helper._common.Utils.removeComment
import com.moseoh.programmers_helper._common.Utils.removeFunctionInBracket
import com.moseoh.programmers_helper.actions.copy_answer.service.impl.ICopyAnswerService
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

@Service
class KotlinCopyAnswerService(
    private val settings: ProgrammersHelperSettings.State = ProgrammersHelperSettings.state,
) : ICopyAnswerService {
    override fun convert(code: String): String {
        var lines = code.lines()

        // package 제거
        lines = lines.removeAll("^package.*".toRegex())

        // main 함수 제거
        if (settings.useMainFunction) {
            lines = lines.removeFunctionInBracket("fun main\\(.*\\).*".toRegex())
        }

        // 주석 제거
        if (!settings.allowCopyComment) {
            lines = lines.removeComment()
        }

        return lines.joinToString(separator = "\n").trimIndent()
    }
}