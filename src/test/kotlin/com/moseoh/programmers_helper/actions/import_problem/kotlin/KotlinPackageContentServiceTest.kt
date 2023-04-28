package com.moseoh.programmers_helper.actions.import_problem.kotlin

import com.moseoh.programmers_helper.actions.import_problem.service.kotlin.KotlinPackageContentService
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class KotlinPackageContentServiceTest {
    private lateinit var packageContentService: KotlinPackageContentService

    @Test
    fun get() {
        // given
        packageContentService = KotlinPackageContentService()

        // when
        val content = packageContentService.get("src", "src/com/moseoh/project")

        // then
        assertEquals("package com.moseoh.project", content)
    }
}