package com.moseoh.programmers_helper._common

import com.moseoh.programmers_helper.actions.import_problem.service.dto.ITemplateDto
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.StringWriter

class Utils {
    companion object {
        fun convert(templatePath: String, dto: ITemplateDto): String {
            val configuration = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
            configuration.setClassLoaderForTemplateLoading(Utils::class.java.classLoader, "/templates")
            configuration.defaultEncoding = "UTF-8"
            val template: Template = configuration.getTemplate(templatePath)
            val writer = StringWriter()
            template.process(mapOf("dto" to dto), writer)
            return writer.toString()
        }
    }
}