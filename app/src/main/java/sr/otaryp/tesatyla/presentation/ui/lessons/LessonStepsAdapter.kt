package sr.otaryp.tesatyla.presentation.ui.lessons

import android.view.LayoutInflater
import android.view.View
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
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position), onStepSelected)
    }

    class StepViewHolder(
        private val binding: LesssonStepItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LessonStepItem, onStepSelected: (LessonStepItem) -> Unit) {
            binding.tvStepTitle.text = item.title
            binding.tvStepPreview.text = item.theoryPreview

            if (item.isCompleted) {
                binding.tvStepStatus.visibility = View.VISIBLE
                binding.tvStepStatus.setText(R.string.lesson_step_completed)
                binding.btnOpenStep.setText(R.string.lesson_step_review)
            } else {
                binding.tvStepStatus.visibility = View.GONE
                binding.btnOpenStep.setText(R.string.lesson_step_open)
            }

            binding.btnOpenStep.setOnClickListener { onStepSelected(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<LessonStepItem>() {
        override fun areItemsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem == newItem
    }
}
