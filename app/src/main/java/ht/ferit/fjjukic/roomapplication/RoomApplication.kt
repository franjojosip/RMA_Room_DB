package ht.ferit.fjjukic.roomapplication

import android.app.Application
import android.content.Context

class RoomApplication : Application() {
    companion object {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this;
    }

}