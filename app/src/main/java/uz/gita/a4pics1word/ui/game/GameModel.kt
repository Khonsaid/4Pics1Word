package uz.gita.a4pics1word.ui.game

import uz.gita.a4pics1word.data.QuestionData
import uz.gita.a4pics1word.domain.AppRepository
import uz.gita.a4pics1word.domain.MyPref

class GameModel : GameContract.Model {
    private val repository = AppRepository.getInstance()
    private val pref = MyPref.getInstance()

    override fun saveLevel(level: Int) = pref.setLevel(level)
    override fun saveCoin(coin: Int) = pref.saveCoin(coin)

    override fun getQuestionByIndex(index: Int): QuestionData = repository.getQuestionByIndex(index)
    override fun getLevel(): Int = pref.getLevel()
    override fun getCoin(): Int = pref.getCoin()
    override fun saveCurrState(answer: ArrayList<Char>, variant: ArrayList<Char>) {
        pref.saveAnswerState(answer)
        pref.saveVariantState(variant)
    }

    override fun getCurrAnswer(): List<String> = pref.getCurrAnswer()

    override fun getCurrVariant(): List<String> = pref.getCurrVariant()
    override fun removeStateGame() {
        pref.removeGameStates()
    }

    override fun setLevelReached(level: Int) {
        pref.setLevelReached(level)
    }

    override fun getSizeQuestion(): Int = repository.getQuestionSize()
}