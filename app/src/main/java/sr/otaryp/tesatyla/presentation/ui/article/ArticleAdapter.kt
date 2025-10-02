package sr.otaryp.tesatyla.presentation.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.content.InspirationRepository

class ArticleAdapter(
    private val onItemClick: (InspirationRepository.Article) -> Unit,
    private val onContinueClick: (InspirationRepository.Article) -> Unit
) : ListAdapter<InspirationRepository.Article, ArticleAdapter.VH>(Diff()) {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCover: ImageView = itemView.findViewById(R.id.ivCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSnippet: TextView = itemView.findViewById(R.id.tvSnippet)
        val btnContinue: AppCompatButton = itemView.findViewById(R.id.btnContinue)
        val cardRoot: View = itemView.findViewById(R.id.cardRoot)
    }

    private class Diff : DiffUtil.ItemCallback<InspirationRepository.Article>() {
        override fun areItemsTheSame(
            oldItem: InspirationRepository.Article,
            newItem: InspirationRepository.Article
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: InspirationRepository.Article,
            newItem: InspirationRepository.Article
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.tvTitle.text = item.title
        holder.tvSnippet.text = item.content
        holder.ivCover.setImageResource(InspirationRepository.imageFor(item.id))

        holder.cardRoot.setOnClickListener { onItemClick(item) }
        holder.btnContinue.setOnClickListener { onContinueClick(item) }
    }
}
