<#if dto.packagePath?has_content>
package ${dto.packagePath};

</#if>
<#if dto.useImportArray>
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
        ${testCase.result.type} ${testCase.result.name}${index} = ${testCase.result.value};
        ${testCase.result.type} answer${index} = new ${dto.className}().solution(<#list testCase.values as value>${value.name}${index}<#if value_has_next>, </#if></#list>);
        PRINT_RESULT(${index}, ${testCase.result.name}${index}, answer${index});
        <#if testCase_has_next>

        </#if>
    </#list>
    }

    public static void PRINT_RESULT(int index, ${dto.testCaseDtos[0].result.type} result, ${dto.testCaseDtos[0].result.type} answer) {
        <#if dto.testCaseDtos[0].result.type?contains("[][]")>
        boolean correct = Arrays.deepEquals(result, answer);
        <#elseif dto.testCaseDtos[0].result.type?contains("[]")>
        boolean correct = Arrays.equals(result, answer);
        <#elseif dto.testCaseDtos[0].result.type == "String">
        boolean correct = result.equals(answer);
        <#else>
        boolean correct = result == answer;
        </#if>
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n테스트 케이스 ").append(index).append(": ");
        sb.append(correct ? "정답" : "오답").append("\n");
        sb.append("\t- 실행 결과: \t").append(answer).append("\n");
        sb.append("\t- 기댓값: \t").append(result).append("\n");
        if (correct) System.out.println(sb);
        else throw new RuntimeException(sb.toString());
    }

</#if>
    ${dto.classContent}
}