package com.moseoh.programmers_helper.solution.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import com.moseoh.programmers_helper.solution.model.Solution
import com.moseoh.programmers_helper.solution.model.TestCase
import org.jsoup.nodes.Document


@Service
class ParseService(
    private val settings: ProgrammersHelperSettings = ProgrammersHelperSettings.instance
) {

    fun getUrl(urlInput: String): String {
        val sb = StringBuilder()
        sb.append(removeLanguageParameter(urlInput))
        sb.append("?language=")
        sb.append(settings.language.name.lowercase())
        return sb.toString()
    }

    private fun removeLanguageParameter(url: String): String {
        return url.replace(Regex("\\?language=[a-zA-Z+]+"), "")
    }

    fun parseHtml(document: Document): Solution {
        return Solution(
            parseTitle(document),
            parseContent(document),
            parseTestCase(document)
        )
    }

    private fun parseTitle(document: Document): String {
        return document.select("li.algorithm-title").text()
    }

    private fun parseContent(document: Document): String {
        return document.select("textarea#code").text()
    }

    private fun parseTestCase(document: Document): Array<TestCase> {
        val h5Element =
            document.select("h5:containsOwn(입출력 예), h5:has(strong:containsOwn(입출력 예))").first()
                ?: document.select("h3:containsOwn(입출력 예제), h3:has(strong:containsOwn(입출력 예제))").first()
        val tableElement = h5Element!!.nextElementSibling()


        val tableHeader = tableElement!!.select("thead > tr > th")
        val tableRows = tableElement.select("tbody > tr")

        return tableRows.map { row ->
            val values = mutableMapOf<String, Any>()
            val columnValues = row.select("td")

            for (i in 0 until tableHeader.size - 1) {
                val key = tableHeader[i].text()
                val value = parseValue(columnValues[i].text())
                values[key] = value
            }

            val result = parseValue(columnValues.last()!!.text())
            TestCase(values, result)
        }.toTypedArray()
    }

    private fun parseValue(value: String): Any {
        return when {
            value.startsWith("[[") -> {
                val list = mutableListOf<Array<*>>()
                val pattern = Regex("\\[(.*?)\\]")
                val matchResult = pattern.findAll(value.removeSurrounding("[", "]"))
                matchResult.forEach { result ->
                    list.add(parseValue(result.value) as Array<*>)
                }
                return list.toTypedArray()
            }

            value.startsWith("[") -> {
                val temp = value.removeSurrounding("[", "]")
                val values = if (temp.startsWith("\"")) {
                    val list = mutableListOf<String>()
                    val pattern = Regex("(\".*?\")")
                    val matchRegex = pattern.findAll(temp)
                    matchRegex.forEach { result ->
                        list.add(result.value)
                    }
                    list
                } else {
                    temp.split(",\\s*".toRegex())
                }

                values.map { parseValue(it) }.toTypedArray()
            }

            value.startsWith("\"") -> value.removeSurrounding("\"", "\"")
            value.length == 1 && value[0].isLetter() -> value[0]
            value.toIntOrNull() != null -> value.toInt()
            value.toLongOrNull() != null -> value.toLong()
            value.toFloatOrNull() != null -> value.toFloat()
            value.toDoubleOrNull() != null -> value.toDouble()
            else -> value
        }
    }
}