package lostankit7.droid.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lostankit7.droid.androidtesting.MainCoroutineRule
import lostankit7.droid.androidtesting.other.Status
import lostankit7.droid.androidtesting.repository.FakeShoppingRepository
import lostankit7.droid.androidtesting.util.Constants
import lostankit7.droid.androidtesting.util.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest {

    //to ensure that everything executes in same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty fields returns error`() {
        viewModel.insertShoppingItem("ank", "", "")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with  too long name returns error`() {
        val name = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(i)
            }
        }

        viewModel.insertShoppingItem(name, "44", "45.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with  too long amount returns error`() {
        viewModel.insertShoppingItem("ank", "4455555555434443433434", "45")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with  valid input returns success`() {
        viewModel.insertShoppingItem("ank", "45", "459.8")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}