package io.github.srthibaultp.projetnewsapi.ihm.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.srthibaultp.projetnewsapi.R
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.databinding.FragmentNewsBinding
import io.github.srthibaultp.projetnewsapi.ihm.adapter.NewsAdapter
import io.github.srthibaultp.projetnewsapi.ihm.adapter.PagingLoadStateAdapter


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnArticleClickListener
{

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter(this)
    private lateinit var searchView: SearchView

    private var _binding: FragmentNewsBinding? = null

    private val binding: FragmentNewsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        initRecyclerView()
        handleApiData()
        applyRetryOption()
        setHasOptionsMenu(true)
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        setHasFixedSize(true)

        itemAnimator = null
        adapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = loadStateAdapter,
            footer = loadStateAdapter
        )
    }

    private val loadStateAdapter = PagingLoadStateAdapter()

    private fun handleApiData()
    {
        viewModel.articles.observe(viewLifecycleOwner)
        {
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun applyRetryOption()
    {
        newsAdapter.addLoadStateListener {
            binding.apply {
                recyclerView.isVisible = true

                swipeToRefresh.apply {
                    setOnRefreshListener {
                        newsAdapter.retry()
                        isRefreshing = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_news_fragment, menu)

        val searchItem = menu.findItem(R.id.action_search_news)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.currentQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty())
        {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, true)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean =
                if (query != null)
                {
                    searchView.clearFocus()
                    searchForArticles(query)
                    true
                }
                else false

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun searchForArticles(newText: String)
    {
        viewModel.searchArticlesPaging(newText)
        binding.recyclerView.scrollToPosition(0)
    }

    override fun onArticleClicked(article: Article)
    {
        val action = NewsFragmentDirections.globalActionNavigateToDetailsFragment(article)
        findNavController().navigate(action)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
        searchView.setOnQueryTextListener(null)
    }
}