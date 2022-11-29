package com.example.shiftstestapplication

import android.app.Application
import timber.log.Timber

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
class ShiftsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}