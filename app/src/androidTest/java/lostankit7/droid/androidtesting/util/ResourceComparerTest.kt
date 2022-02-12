package lostankit7.droid.androidtesting.util

import android.content.Context
import com.google.common.truth.Truth.assertThat
import androidx.test.core.app.ApplicationProvider
import lostankit7.droid.androidtesting.R
import org.junit.After
import org.junit.Before

class ResourceComparerTest {

    private lateinit var comparer: ResourceComparer

    @Before
    fun setUp() {
        comparer = ResourceComparer()
    }

    @After
    fun tearDown() {
        //do something what you want to executed after every test case
    }

    fun resourceIdSameAsString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = comparer.checkResource(context, R.string.app_name, "AndroidTesting")
        assertThat(result).isTrue()
    }

    fun resourceIdDifferentAsString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = comparer.checkResource(context, R.string.app_name, "blah..")
        assertThat(result).isFalse()
    }
}