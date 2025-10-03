package sr.otaryp.tesatyla.presentation.ui.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.databinding.ItemSkillProgressBinding

class SkillProgressAdapter(
    private val onSkillSelected: (SkillProgressItem) -> Unit,
) : ListAdapter<SkillProgressItem, SkillProgressAdapter.SkillProgressViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSkillProgressBinding.inflate(inflater, parent, false)
        return SkillProgressViewHolder(binding, onSkillSelected)
    }

    override fun onBindViewHolder(holder: SkillProgressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SkillProgressViewHolder(
        private val binding: ItemSkillProgressBinding,
        private val onSkillSelected: (SkillProgressItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SkillProgressItem) {
            val context = binding.root.context
            binding.textSkillTitle.text = item.title
            binding.textSkillLessons.text = context.getString(
                R.string.progress_lessons_completed,
                item.completedLessons,
                item.totalLessons,
            )
            binding.textSkillPercent.text = context.getString(
                R.string.progress_percentage_format,
                item.completionPercent,
            )
            binding.skillProgressBar.max = 100
            binding.skillProgressBar.progress = item.completionPercent
            val statusDrawable = if (item.isComplete) {
                R.drawable.shield_bg
            } else {
                R.drawable.shield_gray
            }
            binding.statusIm.setImageResource(statusDrawable)

            binding.root.setOnClickListener { onSkillSelected(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<SkillProgressItem>() {
        override fun areItemsTheSame(oldItem: SkillProgressItem, newItem: SkillProgressItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SkillProgressItem, newItem: SkillProgressItem): Boolean =
            oldItem == newItem
    }
}

