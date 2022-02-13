package lostankit7.droid.androidtesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lostankit7.droid.androidtesting.data.local.ShoppingItem
import lostankit7.droid.androidtesting.data.responses.ImageResponse
import lostankit7.droid.androidtesting.other.Event
import lostankit7.droid.androidtesting.other.Resource
import lostankit7.droid.androidtesting.repository.ShoppingRepository
import lostankit7.droid.androidtesting.util.Constants

//.value ensures that each value is emitted
//postValue() if we emit data frequently then only last value reflect

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {

        if (!validateInput(name, amountString, priceString)) return

        val shoppingItem =
            ShoppingItem(name, amountString.toInt(), priceString.toFloat(), _curImageUrl.value ?: "")

        insertShoppingItemIntoDb(shoppingItem)

        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) return

        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

    private fun validateInput(name: String, amountString: String, priceString: String): Boolean {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "Field should not be null",
                        null
                    )
                )
            )
            return false
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "name of item is too long",
                        null
                    )
                )
            )
            return false
        }
        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "price of item should not be long than ${Constants.MAX_PRICE_LENGTH}",
                        null
                    )
                )
            )
            return false
        }
        try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "please enter valid amount",
                        null
                    )
                )
            )
            return false
        }
        return true
    }
}













