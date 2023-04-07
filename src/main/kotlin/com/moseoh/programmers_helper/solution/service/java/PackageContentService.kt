package com.moseoh.programmers_helper.solution.service.java

import com.moseoh.programmers_helper.solution.service.impl.IPackageContentService

class PackageContentService : IPackageContentService {
    override fun get(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return "package " + packagePath.replace('/', '.') + ";"
    }
}