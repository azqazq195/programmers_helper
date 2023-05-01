package com.moseoh.programmers_helper.actions.import_problem.service.dto

data class KotlinTemplateDto(
    val packagePath: String,
    val useMain: Boolean,
    val helpComment: String?,
    val className: String,
    val classContent: String,
    val testCaseDtos: List<TestCaseDto>,
) : ITemplateDto {
    data class TestCaseDto(
        val values: List<Value>,
        val result: Value,
    )

    data class Value(
        val type: String,
        val name: String,
        val value: String,
    )
}

