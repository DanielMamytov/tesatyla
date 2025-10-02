package sr.otaryp.tesatyla.presentation.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.content.InspirationRepository
import sr.otaryp.tesatyla.databinding.FragmentArticleBinding
import sr.otaryp.tesatyla.databinding.ItemArticleSummaryBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter

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

        adapter = ArticleAdapter(
            onItemClick = { article -> openDetail(article.id) },
            onContinueClick = { article -> openDetail(article.id) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
//        binding.recyclerView.addItemDecoration(VerticalSpaceItemDecoration(6))

        // подаем 10 статей в список
        adapter.submitList(InspirationRepository.getArticles())
    }

    private fun openDetail(articleId: Int) {
        // Если используешь SafeArgs:
        // val action = ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(articleId)
        // findNavController().navigate(action)

//        // Универсально через Bundle:
//        val args = bundleOf("article_id" to articleId)
//        findNavController().navigate(R.id.action_articleFragment_to_articleDetailFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

