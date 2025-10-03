package sr.otaryp.tesatyla.presentation.ui.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

            when {
                item.isCompleted -> {
                    binding.tvStepStatus.visibility = View.VISIBLE
                    binding.tvStepStatus.setText(R.string.lesson_step_completed)
                    binding.btnStepAction.setText(R.string.lesson_step_review)
                    binding.btnStepAction.isEnabled = true
                    binding.btnStepAction.alpha = 1f
                    binding.ivStepStatus.setImageResource(R.drawable.shield_bg)
                    binding.cardContainer.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.step_card_background_completed)
                    )
                    binding.cardContainer.strokeColor =
                        ContextCompat.getColor(context, R.color.step_card_stroke_completed)
                }

                item.isLocked -> {
                    binding.tvStepStatus.visibility = View.GONE
                    binding.btnStepAction.setText(R.string.lesson_step_locked)
                    binding.btnStepAction.isEnabled = false
                    binding.btnStepAction.alpha = 0.6f
                    binding.ivStepStatus.setImageResource(R.drawable.shield_gray)
                    binding.cardContainer.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.step_card_background)
                    )
                    binding.cardContainer.strokeColor =
                        ContextCompat.getColor(context, R.color.step_card_stroke)
                }

                else -> {
                    binding.tvStepStatus.visibility = View.GONE
                    binding.btnStepAction.setText(R.string.lesson_step_open)
                    binding.btnStepAction.isEnabled = true
                    binding.btnStepAction.alpha = 1f
                    binding.ivStepStatus.setImageResource(R.drawable.shield_gray)
                    binding.cardContainer.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.step_card_background)
                    )
                    binding.cardContainer.strokeColor =
                        ContextCompat.getColor(context, R.color.step_card_stroke)
                }
            }

            binding.btnStepAction.setOnClickListener {
                if (!item.isLocked) {
                    onStepSelected(item)
                }
            }
            binding.root.setOnClickListener {
                if (!item.isLocked) {
                    onStepSelected(item)
                }
            }

        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<LessonStepItem>() {
        override fun areItemsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonStepItem, newItem: LessonStepItem): Boolean =
            oldItem == newItem
    }
}
