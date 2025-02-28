package uz.gita.a4pics1word.domain

import android.content.SharedPreferences

class MyPref private constructor() {
    companion object {
        private lateinit var instance: MyPref
        private lateinit var pref: SharedPreferences
        private val repository = AppRepository.getInstance()
        fun init(sharedPreferences: SharedPreferences) {
            instance = MyPref()
            this.pref = sharedPreferences
        }

        fun getInstance(): MyPref {
            return instance
        }
    }

    private var changedLevel = -1
    fun setLevel(level: Int) {
        if (repository.getQuestionSize() >= level)
            pref.edit().putInt("level", level).apply()
    }

    fun getLevel(): Int {
        return pref.getInt("level", getLevelReached());
    }

    fun saveAnswerState(answer: ArrayList<Char>) {
        val sb = StringBuilder()
        for (ch in answer) {
            sb.append(ch).append(".")
        }
        sb.setLength(sb.length - 1)
        pref.edit().putString("curr_answer", sb.toString()).apply()
    }

    fun saveVariantState(variant: ArrayList<Char>) {
        val sb = StringBuilder()
        for (ch in variant) {
            sb.append(ch).append(".")
        }
        sb.setLength(sb.length - 1)
        pref.edit().putString("curr_variant", sb.toString()).apply()
    }

    fun getCurrAnswer(): List<String> {
        return pref.getString("curr_answer", null)?.split(".") ?: emptyList()
    }

    fun getCurrVariant(): List<String> {
        return pref.getString("curr_variant", null)?.split(".") ?: emptyList()
    }

    fun removeGameStates() {
        pref.edit().remove("curr_answer").apply()
        pref.edit().remove("curr_variant").apply()
    }

    fun saveCoin(coin: Int) {
        pref.edit().putInt("coin", coin).apply()
    }

    fun getCoin(): Int = pref.getInt("coin", 120)
    fun setLevelReached(level: Int) {
        if (getLevelReached() < level) {
            pref.edit().putInt("level_reached", level).apply();
        }
    }

    fun getLevelReached(): Int = pref.getInt("level_reached", 0)

    fun setSoundBtnEnabled(soundEnabled: Boolean) {
        pref.edit().putBoolean("SOUND_BTN_ENABLED", soundEnabled).apply();
    }

    fun getSoundBtnEnabled(): Boolean = pref.getBoolean("SOUND_BTN_ENABLED", true);
    fun saveChangedLevel(level: Int) {
        changedLevel = level
    }

    fun getChangedLevel(): Int = changedLevel
}