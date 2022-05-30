package io.github.srthibaultp.projetnewsapi.ihm.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.srthibaultp.projetnewsapi.R
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.databinding.FragmentSavedBinding
import io.github.srthibaultp.projetnewsapi.ihm.adapter.NewsAdapter
import io.github.srthibaultp.projetnewsapi.ihm.news.NewsFragmentDirections

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved), NewsAdapter.OnArticleClickListener
{

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var binding: FragmentSavedBinding

    private val newsAdapter = NewsAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        initRecyclerView()
        observerArticles()
        applySwipeToDelete()
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = newsAdapter
        setHasFixedSize(true)
        itemAnimator = null
    }

    private fun observerArticles() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner) { newsAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(it)) }
    }

    private fun applySwipeToDelete()
    {
        val touchHelper = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.LEFT)
            {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
                {
                    val position = viewHolder.bindingAdapterPosition
                    val currentItem = newsAdapter.getCurrentItem(position)
                    currentItem?.let {
                        viewModel.deleteArticle(it)
                        undoDeletingArticle(it)
                    }
                }
            }

        ItemTouchHelper(touchHelper).attachToRecyclerView(binding.recyclerView)
    }

    override fun onArticleClicked(article: Article)
    {
        val action = NewsFragmentDirections.globalActionNavigateToDetailsFragment(article)
        findNavController().navigate(action)
    }

    private fun undoDeletingArticle(article: Article) {
        Snackbar.make(binding.root, getString(R.string.article_deleted), Snackbar.LENGTH_LONG).apply {
            setAction(getString(R.string.undo)) { viewModel.saveArticle(article) }
            show()
        }
    }
}