package com.moseoh.programmers_helper.actions.import_problem.service.impl

import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto

interface IClassContentService {
    fun get(solution: SolutionDto): String
}
