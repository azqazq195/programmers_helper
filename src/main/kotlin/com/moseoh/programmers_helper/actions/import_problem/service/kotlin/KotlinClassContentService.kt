package com.moseoh.programmers_helper.actions.import_problem.service.kotlin

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.service.impl.IClassContentService

@Service
class KotlinClassContentService : IClassContentService {
    override fun get(solution: SolutionDto): String {
        return solution.classContent
    }
}