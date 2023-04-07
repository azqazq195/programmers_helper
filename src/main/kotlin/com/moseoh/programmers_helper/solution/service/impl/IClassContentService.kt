package com.moseoh.programmers_helper.solution.service.impl

import com.moseoh.programmers_helper.solution.model.dto.SolutionDto

interface IClassContentService {
    fun get(solution: SolutionDto): String
}
