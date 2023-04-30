package com.moseoh.programmers_helper._common

import com.moseoh.programmers_helper.actions.import_problem.model.dto.SolutionDto
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.StringWriter

class Utils {
    companion object {
        fun convert(template: String, values: Map<String, String>): String {
            var result = template
            for ((key, value) in values) {
                result = result.replace(key, value)
            }
            return result
        }

        fun convert(templatePath: String, solutionDto: SolutionDto): String {
            val configuration = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
            configuration.setClassLoaderForTemplateLoading(Utils::class.java.classLoader, "/templates")
            configuration.defaultEncoding = "UTF-8"
            val template: Template = configuration.getTemplate(templatePath)
            val writer = StringWriter()
            template.process(mapOf("solutionDto" to solutionDto, "packagePath" to "packagePath"), writer)
            return writer.toString()
        }
    }
}