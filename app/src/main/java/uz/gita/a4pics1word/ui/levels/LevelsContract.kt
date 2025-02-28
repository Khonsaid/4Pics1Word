package uz.gita.a4pics1word.ui.levels

import uz.gita.a4pics1word.data.QuestionData

interface LevelsContract {
    interface Model {
        fun getLevel(): Int
        fun getQuestions(): ArrayList<QuestionData>
        fun saveLevel(level: Int)
        fun getLevelReached(): Int
    }

    interface View {
        fun openHomeScreen()
        fun submitData(list: ArrayList<QuestionData>, level: Int, curLevel: Int)
    }

    interface Presenter {
        fun clickBtnX()
        fun clickBtnPlay(level: Int)
        fun loadData()
    }
}