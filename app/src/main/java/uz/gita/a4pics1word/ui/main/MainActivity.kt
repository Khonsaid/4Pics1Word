package uz.gita.a4pics1word.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.ui.game.GameActivity
import uz.gita.a4pics1word.ui.levels.LevelsScreen

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter
    private val tvCoin: TextView by lazy { findViewById(R.id.tv_coi_main) }
    private var isToastShowing = false
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = MainPresenter(this)
        overlay = findViewById(R.id.overlay)
        findViewById<AppCompatButton>(R.id.btn_play).setOnClickListener {
            presenter.clickPlayBtn()
        }
        findViewById<AppCompatImageButton>(R.id.btn_info).setOnClickListener {
            presenter.clickInfo()
        }
        presenter.loadData()

        supportFragmentManager.beginTransaction().add(R.id.grid_layout, LevelsScreen()).commit()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.loadData()
    }

    override fun showToast(message: String) {
        if (isToastShowing) return
        val view = LayoutInflater.from(this).inflate(R.layout.item_toast, null, false)
        view.findViewById<TextView>(R.id.tv_toast).text = message
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
        isToastShowing = true
        Handler(Looper.getMainLooper()).postDelayed({
            isToastShowing = false
        }, 2000)
    }

    override fun showDialogLevel(onResult: (Boolean) -> Unit) {
        val dialog = Dialog(this, R.style.FullScreenDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null, false)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        view.findViewById<TextView>(R.id.tv_exit).text = "Level ${presenter.getLevel()} will be deleted!!! \nDo you agree with that?"

        dialog.setContentView(view)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
        dialog.findViewById<AppCompatButton>(R.id.btn_next_level).setOnClickListener {
            onResult(true)
            dialog.dismiss()
        }
        dialog.findViewById<AppCompatImageButton>(R.id.btn_back_home).setOnClickListener {
            onResult(false)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun openGameScreen() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    override fun loadData(images: List<Int>, coin: Int, level: Int) {
        tvCoin.text = coin.toString()
    }

    override fun showInfoDialog() {
        val dialog = Dialog(this, R.style.FullScreenDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_info, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(view)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
        dialog.findViewById<AppCompatButton>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<AppCompatImageButton>(R.id.btn_back_home_info).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}