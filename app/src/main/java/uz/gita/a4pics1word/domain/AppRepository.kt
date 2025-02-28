package uz.gita.a4pics1word.domain

import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.data.QuestionData

class AppRepository private constructor() {
    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!::instance.isInitialized) instance = AppRepository()
            return instance
        }
    }

    private val questions: ArrayList<QuestionData> = arrayListOf()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        questions.add(QuestionData(listOf(R.drawable.fri3, R.drawable.fri1, R.drawable.fri2, R.drawable.burger2), "fri", "arhdgtqfosih"))
        questions.add(QuestionData(listOf(R.drawable.italia, R.drawable.dodo, R.drawable.cola, R.drawable.box), "pizza", "arhzgtpfosiz"))
        questions.add(
            QuestionData(
                listOf(R.drawable.burger1, R.drawable.fri2, R.drawable.burger2, R.drawable.cola),
                "burger",
                "arbdguqroeih"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.think1, R.drawable.think2, R.drawable.think3, R.drawable.think4),
                "think",
                "nrhkgtpfosiz"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.wheel1, R.drawable.wheel2, R.drawable.wheel3, R.drawable.wheel4),
                "wheel",
                "phosarwzetel"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.dream1, R.drawable.dream2, R.drawable.dream3, R.drawable.dream4),
                "dream",
                "umrarbdgoeih"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.heart1, R.drawable.heart2, R.drawable.heart3, R.drawable.heart4),
                "heart",
                "tgumarbroeih"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.white1, R.drawable.white2, R.drawable.white3, R.drawable.white4),
                "white",
                "houwasetodin"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.tuna1, R.drawable.tuna2, R.drawable.tuna3, R.drawable.tuna4),
                "tuna",
                "ethousodinwa"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.sport1, R.drawable.sport2, R.drawable.sport3, R.drawable.sport4),
                "sport",
                "toupaseroein"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.loud1, R.drawable.loud2, R.drawable.loud3, R.drawable.loud4),
                "loud",
                "ethousodilwa"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.blue1, R.drawable.blue2, R.drawable.blue3, R.drawable.blue4),
                "blue",
                "etgousbdrlwa"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.driver1, R.drawable.driver2, R.drawable.driver3, R.drawable.driver4),
                "driver",
                "itroevbdrlwa"
            )
        )
        questions.add(
            QuestionData(
                listOf(R.drawable.magic1, R.drawable.magic2, R.drawable.magic3, R.drawable.magic4),
                "magic",
                "morigvbcrlwa"
            )
        )
    }

    fun getQuestionByIndex(index: Int) = questions[index]
    fun getQuestionSize(): Int = questions.size
    fun getQuestions(): ArrayList<QuestionData> = questions
}