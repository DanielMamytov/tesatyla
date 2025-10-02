package sr.otaryp.tesatyla.presentation.ui.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.databinding.ItemLessonBinding

class LessonAdapter(
    private val onLessonSelected: (LessonListItem) -> Unit
) : ListAdapter<LessonListItem, LessonAdapter.LessonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position), onLessonSelected)
    }

    class LessonViewHolder(
        private val binding: ItemLessonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LessonListItem, onLessonSelected: (LessonListItem) -> Unit) {
            binding.titleTv.text = item.title
            binding.textDescription.text = item.description

            val iconRes = if (item.isCompleted) {
                R.drawable.shield_bg
            } else {
                R.drawable.shield_gray
            }
            binding.levelIm.setImageResource(iconRes)

            val buttonTextRes = if (item.isCompleted) {
                R.string.lesson_action_repeat
            } else {
                R.string.lesson_action_continue
            }
            binding.btnContinue.setText(buttonTextRes)

            binding.btnContinue.setOnClickListener { onLessonSelected(item) }
            binding.root.setOnClickListener { onLessonSelected(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<LessonListItem>() {
        override fun areItemsTheSame(oldItem: LessonListItem, newItem: LessonListItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonListItem, newItem: LessonListItem): Boolean =
            oldItem == newItem
    }
}


data class LessonListItem(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)
