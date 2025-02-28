package uz.gita.a4pics1word.ui.levels

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.data.QuestionData
import uz.gita.a4pics1word.ui.levels.adapter.LevelsAdapter
import kotlin.math.abs

class LevelsScreen : Fragment(R.layout.screen_levell), LevelsContract.View {
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: LevelsAdapter
    private lateinit var presenter: LevelsContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager2 = view.findViewById(R.id.vp_game)
        presenter = LevelsPresenter(this)

        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                presenter.clickBtnPlay(position)
            }
        })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }

    override fun openHomeScreen() {
        parentFragmentManager.popBackStack()
    }

    override fun submitData(list: ArrayList<QuestionData>, level: Int, currLevel: Int) {
        adapter = LevelsAdapter(list, level)
        viewPager2.adapter = adapter
        viewPager2.setCurrentItem(currLevel, true)
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }
}