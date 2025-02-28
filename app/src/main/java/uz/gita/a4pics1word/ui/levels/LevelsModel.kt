package uz.gita.a4pics1word.ui.levels

import uz.gita.a4pics1word.data.QuestionData
import uz.gita.a4pics1word.domain.AppRepository
import uz.gita.a4pics1word.domain.MyPref

class LevelsModel : LevelsContract.Model {
    private val pref = MyPref.getInstance()
    private val repository = AppRepository.getInstance()

    override fun getLevel(): Int = pref.getLevel()

    override fun getQuestions(): ArrayList<QuestionData> = repository.getQuestions()

    override fun saveLevel(level: Int) {
        pref.saveChangedLevel(level)
    }

    override fun getLevelReached(): Int = pref.getLevelReached()
}