package com.base.videogames.validations


import com.base.core.extensions.PasswordValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
class ValidationUnitTest {

    @Before
    fun setUp() {

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun passwordValidator_CorrentPassowrd_ReturnsTrue() {
        assertTrue(PasswordValidator().validate("12345678"))
    }
}
