package com.moseoh.programmers_helper.actions.import_problem.service.kotlin

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.actions.import_problem.service.impl.IPackageContentService

@Service
class KotlinPackageContentService : IPackageContentService {
    override fun get(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return "package " + packagePath.replace('/', '.')
    }
}