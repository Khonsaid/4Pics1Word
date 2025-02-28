package uz.gita.a4pics1word.app

import android.app.Application
import android.content.Context
import uz.gita.a4pics1word.domain.MyPref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyPref.init(this.getSharedPreferences("my_game", Context.MODE_PRIVATE))
    }
}