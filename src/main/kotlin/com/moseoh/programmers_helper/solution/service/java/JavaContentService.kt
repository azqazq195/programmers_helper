package com.moseoh.programmers_helper.solution.service.java

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.moseoh.programmers_helper.common.Utils
import com.moseoh.programmers_helper.solution.model.dto.SolutionDto

@Service
class JavaContentService(
    private val packageContentService: PackageContentService = service<PackageContentService>(),
    private val classContentService: ClassContentService = service<ClassContentService>()
) {
    companion object {
        val CONTENT_TEMPLATE = """
            ${'$'}{package}
            
            ${'$'}{class}
        """.trimIndent()

        val CONTENT_TEMPLATE_WITH_IMPORT = """
            ${'$'}{package}
            
            ${'$'}{import}
            
            ${'$'}{class}
        """.trimIndent()
    }

    fun get(project: Project, directory: VirtualFile, solution: SolutionDto): String {
        val isImportArrays = solution.isImportArrays()
        val template = if (isImportArrays) CONTENT_TEMPLATE_WITH_IMPORT else CONTENT_TEMPLATE

        val values = hashMapOf<String, String>()
        values["${'$'}{package}"] = packageContentService.get(project.basePath!!, directory.path)
        values["${'$'}{class}"] = classContentService.get(solution)
        if (isImportArrays) values["${'$'}{import}"] = importContent()
        return Utils.convert(template, values)
    }

    private fun importContent(): String =
        "import java.util.Arrays;"

}