package com.moseoh.programmers_helper.settings.model

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "ProgrammersHelperSettings", storages = [Storage("ProgrammersHelperSettings.xml")])
@Service
class ProgrammersHelperSettings : PersistentStateComponent<ProgrammersHelperSettings.State> {
    data class State(
        /**
         * 사용 언어
         */
        var language: Language = Language.Kotlin,

        /**
         * 클립보드 사용시 url 자동 입력
         */
        var useClipboard: Boolean = true,

        /**
         * 폴더(문제_제목) > 파일(Solution)
         * 파일(문제_제목)
         */
        var useFolder: Boolean = true,

        /**
         * 문제_제목
         * 문제제목
         */
        var useNameSpacing: Boolean = false,

        /**
         * main 함수 만들기
         */
        var useMainFunction: Boolean = true,

        /**
         * 도움말 보기
         */
        var useHelpComment: Boolean = true,

        /**
         * 주석 복사 여부
         */
        var allowCopyComment: Boolean = false,
    )

    private var state = State()

    override fun getState(): State {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        val state: ProgrammersHelperSettings.State
            get() = ApplicationManager.getApplication().getService(ProgrammersHelperSettings::class.java).state
    }
}
