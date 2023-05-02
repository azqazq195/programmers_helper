package com.moseoh.programmers_helper.actions.copy_answer.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.Utils.removeAll
import com.moseoh.programmers_helper._common.Utils.removeComment
import com.moseoh.programmers_helper._common.Utils.removeFunctionInBracket
import com.moseoh.programmers_helper.actions.copy_answer.service.impl.ICopyAnswerService
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

@Service
class JavaCopyAnswerService(
    private val settings: ProgrammersHelperSettings = ProgrammersHelperSettings.instance,
) : ICopyAnswerService {
    override fun convert(code: String): String {
        var lines = code.lines()

        // package 제거
        lines = lines.removeAll("^package.*".toRegex())

        // main 함수 제거
        if (settings.useMainFunction) {
            lines = lines.removeFunctionInBracket("public .* void main\\(.*\\).*".toRegex())
            lines = lines.removeFunctionInBracket("public .* void PRINT_RESULT\\(.*\\).*".toRegex())
        }

        // 주석 제거
        if (!settings.allowCopyComment) {
            lines = lines.removeComment()
        }

        return lines.joinToString(separator = "\n").trimIndent()
    }
}
