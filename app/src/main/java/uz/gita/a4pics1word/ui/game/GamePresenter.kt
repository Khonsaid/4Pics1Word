package uz.gita.a4pics1word.ui.game

import android.util.Log
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.data.QuestionData
import uz.gita.a4pics1word.utils.PriceEnum

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {
    private val model = GameModel()
    private var level: Int = model.getLevel()
    private var _answer: ArrayList<Char> = arrayListOf()
    private var _variant: ArrayList<Char> = arrayListOf()
    private val TAG = "TTT"
    private lateinit var answer: String
    private lateinit var variant: String
    private lateinit var images: List<Int>
    private var coin = model.getCoin()

    init {
        Log.d(TAG, "l:$level ")
        if (model.getCurrAnswer().any { it.isNotEmpty() } && model.getCurrVariant().any { it.isNotEmpty() }) {
            val currAnswer = model.getCurrAnswer()
            val currVariant = model.getCurrVariant()
            val questionData = model.getQuestionByIndex(level)
            _variant.clear()
            _answer.clear()

            answer = questionData.answer
            variant = questionData.variants
            images = questionData.images

            view.showQuestionImg(questionData.images)
            view.showAnswerButtonsByLength(currAnswer.size)

            currVariant.forEachIndexed { index, str ->
                if (str.isNotEmpty()) {
                    _variant.add(str[0]) // Convert first character of String to Char
                    if (str != "*" && str != "+") {
                        view.showVariant(str, index)
                    } else {
                        view.showVariant(variant[index].toString(), index)
                        view.hindVariantBtn(index)
                    }
                }
            }

            currAnswer.forEachIndexed { index, str ->
                if (str.isNotEmpty()) {
                    _answer.add(str[0]) // Convert first character of String to Char
                    view.selectAnswerBtn(_answer[index], index, true)
                    if (str == "#") view.unselectAnswerBtn(index)
                    for (i in variant.indices) {
                        if (_variant[i] == '+' && variant[i].toString() == str) {
                            view.selectAnswerBtn(_answer[index], index, false)
                        }
                    }
                }
            }
            view.showCoin(coin.toString())
            view.showLevel(level + 1)
        } else {
            showQuestion()
        }
    }

    private fun showQuestion() {
        if (level < model.getSizeQuestion()) {
            val questionData = model.getQuestionByIndex(level)
            view.showVariants(questionData.variants)
            view.showQuestionImg(questionData.images)
            view.showAnswerButtonsByLength(questionData.answer.length)
            view.showLevel(level + 1)
            view.showCoin(coin.toString())

            adjustStringBuilder(questionData);

            answer = questionData.answer
            variant = questionData.variants
            images = questionData.images
        } else {
            view.showToast("Game over")
        }
    }

    private fun adjustStringBuilder(questionData: QuestionData) {
        _variant.clear()
        _answer.clear()
        val currVariant = questionData.variants
        for (i in currVariant.indices) {
            _variant.add(currVariant[i])
        }
        for (i in 0 until questionData.answer.length) {
            _answer.add('#')
        }
    }

    override fun clickVariantBtn(index: Int) {
        var answerIndex = _answer.indexOf('#')
        if (answerIndex != -1) {
            val ch = _variant[index]
            _variant[index] = '*'
            _answer[answerIndex] = ch

            view.hindVariantBtn(index)
            view.selectAnswerBtn(ch, answerIndex, true)
        }
        answerIndex = _answer.indexOf('#')
        if (_answer.joinToString("") == answer) {
            level++
            coin += PriceEnum.WIN_COIN.price
            model.removeStateGame()

            view.showWinLayout { confirm ->
                if (confirm) {
                    showQuestion()
                }
            }
        } else if (answerIndex == -1) {
            view.renderViews()
//            view.showToast("Javob noto'g'ri")
        }
    }

    override fun clickAnswerBtn(index: Int) {
        var indexVariant = -1//checkAns(variant.indexOf(_answer[index]))
        for (i in variant.indices) {
            if (_answer[index] == variant[i] && _variant[i] != variant[i]) {
                indexVariant = i
                break
            }
        }
        if (indexVariant != -1) {
            view.unselectAnswerBtn(index)
            view.showVariantBtn(indexVariant)
            _variant[indexVariant] = _answer[index]
            _answer[index] = '#'
        }
    }

    override fun openSelectedImg(index: Int) {
        when (index) {
            1 -> view.showSelectedImg(images[0])
            2 -> view.showSelectedImg(images[1])
            3 -> view.showSelectedImg(images[2])
            4 -> view.showSelectedImg(images[3])
            5 -> view.showSelectedImg(index)
        }
    }

    private fun checkAns(indexVariant: Int): Int {
        var currIndex = indexVariant

        while (currIndex != -1 && _variant[currIndex] != '*') {
            currIndex = variant.indexOf(_variant[currIndex], currIndex + 1)
        }
        return currIndex
    }

    override fun clickOpenAnswerBtn() {
        view.showDialog(R.string.open_correct_letter, PriceEnum.OPEN_WORD.price.toString()) { confirm ->
            if (confirm && -1 != _answer.indexOf('#')) {
                val freeSpaceAnswer: MutableList<Int> = mutableListOf()
                _answer.forEachIndexed { index, c ->
                    if (c == '#') freeSpaceAnswer.add(index)
                }
                freeSpaceAnswer.randomOrNull()?.let { answerIndex ->
                    var variantIndex = -1
                    for (i in 0 until _variant.size) {
                        if (answer[answerIndex] == _variant[i] && _variant[i] != '#' && _variant[i] != '+') {
                            variantIndex = i
                            break
                        }
                    }
                    if (variantIndex != -1) {
                        view.selectAnswerBtn(answer[answerIndex], answerIndex, false)
                        _answer[answerIndex] = variant[variantIndex]
                        _variant[variantIndex] = '+'
                        view.hindVariantBtn(variantIndex)
                        coin -= PriceEnum.OPEN_WORD.price
                    } else {
                        view.vibrate()
                    }
                }
                val findSpace = _answer.indexOf('#')
                if (findSpace == -1 && _answer.joinToString("") == answer) {
                    view.showWinLayout {
                        level++
                        model.removeStateGame()
                        if (it) {
                            showQuestion()
                        }
                    }
                }
                view.showCoin(coin.toString())
            }
        }
    }

    override fun clickBuyBtn(price: Int): Boolean = price <= coin

    override fun clickRemoveVariantBtn() {
        view.showDialog(R.string.remove_extra_letters, PriceEnum.DELETE_WORD.price.toString()) { confirm ->
            if (confirm && _variant.joinToString("") != answer) {
                val notAnswer = _variant.filter { it != '*' && it != '+' && !answer.contains(it) }

                notAnswer.randomOrNull()?.let { randomChar ->
                    var index = -1
                    do {
                        index = variant.indexOf(randomChar, index + 1)
                    } while (index != -1 && _variant[index] == '+')

                    if (index != -1) {
                        view.hindVariantBtn(index)
                        _variant[index] = '+'
                        coin -= PriceEnum.DELETE_WORD.price
                    } else {
                        view.vibrate()
//                        view.soundError()
                    }
                }
                view.showCoin(coin.toString())
            }
        }
    }

    override fun clickClearBtn() {
        for (index in _answer.indices) {
            if (_answer[index] != '*' && _answer[index] != '+') {
                val indexVariant = checkAns(variant.indexOf(_answer[index]))
                if (indexVariant != -1) {
                    view.unselectAnswerBtn(index)
                    view.showVariantBtn(indexVariant)
                    _variant[indexVariant] = _answer[index]
                    _answer[index] = '#'
                } else {
                    view.vibrate()
//                    view.soundError()
                }
            }
        }
    }

    override fun clickHomeScreen() {
        view.openHomeScreen()
    }

    override fun onStop() {
        model.saveLevel(level)
        model.saveCoin(coin)
        model.saveCurrState(_answer, _variant)
        Log.d(TAG, "onStop: $level")
        model.setLevelReached(level)
    }
}