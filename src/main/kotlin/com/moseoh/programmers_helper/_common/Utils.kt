package com.moseoh.programmers_helper._common

class Utils {
    companion object {
        fun convert(template: String, values: Map<String, String>): String {
            var result = template
            for ((key, value) in values) {
                result = result.replace(key, value)
            }
            return result
        }
    }
}