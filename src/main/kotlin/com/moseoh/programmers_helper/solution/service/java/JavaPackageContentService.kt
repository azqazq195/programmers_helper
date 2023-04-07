package com.moseoh.programmers_helper.solution.service.java

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.solution.service.impl.IPackageContentService

@Service
class JavaPackageContentService : IPackageContentService {
    override fun get(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return "package " + packagePath.replace('/', '.') + ";"
    }
}