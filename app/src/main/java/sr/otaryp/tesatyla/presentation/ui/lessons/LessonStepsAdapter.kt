package sr.otaryp.tesatyla.presentation.ui.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.databinding.LesssonStepItemBinding

class LessonStepsAdapter(
    private val onStepSelected: (LessonStepItem) -> Unit
) : ListAdapter<LessonStepItem, LessonStepsAdapter.StepViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = LesssonStepItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StepViewHolder(binding, onStepSelected)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StepViewHolder(
        private val binding: LesssonStepItemBinding,
        private val onStepSelected: (LessonStepItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LessonStepItem) {
            binding.tvStepTitle.text = item.title
            binding.tvStepPreview.text = item.theoryPreview

            val context = binding.root.context
            val buttonTextRes = if (item.isCompleted) {
                R.string.lesson_step_review
            } else {
                R.string.lesson_step_open
            }
            binding.btnOpenStep.setText(buttonTextRes)
            binding.btnOpenStep.contentDescription = context.getString(buttonTextRes)

            binding.btnOpenStep.setOnClickListener { onStepSelected(item) }
            binding.root.setOnClickListener { onStepSelected(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<LessonStepItem>() {
        override fun areItemsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem == newItem
    }
}
