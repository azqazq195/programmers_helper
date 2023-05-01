<#if dto.packagePath?has_content>
package ${dto.packagePath}

</#if>
<#if dto.useMain>
fun main() {
    fun printResult(index: Int, result: ${dto.testCaseDtos[0].result.type}, answer: ${dto.testCaseDtos[0].result.type}) {
        <#if dto.testCaseDtos[0].result.type?matches("^Array<(.*Array.*)>$")>
        val correct = result.contentDeepEquals(answer)
        <#elseif dto.testCaseDtos[0].result.type?matches("^.*Array.*$")>
        val correct = result.contentEquals(answer)
        <#else>
        val correct = result == answer
        </#if>
        val sb = StringBuilder()
        sb.append("\n\n테스트 케이스 ").append(index).append(": ")
        sb.append(if (correct) "정답" else "오답").append("\n")
        sb.append("\t- 실행 결과: \t").append(answer).append("\n")
        sb.append("\t- 기댓값: \t").append(result).append("\n")
        if (correct) println(sb) else throw RuntimeException(sb.toString())
    }

<#list dto.testCaseDtos as testCase>
    <#assign index = testCase?counter>
    <#-- 변수 나열 -->
    <#list testCase.values as value>
    val ${value.name}${index} = ${value.value}
    </#list>
    val ${testCase.result.name}${index} = ${testCase.result.value}
    val answer${index} = ${dto.className}().solution(<#list testCase.values as value>${value.name}${index}<#if value_has_next>, </#if></#list>)
    printResult(${index}, ${testCase.result.name}${index}, answer${index})
    <#if testCase_has_next>

    </#if>
</#list>
}

</#if>
class ${dto.className} {
    ${dto.classContent}
}