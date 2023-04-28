package com.moseoh.programmers_helper.conversion.action.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.conversion.action.service.impl.IConversionService

@Service
class KotlinConversionService : IConversionService {
    override fun convert(code: String): String {
        var lines = code.lines()
        val packageLine = getPackageLineIndex(lines)
        val mainLine = getMainLineIndex(lines)

        // main 제거
        if (mainLine != Pair(-1, -1)) {
            lines = lines.subList(0, mainLine.first) + lines.subList(mainLine.second + 1, lines.size)
        }

        // package 제거
        if (packageLine != Pair(-1, -1)) {
            lines = lines.subList(0, packageLine.first) + lines.subList(packageLine.second + 1, lines.size)
        }

        return lines.joinToString(separator = "\n").trimIndent()
    }

    private fun getPackageLineIndex(lines: List<String>): Pair<Int, Int> {
        val first = lines.indexOfFirst { it.trim().startsWith("package") }
        val last = lines.indexOfLast { it.trim().startsWith("package") }
        return if (lines[last + 1].trim() == "") Pair(first, last + 1) else Pair(first, last)
    }

    private fun getMainLineIndex(lines: List<String>): Pair<Int, Int> {
        val first = lines.indexOfFirst { it.trim().startsWith("fun main(") }
        if (first == -1) return Pair(-1, -1)

        var bracketCount = 0
        for (i in first until lines.size) {
            bracketCount += lines[i].count { it == '{' } - lines[i].count { it == '}' }
            if (bracketCount == 0) {
                return if (lines[i + 1].trim() == "") Pair(first, i + 1) else Pair(first, i)
            }
        }

        throw RuntimeException("Conversion Error: main method end line not found")
    }
}