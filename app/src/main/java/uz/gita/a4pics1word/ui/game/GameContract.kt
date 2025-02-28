package uz.gita.a4pics1word.ui.game

import uz.gita.a4pics1word.data.QuestionData

interface GameContract {
    interface Model {
        fun getQuestionByIndex(index: Int): QuestionData
        fun saveLevel(level: Int)
        fun getLevel(): Int
        fun getSizeQuestion(): Int
        fun saveCoin(coin: Int)
        fun getCoin(): Int

        fun saveCurrState(answer: ArrayList<Char>, variant: ArrayList<Char>)
        fun getCurrAnswer(): List<String>
        fun getCurrVariant(): List<String>
        fun removeStateGame()
        fun setLevelReached(level: Int)
    }

    interface View {
        fun showAnswerButtonsByLength(length: Int)
        fun showQuestionImg(images: List<Int>)
        fun showVariants(variant: String)
        fun showToast(message: String)
        fun showVariantBtn(index: Int)
        fun openHomeScreen()

        fun showLevel(level: Int)
        fun showSelectedImg(img: Int)
        fun showCoin(coin: String)
        fun showDialog(dialog: Int, price: String, onConfirm: (Boolean) -> Unit)
//        fun clearAnswer(index: Int)

        fun renderViews()
        fun hindVariantBtn(index: Int)
        fun selectAnswerBtn(ch: Char, index: Int, clickable: Boolean)
        fun unselectAnswerBtn(index: Int)
        fun showWinLayout(onConfirm: (Boolean) -> Unit)

        fun vibrate()
        fun soundError()
        fun showVariant(ch: String, index: Int)
        fun hindAnswerBtn(index: Int)
    }

    interface Presenter {
        fun clickVariantBtn(index: Int)
        fun clickAnswerBtn(index: Int)
        fun clickBuyBtn(price: Int): Boolean
        fun openSelectedImg(index: Int)

        fun clickOpenAnswerBtn()
        fun clickRemoveVariantBtn()
        fun clickClearBtn()

        fun clickHomeScreen()
        fun onStop()
    }
}