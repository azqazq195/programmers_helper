package com.moseoh.programmers_helper.solution.service.java

class PackageContentService {
    fun get(projectPath: String, directoryPath: String): String {
        val packagePath = directoryPath.substring(directoryPath.indexOf(projectPath) + projectPath.length + 1)
        return "package " + packagePath.replace('/', '.') + ";"
    }
}