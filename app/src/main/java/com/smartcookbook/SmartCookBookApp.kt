package com.smartcookbook

import android.app.Application
import com.smartcookbook.data.local.AppDatabase

class SmartCookBookApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
}
