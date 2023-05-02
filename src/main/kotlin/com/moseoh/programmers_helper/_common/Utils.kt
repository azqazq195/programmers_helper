package com.moseoh.programmers_helper._common

import com.moseoh.programmers_helper.actions.import_problem.service.dto.ITemplateDto
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.StringWriter

object Utils {
    fun convert(templatePath: String, dto: ITemplateDto): String {
        val configuration = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
        configuration.setClassLoaderForTemplateLoading(Utils::class.java.classLoader, "/templates")
        configuration.defaultEncoding = "UTF-8"
        val template: Template = configuration.getTemplate(templatePath)
        val writer = StringWriter()
        template.process(mapOf("dto" to dto), writer)
        return writer.toString()
    }

    fun List<String>.removeAll(regex: Regex): List<String> {
        return this.filter { !it.trim().matches(regex) }
    }

    fun List<String>.removeFunctionInBracket(regex: Regex): List<String> {
        val startLine = this.indexOfFirst { it.trim().matches(regex) }
        if (startLine == -1) return this

        var endLine = -1
        var bracketCount = 0
        for (i in startLine until this.size) {
            bracketCount += this[i].count { it == '{' } - this[i].count { it == '}' }
            if (bracketCount == 0) {
                endLine = i
                break
            }
        }
        if (endLine == -1) return this

        if (this[endLine + 1].trim() == "") endLine += 1
        return this.subList(0, startLine) + this.subList(endLine + 1, this.size)
    }

    fun List<String>.removeComment(): List<String> {
        var lines = this

        // /** */ 방식 주석
        var startLine = lines.indexOfFirst { it.trim().startsWith("/**") }
        while (startLine != -1) {
            val endLine = lines.subList(startLine, lines.size).indexOfFirst { it.trim().startsWith("*/") }
            if (endLine == -1) break
            lines = lines.subList(0, startLine) + lines.subList(startLine + endLine + 1, lines.size)
            startLine = lines.indexOfFirst { it.trim().startsWith("/**") }
        }

        // /* */ 방식 주석
        startLine = lines.indexOfFirst { it.trim().startsWith("/*") }
        while (startLine != -1) {
            val endLine = lines.subList(startLine, lines.size).indexOfFirst { it.trim().startsWith("*/") }
            if (endLine == -1) break
            lines = lines.subList(0, startLine) + lines.subList(startLine + endLine + 1, lines.size)
            startLine = lines.indexOfFirst { it.trim().startsWith("/*") }
        }

        // // 방식 주석
        return lines.filter { !it.trim().matches("//.*".toRegex()) }
    }
}

