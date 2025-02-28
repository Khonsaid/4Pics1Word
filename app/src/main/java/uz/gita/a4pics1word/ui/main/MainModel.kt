package uz.gita.a4pics1word.ui.main

import uz.gita.a4pics1word.domain.AppRepository
import uz.gita.a4pics1word.domain.MyPref

class MainModel : MainContract.Model {
    private val pref = MyPref.getInstance()
    private val appRepository = AppRepository.getInstance()

    override fun getCoin(): Int = pref.getCoin()

    override fun getCurrQuestion(): List<Int> {
        val index = if (pref.getLevel() < appRepository.getQuestionSize() && pref.getLevel() != -1) pref.getLevel() else 0
        return appRepository.getQuestionByIndex(index).images
    }

    override fun getLevel(): Int = pref.getLevel()
    override fun saveLevel(level: Int) {
        pref.setLevel(level)
    }

    override fun removeState() {
        pref.removeGameStates()
    }

    override fun getChangedLevel(): Int = pref.getChangedLevel()
    override fun saveChangedLevel(level: Int) {
        pref.saveChangedLevel(level)
    }

    override fun getLevelReached(): Int = pref.getLevelReached()

    override fun getQuestionSize(): Int = appRepository.getQuestionSize()
    override fun setCurrLevel(level: Int) {
        pref.removeGameStates()
        pref.setLevel(level)
    }
}