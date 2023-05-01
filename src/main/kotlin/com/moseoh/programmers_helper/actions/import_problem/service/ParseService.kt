package com.moseoh.programmers_helper.actions.import_problem.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper.actions.import_problem.service.dto.ProblemDto
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
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

    fun parseHtmlToProblem(document: Document): ProblemDto {
        return ProblemDto(
            parseTitle(document),
            parseContent(document),
            parseTestCases(document)
        )
    }

    private fun parseTitle(document: Document): String {
        return document.select("li.algorithm-title").text()
    }

    private fun parseContent(document: Document): String {
        return document.select("textarea#code").text()
    }

    private fun parseTestCases(document: Document): List<Map<String, String>> {
        // 입출력 예제 div 찾기
        val h5Element =
            document.select("h5:containsOwn(입출력 예), h5:has(strong:containsOwn(입출력 예))").first()
                ?: document.select("h3:containsOwn(입출력 예제), h3:has(strong:containsOwn(입출력 예제))").first()
                ?: document.select("h3:containsOwn(예제 입출력), h3:has(strong:containsOwn(예제 입출력))").first()
        val tableElement = h5Element!!.nextElementSibling()

        val tableHeaders = tableElement!!.select("thead > tr > th")
        val tableRows = tableElement.select("tbody > tr")

        return tableRows.map { row ->
            val rowValues = row.select("td")
            rowValues.mapIndexed { index, td ->
                tableHeaders[index].text() to td.text().toString()
            }.toMap()
        }.toList()
    }
}