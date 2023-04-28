package com.moseoh.programmers_helper.actions.import_problem.service.impl

import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import com.moseoh.programmers_helper.actions.import_problem.model.dto.TestCaseDto

interface IMainContentService {
    fun get(solutionDto: SolutionDto): String
    fun mainContent(solutionDto: SolutionDto): String
    fun valueContents(index: Int, solutionDto: SolutionDto, testCase: TestCaseDto): String
    fun assertionContent(index: Int, testCase: TestCaseDto): String
}