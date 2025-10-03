package sr.otaryp.tesatyla.presentation.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * A RecyclerView implementation that expands to show all of its content and
 * delegates vertical scrolling to the parent container. This is useful when
 * the RecyclerView is placed inside another scrollable view (e.g. ScrollView)
 * and we still want to render the full list of items.
 */
class NonScrollableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandedHeightSpec = MeasureSpec.makeMeasureSpec(
            Int.MAX_VALUE shr 2,
            MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, expandedHeightSpec)
    }

    override fun canScrollVertically(direction: Int): Boolean = false
}
