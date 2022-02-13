package lostankit7.droid.androidtesting.repository

import androidx.lifecycle.LiveData
import lostankit7.droid.androidtesting.data.PixabayAPI
import lostankit7.droid.androidtesting.data.local.ShoppingDao
import lostankit7.droid.androidtesting.data.local.ShoppingItem
import lostankit7.droid.androidtesting.data.responses.ImageResponse
import lostankit7.droid.androidtesting.other.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("unknown error", null)
            } else {
                Resource.error("unknown error", null)
            }
        } catch (e: Exception) {
            Resource.error("internet error", null)
        }
    }


}