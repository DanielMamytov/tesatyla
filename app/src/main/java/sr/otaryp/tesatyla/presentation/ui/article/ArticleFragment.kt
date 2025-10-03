package sr.otaryp.tesatyla.presentation.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.content.InspirationRepository
import sr.otaryp.tesatyla.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private val allArticles = InspirationRepository.getArticles()

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

        setupRecyclerView()
        setupSearchBar()
        setupBackButton()

        showArticles(allArticles)
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(
            onItemClick = { article -> openDetail(article) },
            onContinueClick = { article -> openDetail(article) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearchBar() {
        binding.searchBar.doAfterTextChanged { text ->
            val query = text?.toString().orEmpty()
            val filtered = if (query.isBlank()) {
                allArticles
            } else {
                allArticles.filter { article ->
                    article.title.contains(query, ignoreCase = true) ||
                        article.content.contains(query, ignoreCase = true)
                }
            }
            showArticles(filtered)
        }
    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.nav_home)
        }
    }

    private fun showArticles(articles: List<InspirationRepository.Article>) {
        adapter.submitList(articles)
        binding.recyclerView.isVisible = articles.isNotEmpty()
        binding.textEmptyState.isVisible = articles.isEmpty()
    }

    private fun openDetail(article: InspirationRepository.Article) {
        val directions = ArticleFragmentDirections.actionNavArticlesToArticleDetailFragment(
            articleTitle = article.title,
            articleContent = article.content
        )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

