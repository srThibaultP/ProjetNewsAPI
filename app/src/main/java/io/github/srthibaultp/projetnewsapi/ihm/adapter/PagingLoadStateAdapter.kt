package io.github.srthibaultp.projetnewsapi.ihm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.srthibaultp.projetnewsapi.databinding.LayoutLoadStateFoofterBinding


class PagingLoadStateAdapter :
    LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder
    {
        val binding = LayoutLoadStateFoofterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) { holder.bindData(loadState) }

    inner class LoadStateViewHolder(private val binding: LayoutLoadStateFoofterBinding) :
        RecyclerView.ViewHolder(binding.root)
    { fun bindData(loadState: LoadState) { binding.apply { root.isVisible = loadState is LoadState.Error } } }
}