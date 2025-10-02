package sr.otaryp.tesatyla.presentation.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sr.otaryp.tesatyla.data.content.InspirationRepository
import sr.otaryp.tesatyla.databinding.FragmentArticleBinding
import sr.otaryp.tesatyla.databinding.ItemArticleSummaryBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderArticles()
    }

    private fun renderArticles() {
        val articles = InspirationRepository.getArticles()

        binding.articleContainer.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        articles.forEach { article ->
            val itemBinding = ItemArticleSummaryBinding.inflate(inflater, binding.articleContainer, false)
            itemBinding.textArticleTitle.text = article.title
            itemBinding.textArticleContent.text = article.content
            binding.articleContainer.addView(itemBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
