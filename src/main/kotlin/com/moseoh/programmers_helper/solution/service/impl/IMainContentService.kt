package com.moseoh.programmers_helper.solution.service.impl

import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.model.dto.TestCaseDto

interface IMainContentService {
    fun get(solutionDto: SolutionDto): String
    fun mainContent(solutionDto: SolutionDto): String
    fun valueContents(index: Int, solutionDto: SolutionDto, testCase: TestCaseDto): String
    fun printlnContent(index: Int, testCase: TestCaseDto): String
    fun assertionContent(index: Int, testCase: TestCaseDto): String
}