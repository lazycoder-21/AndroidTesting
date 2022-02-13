package lostankit7.droid.androidtesting

import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.managers.ApplicationComponentManager

@HiltAndroidApp
class AndroidTestApplication : Application()