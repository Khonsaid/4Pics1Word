package uz.gita.a4pics1word.ui.levels

import android.util.Log

class LevelsPresenter(private val view: LevelsContract.View) : LevelsContract.Presenter {
    private val model: LevelsContract.Model = LevelsModel()

    init {
        view.submitData(model.getQuestions(), model.getLevelReached(), model.getLevel())
    }

    override fun clickBtnX() {
        view.openHomeScreen()
    }

    override fun clickBtnPlay(level: Int) {
        model.saveLevel(level)
    }

    override fun loadData() {
        view.submitData(model.getQuestions(), model.getLevelReached(), model.getLevel())
    }
}