package com.moseoh.programmers_helper.actions.import_problem.java

import com.moseoh.programmers_helper.actions.import_problem.service.java.JavaPackageContentService
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class JavaPackageContentServiceTest {
    private lateinit var packageContentService: JavaPackageContentService

    @Test
    fun get() {
        // given
        packageContentService = JavaPackageContentService()

        // when
        val content = packageContentService.get("src", "src/com/moseoh/project")

        // then
        assertEquals("package com.moseoh.project;", content)
    }
}