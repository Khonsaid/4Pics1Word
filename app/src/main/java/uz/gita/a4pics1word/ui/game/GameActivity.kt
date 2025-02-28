package uz.gita.a4pics1word.ui.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.imageview.ShapeableImageView
import render.animations.Attention
import render.animations.Render
import render.animations.Zoom
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.utils.GameSettings

@SuppressLint("SetTextI18n")
class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var presenter: GameContract.Presenter
    private lateinit var gameSettings: GameSettings
    private val variantButtons: MutableList<Button> = mutableListOf()
    private val answerButtons: MutableList<Button> = mutableListOf()
    private val levelTV: TextView by lazy { findViewById(R.id.tv_level) }
    private val img1: ShapeableImageView by lazy { findViewById(R.id.img1) }
    private val img2: ShapeableImageView by lazy { findViewById(R.id.img2) }
    private val img3: ShapeableImageView by lazy { findViewById(R.id.img3) }
    private val img4: ShapeableImageView by lazy { findViewById(R.id.img4) }
    private val img5: ShapeableImageView by lazy { findViewById(R.id.img_selected) }
    private val tvCoin: AppCompatTextView by lazy { findViewById(R.id.tv_coin) }
    private lateinit var render: Render
    private val TAG = "TTT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gameSettings = GameSettings.getInstance(this)
        render = Render(this)

        loadViews()
        presenter = GamePresenter(this)
        addClickEvents()
    }

    private fun loadViews() {
        val containerAnswer = findViewById<LinearLayout>(R.id.container_answers)
        for (i in 0 until containerAnswer.childCount) {
            answerButtons.add(containerAnswer.getChildAt(i) as AppCompatButton)
        }

        val containerVariant1 = findViewById<LinearLayout>(R.id.container_variants1)
        for (i in 0 until containerVariant1.childCount) {
            variantButtons.add(containerVariant1.getChildAt(i) as AppCompatButton)
        }

        val containerVariant2 = findViewById<LinearLayout>(R.id.container_variants2)
        for (i in 0 until containerVariant2.childCount) {
            variantButtons.add(containerVariant2.getChildAt(i) as AppCompatButton)
        }
    }

    private fun addClickEvents() {
        answerButtons.forEachIndexed { index, button ->
            button.tag = index
            button.setOnClickListener {
                presenter.clickAnswerBtn(it.tag as Int)
            }
        }
        variantButtons.forEachIndexed { index, button ->
            button.tag = index
            button.setOnClickListener {
                presenter.clickVariantBtn(it.tag as Int)
            }
        }

        findViewById<AppCompatImageView>(R.id.open_answer).setOnClickListener { presenter.clickOpenAnswerBtn() }
        findViewById<AppCompatImageView>(R.id.remove_variant).setOnClickListener { presenter.clickRemoveVariantBtn() }
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener { presenter.clickHomeScreen() }
        findViewById<AppCompatImageView>(R.id.clear_answer).setOnClickListener { presenter.clickClearBtn() }

        img1.setOnClickListener { presenter.openSelectedImg(1) }
        img2.setOnClickListener { presenter.openSelectedImg(2) }
        img3.setOnClickListener { presenter.openSelectedImg(3) }
        img4.setOnClickListener { presenter.openSelectedImg(4) }
        img5.setOnClickListener { presenter.openSelectedImg(5) }
    }

    override fun showAnswerButtonsByLength(length: Int) {
        for (i in 0 until length) {
            Log.d(TAG, "showAnswerButtonsByLength: $length")
            answerButtons[i].visibility = View.VISIBLE
            answerButtons[i].text = ""
            answerButtons[i].isClickable = false
            answerButtons[i].setBackgroundResource(R.drawable.bg_place_answer)
            animZoomIn(answerButtons[i])
        }
        for (i in length until 6) {
            answerButtons[i].visibility = View.GONE
        }
    }

    override fun showQuestionImg(images: List<Int>) {
        img1.setImageResource(images[0])
        img2.setImageResource(images[1])
        img3.setImageResource(images[2])
        img4.setImageResource(images[3])
        img5.visibility = View.GONE
    }

    override fun showVariants(variant: String) {
        for (i in 0 until 12) {
            variantButtons[i].visibility = View.VISIBLE
            variantButtons[i].text = variant[i].toString()
            animZoomIn(variantButtons[i])
        }
    }

    override fun showVariantBtn(index: Int) {
        variantButtons[index].visibility = View.VISIBLE
        animZoomIn(variantButtons[index])
    }

    override fun hindVariantBtn(index: Int) {
        variantButtons[index].visibility = View.INVISIBLE
        animZoomOut(variantButtons[index])
    }

    override fun selectAnswerBtn(ch: Char, index: Int, clickable: Boolean) {
        if (clickable) answerButtons[index].setBackgroundResource(R.drawable.bg_btn_answer)
        else answerButtons[index].setBackgroundResource(R.drawable.bg_btn_open_word)
        answerButtons[index].text = ch.toString()
        answerButtons[index].isClickable = clickable
        animZoomIn(answerButtons[index])
    }

    override fun unselectAnswerBtn(index: Int) {
        answerButtons[index].text = ""
        answerButtons[index].isClickable = false
        animZoomOut(answerButtons[index])
        answerButtons[index].setBackgroundResource(R.drawable.bg_place_answer)
    }

    override fun showSelectedImg(img: Int) {
        if (img5.isVisible) img5.visibility = View.GONE
        else {
            img5.visibility = View.VISIBLE
            img5.setImageResource(img)
        }
    }

    override fun showCoin(coin: String) {
        tvCoin.text = coin
    }

    override fun showLevel(level: Int) {
        levelTV.text = level.toString()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun renderViews() {
        gameSettings.vibrateHeavyClick()
        answerButtons.forEach { renderViewWooble(it) }
    }

    private fun renderViewWooble(button: Button) {
        render.setAnimation(Attention.Wobble(button))
        render.setDuration(500)
        render.start()
    }

    private fun animZoomOut(button: Button) {
        render.setAnimation(Zoom.InUp(button))
        render.setDuration(200)
        render.start()
    }

    private fun animZoomIn(button: Button) {
        render.setAnimation(Zoom.InDown(button))
        render.setDuration(200)
        render.start()
    }

    @SuppressLint("InflateParams")
    override fun showDialog(dialog: Int, price: String, onConfirm: (Boolean) -> Unit) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_query, null, false)
        val dialogGame = Dialog(this)
        dialogGame.setContentView(view)
        dialogGame.window?.apply {
            setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        view.findViewById<AppCompatTextView>(R.id.tv_dialog).setText(dialog)
        view.findViewById<AppCompatImageButton>(R.id.btn_back_dialog).setOnClickListener { dialogGame.dismiss() }
        view.findViewById<AppCompatButton>(R.id.btn_coin).apply {
            text = price
            setOnClickListener {
                if (presenter.clickBuyBtn(price.toInt())) {
                    onConfirm(true)
                    dialogGame.dismiss()
                } else {
                    renderViewWooble(this)
                    showToast("Not enough funds!")
                    onConfirm(false)
                    soundError()
                    vibrate()
                }
            }
        }
        dialogGame.show()
    }

    @SuppressLint("InflateParams")
    override fun showWinLayout(onConfirm: (Boolean) -> Unit) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_win, null, false)
        val dialogWin = Dialog(this)
        dialogWin.setContentView(view)
        dialogWin.window?.apply {
            setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialogWin.setCancelable(false)
        vibrate()
        view.findViewById<AppCompatImageButton>(R.id.btn_back_home).setOnClickListener {
            dialogWin.dismiss()
            finish()
        }
        view.findViewById<AppCompatButton>(R.id.btn_next_level).setOnClickListener {
            onConfirm(true)
            dialogWin.dismiss()
        }
        dialogWin.show()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStop()
    }

    override fun openHomeScreen() {
        finish()
    }

    override fun vibrate() {
        gameSettings.vibrateHeavyClick()
    }

    override fun showVariant(ch: String, index: Int) {
        variantButtons[index].text = ch
        animZoomIn(variantButtons[index])
    }

    override fun hindAnswerBtn(index: Int) {
        answerButtons[index].visibility = View.GONE
        animZoomOut(answerButtons[index])
    }

    override fun soundError() {
        gameSettings.playSound(gameSettings.errorSound)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}
