package sr.otaryp.tesatyla.presentation.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.content.InspirationRepository
import sr.otaryp.tesatyla.databinding.FragmentArticleBinding
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private val allArticles by lazy { InspirationRepository.getArticles() }

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
        setupSettingsButton()
        setupSystemBackNavigation()

        // стартовый список
        showArticles(allArticles)
        binding.titleTv.applyVerticalGradient()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(
            onItemClick = { article -> openDetail(article) },
            onContinueClick = { article -> openDetail(article) }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ArticleFragment.adapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            // addItemDecoration(VerticalSpaceItemDecoration(6))
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.doAfterTextChanged { text ->
            val query = text?.toString().orEmpty().trim()
            val filtered = if (query.isEmpty()) {
                allArticles
            } else {
                allArticles.filter { a ->
                    a.title.contains(query, ignoreCase = true) ||
                            a.content.contains(query, ignoreCase = true)
                }
            }
            showArticles(filtered)
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            navigateHome()
        }
    }

    private fun setupSystemBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateHome()
        }
    }

    private fun setupSettingsButton() {
        binding.imageSettings.setOnClickListener {
            findNavController().navigate(R.id.action_global_settingsFragment)
        }
    }

    private fun showArticles(articles: List<InspirationRepository.Article>) {
        adapter.submitList(articles)
        binding.recyclerView.isVisible = articles.isNotEmpty()
        binding.textEmptyState.isVisible = articles.isEmpty()
    }

    private fun openDetail(article: InspirationRepository.Article) {
        // Вариант с SafeArgs (как у тебя было ранее)
        val directions = ArticleFragmentDirections.actionNavArticlesToArticleDetailFragment(
            articleTitle = article.title,
            articleContent = article.content,
            articleImageRes = InspirationRepository.imageFor(article.id)
        )
        findNavController().navigate(directions)

        // Если нужен переход по ID, раскомментируй этот вариант и поправь граф:
//         val action = ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(article.id)
//         findNavController().navigate(action)
    }

    private fun navigateHome() {
        val navController = findNavController()
        if (!navController.popBackStack(R.id.nav_home, false)) {
            navController.navigate(R.id.nav_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
