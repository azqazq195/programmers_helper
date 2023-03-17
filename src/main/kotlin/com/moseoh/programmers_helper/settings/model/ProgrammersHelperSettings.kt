package com.moseoh.programmers_helper.settings.model

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "ProgrammersHelperSettings", storages = [Storage("ProgrammersHelperSettings.xml")])
@Service
class ProgrammersHelperSettings : PersistentStateComponent<ProgrammersHelperSettings.State> {
    var selectedLanguage: Language = Language.Kotlin

    data class State(var selectedLanguage: Language = Language.Kotlin)

    override fun getState(): State {
        return State(selectedLanguage)
    }

    override fun loadState(state: State) {
        selectedLanguage = state.selectedLanguage
    }

    companion object {
        val instance: ProgrammersHelperSettings
            get() = ApplicationManager.getApplication().getService(ProgrammersHelperSettings::class.java)
    }
}
