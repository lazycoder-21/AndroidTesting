package lostankit7.droid.androidtesting.repository

import androidx.lifecycle.LiveData
import lostankit7.droid.androidtesting.data.local.ShoppingItem
import lostankit7.droid.androidtesting.data.responses.ImageResponse
import lostankit7.droid.androidtesting.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeShoppingItems() : LiveData<List<ShoppingItem>>

    fun observeTotalPrice() : LiveData<Float>

    suspend fun searchForImage(imageQuery : String) : Resource<ImageResponse>
}