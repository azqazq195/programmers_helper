<#assign answerType = dto.testCaseDtos[0].answer.type>
<#if dto.packagePath?has_content>
package ${dto.packagePath}

</#if>
<#if dto.helpComment?has_content>
${dto.helpComment}
</#if>
<#if dto.useMain>
fun main() {
    fun printResult(index: Int, result: ${answerType}, answer: ${answerType}) {
        <#if answerType?matches("^Array<(.*Array.*)>$")>
        val correct = result.contentDeepEquals(answer)
        <#elseif answerType?matches("^.*Array.*$")>
        val correct = result.contentEquals(answer)
        <#else>
        val correct = result == answer
        </#if>
        val sb = StringBuilder()
        sb.append("\n\n테스트 케이스 ").append(index).append(": ")
        sb.append(if (correct) "정답" else "오답").append("\n")
        <#if answerType?matches("^Array<(.*Array.*)>$")>
        sb.append("\t- 실행 결과: \t").append(result.contentDeepToString()).append("\n")
        sb.append("\t- 기댓값: \t").append(answer.contentDeepToString()).append("\n")
        <#elseif answerType?matches("^.*Array.*$")>
        sb.append("\t- 실행 결과: \t").append(result.contentToString()).append("\n")
        sb.append("\t- 기댓값: \t").append(answer.contentToString()).append("\n")
        <#else>
        sb.append("\t- 실행 결과: \t").append(result).append("\n")
        sb.append("\t- 기댓값: \t").append(answer).append("\n")
        </#if>
        if (correct) println(sb) else throw RuntimeException(sb.toString())
    }

<#list dto.testCaseDtos as testCase>
    <#assign index = testCase?counter>
    <#-- 변수 나열 -->
    <#list testCase.values as value>
    val ${value.name}${index} = ${value.value}
    </#list>
    val answer${index} = ${testCase.answer.value}
    val result${index} = ${dto.className}().solution(<#list testCase.values as value>${value.name}${index}<#if value_has_next>, </#if></#list>)
    printResult(${index}, result${index}, answer${index})
    <#if testCase_has_next>

    </#if>
</#list>
}

</#if>
class ${dto.className} {
    ${dto.classContent}
}