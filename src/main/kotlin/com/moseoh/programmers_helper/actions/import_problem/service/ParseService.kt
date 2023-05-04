package com.moseoh.programmers_helper.actions.import_problem.service

import com.intellij.openapi.components.Service
import com.moseoh.programmers_helper._common.CException
import com.moseoh.programmers_helper.actions.import_problem.service.dto.ProblemDto
import com.moseoh.programmers_helper.settings.model.ProgrammersHelperSettings
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


@Service
class ParseService(
    private val settings: ProgrammersHelperSettings.State = ProgrammersHelperSettings.state
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
            parseTestCases(document),
            parseAnswerName(document)
        )
    }

    private fun parseTitle(document: Document): String {
        return document.select("li.algorithm-title").text()
    }

    private fun parseContent(document: Document): String {
        return document.select("textarea#code").text()
    }

    private fun parseTestCases(document: Document): List<Map<String, String>> {
        val tableElement = findTestCaseTable(document)

        val tableHeaders = tableElement.select("thead > tr > th")
        val tableRows = tableElement.select("tbody > tr")

        val list = tableRows.map { row ->
            val rowValues = row.select("td")
            rowValues.mapIndexed { index, td ->
                tableHeaders[index].text() to td.text().toString()
            }.toMap()
        }.toList()

        if (list.isEmpty()) throw CException("테스트 케이스의 값을 읽지 못했습니다.")
        return list
    }

    private fun parseAnswerName(document: Document): String {
        val tableElement = findTestCaseTable(document)
        val tableHeaders = tableElement.select("thead > tr > th")
        return tableHeaders.last()!!.text()
    }

    private fun findTestCaseTable(document: Document): Element {
        // 입출력 예제 div 찾기
        val h5Element =
            document.select("h5:containsOwn(입출력 예), h5:has(strong:containsOwn(입출력 예))").first()
                ?: document.select("h3:containsOwn(입출력 예제), h3:has(strong:containsOwn(입출력 예제))").first()
                ?: document.select("h3:containsOwn(예제 입출력), h3:has(strong:containsOwn(예제 입출력))").first()

        if (h5Element!!.nextElementSibling() == null) throw CException("테스트 케이스를 찾을 수 없습니다.")
        return h5Element.nextElementSibling()!!
    }
}