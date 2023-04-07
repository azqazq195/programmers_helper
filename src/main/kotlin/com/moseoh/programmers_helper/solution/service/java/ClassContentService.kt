package com.moseoh.programmers_helper.solution.service.java

import com.intellij.openapi.components.service
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.service.impl.IClassContentService

class ClassContentService(
    private val settings: ProgrammersHelperSettings = ProgrammersHelperSettings.instance,
    private val mainContentService: MainContentService = service<MainContentService>()
) : IClassContentService {
    override fun get(solution: SolutionDto): String {
        if (!settings.useMainFunction) {
            return solution.classContent
        }

        val classLines = solution.classContent.lines() as MutableList<String>
        val mainLines = mainContentService.get(solution).lines()
        mainLines.asReversed().forEach { mainLine ->
            classLines.add(1, "\t$mainLine")
        }
        return classLines.joinToString(separator = "\n")
    }
}