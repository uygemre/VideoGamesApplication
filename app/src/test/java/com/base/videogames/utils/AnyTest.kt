package com.base.videogames.utils

import com.base.core.extensions.getMethodTag
import org.junit.Assert.assertEquals
import org.junit.Test

class AnyTest {

    @Test
    fun testGetMethodTag() {
        assertEquals("AnyTestgetMethodTag", getMethodTag())
    }
}