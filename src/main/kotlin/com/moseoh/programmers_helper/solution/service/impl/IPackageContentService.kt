package com.moseoh.programmers_helper.solution.service.impl

interface IPackageContentService {
    fun get(projectPath: String, directoryPath: String): String
}