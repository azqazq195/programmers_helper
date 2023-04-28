package com.moseoh.programmers_helper.actions.import_problem.service.java

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.service.impl.IClassContentService
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings

@Service
class JavaClassContentService(
    private val settings: ProgrammersHelperSettings = ProgrammersHelperSettings.instance,
    private val mainContentService: JavaMainContentService = service<JavaMainContentService>()
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