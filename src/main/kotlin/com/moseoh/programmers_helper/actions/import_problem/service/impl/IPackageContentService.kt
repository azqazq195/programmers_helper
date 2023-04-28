package com.moseoh.programmers_helper.actions.import_problem.service.impl

interface IPackageContentService {
    fun get(projectPath: String, directoryPath: String): String
}