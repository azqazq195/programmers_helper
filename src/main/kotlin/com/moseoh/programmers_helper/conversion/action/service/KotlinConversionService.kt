package com.moseoh.programmers_helper.conversion.action.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.conversion.action.service.`interface`.ConversionInterface

@Service
class KotlinConversionService : ConversionInterface {
    override fun convert(code: String): String {
        val lines = code.lines()
        val importLines = lines.filter { it.startsWith("import") }
        val classStartIndex = lines.indexOfFirst { it.startsWith("class ") }
        val classEndIndex = lines.indexOfLast { it == "}" }

        val classLines = lines.subList(classStartIndex, classEndIndex + 1)
        val extractedLines =
            if (importLines.isEmpty()) importLines + classLines
            else importLines + listOf("") + classLines
        return extractedLines.joinToString(separator = "\n")
    }
}