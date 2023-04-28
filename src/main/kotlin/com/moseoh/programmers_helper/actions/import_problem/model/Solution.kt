package com.moseoh.programmers_helper.actions.import_problem.model

data class Solution(
    val title: String,
    val content: String,
    val testCases: Array<TestCase>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Solution

        if (title != other.title) return false
        if (content != other.content) return false
        return testCases.contentEquals(other.testCases)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + testCases.contentHashCode()
        return result
    }
}