package uz.gita.a4pics1word.ui.main

interface MainContract {
    interface Model {
        fun getCoin(): Int
        fun getCurrQuestion(): List<Int>
        fun getLevel(): Int
        fun saveLevel(level: Int)
        fun getChangedLevel(): Int
        fun getQuestionSize(): Int
        fun setCurrLevel(level: Int)
        fun saveChangedLevel(level: Int)
        fun removeState()
        fun getLevelReached(): Int
    }

    interface View {
        fun showDialogLevel(onResult: (Boolean) -> Unit)
        fun openGameScreen()
        fun loadData(images: List<Int>, coin: Int, level: Int)
        fun showInfoDialog()
        fun showToast(message: String)
    }

    interface Presenter {
        fun clickPlayBtn()
        fun clickSettingsBtn()
        fun clickInfo()
        fun loadData()
        fun currLevel(level: Int)
        fun getLevel(): Int
    }
}