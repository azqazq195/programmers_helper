package com.moseoh.programmers_helper.solution.service.kotlin

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.common.Utils
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto
import com.moseoh.programmers_helper.solution.service.impl.IContentService

@Service
class KotlinContentService(
    private val settings: ProgrammersHelperSettings = ProgrammersHelperSettings.instance,
    private val packageContentService: KotlinPackageContentService = service<KotlinPackageContentService>(),
    private val mainContentService: KotlinMainContentService = service<KotlinMainContentService>(),
    private val classContentService: KotlinClassContentService = service<KotlinClassContentService>()
) : IContentService {
    companion object {
        val CONTENT_TEMPLATE = """
            ${'$'}{package}
            
            ${'$'}{class}
        """.trimIndent()

        val CONTENT_TEMPLATE_WITH_MAIN = """
            ${'$'}{package}

            ${'$'}{main}
            
            ${'$'}{class}
        """.trimIndent()
    }

    override fun get(project: Project, directory: VirtualFile, solution: SolutionDto): String {
        val template = if (settings.useMainFunction) CONTENT_TEMPLATE_WITH_MAIN else CONTENT_TEMPLATE

        val values = hashMapOf<String, String>()
        values["${'$'}{package}"] = packageContentService.get(project.basePath!!, directory.path)
        values["${'$'}{class}"] = classContentService.get(solution)
        if (settings.useMainFunction) values["${'$'}{main}"] = mainContentService.get(solution)
        return Utils.convert(template, values)
    }
}