package uz.gita.a4pics1word.ui.levels.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.helper.widget.Layer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import uz.gita.a4pics1word.R
import uz.gita.a4pics1word.data.QuestionData

class LevelsAdapter(private val list: ArrayList<QuestionData>, private val levelReached: Int) :
    RecyclerView.Adapter<LevelsAdapter.LevelsViewHolder>() {
    private var onClickLevelListener: ((Int) -> Unit)? = null
    fun onClickLevelListener(listener: (Int) -> Unit) {
        onClickLevelListener = listener
    }

    inner class LevelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img1 = view.findViewById<ShapeableImageView>(R.id.img1_levels)
        private val img2 = view.findViewById<ShapeableImageView>(R.id.img2_levels)
        private val img3 = view.findViewById<ShapeableImageView>(R.id.img3_levels)
        private val img4 = view.findViewById<ShapeableImageView>(R.id.img4_levels)
        private val layer = view.findViewById<Layer>(R.id.layer)
        private val llBtn = view.findViewById<AppCompatButton>(R.id.ll_level_item)

        init {
            llBtn.setOnClickListener { onClickLevelListener?.invoke(adapterPosition) }
        }

        fun bind(data: QuestionData, position: Int) {
            img1.setImageResource(data.images[0])
            img2.setImageResource(data.images[1])
            img3.setImageResource(data.images[2])
            img4.setImageResource(data.images[3])
            llBtn.text = "Level ${position + 1}"
            if (position <= levelReached) {
                layer.visibility = View.GONE
                llBtn.setBackgroundResource(R.drawable.bg_btn_play)
            } else {
                layer.visibility = View.VISIBLE
                llBtn.setBackgroundResource(R.drawable.bg_level_text)
            }
            llBtn.isClickable = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsViewHolder =
        LevelsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_levels, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LevelsViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}