package com.moseoh.programmers_helper.solution.service.`interface`

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.solution.model.Solution

interface ContentService {
    fun getContent(project: Project, directory: VirtualFile, solution: Solution): String
    fun getPackage(projectPath: String, directoryPath: String): String
    fun getMain(solution: Solution): String
    fun getMainContent(solution: Solution): String
    fun value(value: Any): String
    fun getClass(solution: Solution): String
}