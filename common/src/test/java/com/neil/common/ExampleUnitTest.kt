package com.neil.common

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testBoolean() {
        //Boolean扩展函数使用
        val result1 = true.yes {
            "true"
        }.otherwise {
            "false"
        }

        val result2 = false.yes {
            "true"
        }.otherwise {
            "false"
        }

        println("result1 = $result1 , result2 = $result2")
    }

}
