package com.moseoh.programmers_helper.conversion.action.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.conversion.action.service.impl.IConversionService

@Service
class JavaConversionService : IConversionService {
    override fun convert(code: String): String {
        val lines = code.lines()
        val importLines = lines.filter { it.startsWith("import") }
        val classStartIndex = lines.indexOfFirst { it.startsWith("public class ") || it.startsWith("class ") }
        val classEndIndex = lines.indexOfLast { it == "}" }

        val classLines = mutableListOf<String>()
        var mainBraceCount = 0
        var insideMain = false

        for (line in lines.subList(classStartIndex, classEndIndex + 1)) {
            if (line.contains("public static void main")) {
                insideMain = true
            }

            if (insideMain) {
                mainBraceCount += line.count { it == '{' } - line.count { it == '}' }
                if (mainBraceCount == 0) {
                    insideMain = false
                }
            } else {
                classLines.add(line)
            }
        }

        val extractedLines =
            if (importLines.isEmpty()) importLines + classLines
            else importLines + listOf("") + classLines
        return extractedLines.joinToString(separator = "\n")
    }
}
