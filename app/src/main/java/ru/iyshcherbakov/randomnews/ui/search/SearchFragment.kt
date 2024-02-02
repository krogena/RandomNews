package ru.iyshcherbakov.randomnews.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.iyshcherbakov.randomnews.databinding.FragmentSearchBinding
import ru.iyshcherbakov.randomnews.ui.adapters.NewsAdapter
import ru.iyshcherbakov.randomnews.utils.Resource

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel by viewModels<SearchViewModel>()
    lateinit var newsAdapter: NewsAdapter



    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job: Job? = null
        binding.editText.addTextChangedListener { text: Editable? ->  
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
initAdapter()
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    binding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.d("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    binding.pagSearchProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.searchNewsAdapter.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}