package lostankit7.droid.androidtesting

import com.google.common.truth.Truth.assertThat
import lostankit7.droid.androidtesting.util.RegistrationUtils
import org.junit.Test

class RegistrationUtilsTest {

    private val password = "123"

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtils.validateInput("", password, password)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and password returns true`() {
        val result = RegistrationUtils.validateInput("A",password,password)
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtils.validateInput("Ankit",password,password)
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtils.validateInput("ank","","")
        assertThat(result).isFalse()
    }

    @Test
    fun `different confirm password returns false`() {
        val result = RegistrationUtils.validateInput("ank","12","1234")
        assertThat(result).isFalse()
    }

    @Test
    fun `password containing less than 2 digits returns false`() {
        val result = RegistrationUtils.validateInput("ank","1an","12")
        assertThat(result).isFalse()
    }

}