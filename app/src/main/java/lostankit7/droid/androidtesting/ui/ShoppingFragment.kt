package lostankit7.droid.androidtesting.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import lostankit7.droid.androidtesting.R
import javax.inject.Inject


class ShoppingFragment (

) : Fragment(R.layout.fragment_shopping) {

    var viewModel: ShoppingViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)


    }

}
















