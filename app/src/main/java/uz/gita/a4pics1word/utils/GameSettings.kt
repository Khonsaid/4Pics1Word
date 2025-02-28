package uz.gita.a4pics1word.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.VibrationEffect
import android.os.Vibrator
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.domain.MyPref

class GameSettings private constructor(context: Context) {
    private val pref = MyPref.getInstance()
    var answerSound: Int = 0
        private set
    var variantSound: Int = 0
        private set
    var btnSound: Int = 0
        private set
    var imgHintSound: Int = 0
        private set
    var openImgSound: Int = 0
        private set
    var win: Int = 0
        private set
    var errorSound: Int = 0
        private set
    private var soundEnable: Boolean = pref.getSoundBtnEnabled()

    companion object {
        private var instance: GameSettings? = null

        fun getInstance(context: Context): GameSettings = instance ?: synchronized(this) {
            instance ?: GameSettings(context).also { instance == it }
        }

        private val soundPool = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        ).build()
    }

    private val vibrator = context.getSystemService(Vibrator::class.java) as Vibrator

//    init {
//        errorSound = soundPool.load(context, R.raw.click_error, 1)
//        imgHintSound = soundPool.load(context, R.raw.rapid_wind, 1)
//        openImgSound = soundPool.load(context, R.raw.slow_wind, 1)
//        variantSound = soundPool.load(context, R.raw.remove, 1)
//        answerSound = soundPool.load(context, R.raw.add, 1)
//        win = soundPool.load(context, R.raw.win, 1)
//    }

    fun vibrateHeavyClick() {
        if (true) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                vibrator.cancel()
                vibrator.vibrate(vibrationEffect)
            }
        }
    }

    fun playSound(soundID: Int) {
        if (soundEnable) soundPool.play(soundID, 1F, 1F, 0, 0, 1F)
    }

    fun setSoundEnable(soundEnable: Boolean) {
        pref.setSoundBtnEnabled(soundEnable)
    }
}