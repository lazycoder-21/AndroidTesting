package lostankit7.droid.androidtesting.util

import android.content.Context

class ResourceComparer {

    fun checkResource(context: Context, resId: Int, str: String): Boolean {
        return context.getString(resId) == str
    }

}