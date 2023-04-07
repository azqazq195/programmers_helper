package com.moseoh.programmers_helper.solution.service.kotlin

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.service.impl.IClassContentService

@Service
class KotlinClassContentService : IClassContentService {
    override fun get(solution: SolutionDto): String {
        return solution.classContent
    }
}