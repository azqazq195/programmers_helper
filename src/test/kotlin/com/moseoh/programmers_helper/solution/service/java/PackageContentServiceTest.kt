package com.moseoh.programmers_helper.solution.service.java

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class PackageContentServiceTest {
    private lateinit var packageContentService: PackageContentService

    @Test
    fun get() {
        // given
        packageContentService = PackageContentService()

        // when
        val content = packageContentService.get("src", "src/com/moseoh/project")

        // then
        assertEquals("package com.moseoh.project;", content)
    }
}