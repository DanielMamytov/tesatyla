package sr.otaryp.tesatyla.presentation.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingSlide(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val illustrationRes: Int
)
