package sr.otaryp.tesatyla.presentation.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sr.otaryp.tesatyla.databinding.ItemOnboardingSlideBinding

class OnboardingPagerAdapter(
    private val slides: List<OnboardingSlide>
) : RecyclerView.Adapter<OnboardingPagerAdapter.SlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemOnboardingSlideBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(slides[position])
    }

    override fun getItemCount(): Int = slides.size

    inner class SlideViewHolder(
        private val binding: ItemOnboardingSlideBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(slide: OnboardingSlide) {
            binding.textTitle.setText(slide.titleRes)
            binding.textDescription.setText(slide.descriptionRes)
            binding.imageIllustration.setImageResource(slide.illustrationRes)
        }
    }
}
