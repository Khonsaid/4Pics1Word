package uz.gita.a4pics1word.ui.main.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.domain.AppRepository
import uz.gita.a4pics1word.domain.MyPref
import uz.gita.a4pics1word.ui.levels.adapter.LevelsAdapter
import kotlin.math.abs

class LevelDialog(context: Context) : AlertDialog(context) {
    private val pref = MyPref.getInstance()
    private val repository = AppRepository.getInstance()
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: LevelsAdapter
    private var onClickLevelListener: ((Int) -> Unit)? = null

    fun onClickLevelListener(listener: (Int) -> Unit) {
        onClickLevelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.screen_level, null, false)
        setContentView(view)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        viewPager2 = view.findViewById(R.id.vp_game)
        adapter = LevelsAdapter(repository.getQuestions(), pref.getLevelReached())
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setUpTransformer()

        adapter.onClickLevelListener {
            onClickLevelListener?.invoke(it)
        }

        viewPager2.setCurrentItem(pref.getLevelReached(), false)
        view.findViewById<AppCompatImageButton>(R.id.btn_back_home_level).setOnClickListener { dismiss() }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.16f
        }

        viewPager2.setPageTransformer(transformer)
    }
}