package lostankit7.droid.androidtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import lostankit7.droid.androidtesting.data.local.ShoppingItem
import lostankit7.droid.androidtesting.data.responses.ImageResponse
import lostankit7.droid.androidtesting.other.Resource
import java.nio.channels.FileLock

class FakeShoppingRepository : ShoppingRepository {

    var showNetworkError = false

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observeShoppingItems = MutableLiveData(shoppingItems)
    private val observeTotalPrice = MutableLiveData<Float>()

    private fun refreshLiveData() {
        observeShoppingItems.postValue(shoppingItems)
        observeTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice() =
        shoppingItems.sumOf { it.price.toDouble() }.toFloat()

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
        return observeShoppingItems as LiveData<List<ShoppingItem>>
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (showNetworkError) Resource.error("error", null)
        else Resource.success(ImageResponse(listOf(), 0, 0))
    }
}