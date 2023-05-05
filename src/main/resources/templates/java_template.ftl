<#assign answerType = dto.testCaseDtos[0].answer.type>
<#if dto.packagePath?has_content>
package ${dto.packagePath};

</#if>
<#if answerType?contains("[][]") || answerType?contains("[]")>
import java.util.Arrays;

</#if>
<#if dto.helpComment?has_content>
${dto.helpComment}
</#if>
class ${dto.className} {
<#if dto.useMain>
    public static void main(String[] args) {
    <#list dto.testCaseDtos as testCase>
        <#assign index = testCase?counter>
        <#-- 변수 나열 -->
        <#list testCase.values as value>
        ${value.type} ${value.name}${index} = ${value.value};
        </#list>
        ${testCase.answer.type} answer${index} = ${testCase.answer.value};
        ${testCase.answer.type} result${index} = new ${dto.className}().solution(<#list testCase.values as value>${value.name}${index}<#if value_has_next>, </#if></#list>);
        PRINT_RESULT(${index}, result${index}, answer${index});
        <#if testCase_has_next>

        </#if>
    </#list>
    }

    public static void PRINT_RESULT(int index, ${answerType} result, ${answerType} answer) {
        <#if answerType?contains("[][]")>
        boolean correct = Arrays.deepEquals(result, answer);
        <#elseif answerType?contains("[]")>
        boolean correct = Arrays.equals(result, answer);
        <#elseif answerType == "String">
        boolean correct = result.equals(answer);
        <#else>
        boolean correct = result == answer;
        </#if>
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
        sb.append(correct ? "정답" : "오답").append("\n");
        <#if answerType?contains("[][]")>
        sb.append("\t- 실행 결과: \t").append(Arrays.deepToString(result)).append("\n");
        sb.append("\t- 기댓값: \t").append(Arrays.deepToString(answer)).append("\n");
        <#elseif answerType?contains("[]")>
        sb.append("\t- 실행 결과: \t").append(Arrays.toString(result)).append("\n");
        sb.append("\t- 기댓값: \t").append(Arrays.toString(answer)).append("\n");
        <#else>
        sb.append("\t- 실행 결과: \t").append(result).append("\n");
        sb.append("\t- 기댓값: \t").append(answer).append("\n");
        </#if>
        if (correct) System.out.println(sb);
        else throw new RuntimeException(sb.toString());
    }

</#if>
    ${dto.classContent}
}