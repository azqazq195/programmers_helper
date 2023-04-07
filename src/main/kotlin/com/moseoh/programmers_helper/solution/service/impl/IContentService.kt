package com.moseoh.programmers_helper.solution.service.impl

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto

interface IContentService {
    fun get(project: Project, directory: VirtualFile, solution: SolutionDto): String
}