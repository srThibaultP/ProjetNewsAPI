package io.github.srthibaultp.projetnewsapi.ihm.navigateur

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.srthibaultp.projetnewsapi.R
import io.github.srthibaultp.projetnewsapi.databinding.FragmentNavigateurBinding
import io.github.srthibaultp.projetnewsapi.ihm.news.NewsViewModel

@AndroidEntryPoint
class NavigateurFragment : Fragment(R.layout.fragment_navigateur)
{
    private val viewModel: NewsViewModel by viewModels()
    private val navigationArgs: NavigateurFragmentArgs by navArgs()
    private var firstTime = 0

    private lateinit var binding: FragmentNavigateurBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNavigateurBinding.bind(view)

        displayArticle()
        binding.fabSaveArticle.apply {
            setOnClickListener {
                val messageSaved: String = getString(R.string.article_already_saved)
                val messageAlreadySaved: String = getString(R.string.article_saved)

                if (navigationArgs.article.isSaved or (firstTime > 0))
                {
                    setImageResource(R.drawable.ic_bookmark_selected)
                    Snackbar.make(this, messageSaved, Snackbar.LENGTH_LONG).show()
                }
                else Snackbar.make(this, messageAlreadySaved, Snackbar.LENGTH_LONG).show()

                viewModel.saveArticleClicked(navigationArgs.article)
                setImageResource(R.drawable.ic_bookmark_selected)
                firstTime++
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayArticle()
    {
        binding.webView.apply {
            webViewClient = webViewCustomClient
            loadUrl(navigationArgs.article.url)
            settings.javaScriptEnabled = true
        }
    }

    private val webViewCustomClient = object : WebViewClient()
    {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) { super.onPageStarted(view, url, favicon) }
        override fun onPageFinished(view: WebView?, url: String?) { super.onPageFinished(view, url) }
    }
}