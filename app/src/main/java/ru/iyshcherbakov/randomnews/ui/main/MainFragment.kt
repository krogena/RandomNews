package ru.iyshcherbakov.randomnews.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.iyshcherbakov.randomnews.R
import ru.iyshcherbakov.randomnews.databinding.FragmentDetailsBinding
import ru.iyshcherbakov.randomnews.databinding.FragmentMainBinding
import ru.iyshcherbakov.randomnews.ui.adapters.NewsAdapter
import ru.iyshcherbakov.randomnews.utils.Resource

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    lateinit var binding: FragmentMainBinding

    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.newsLiveData.observe(viewLifecycleOwner){responce ->
            when(responce){
                is Resource.Success -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.d("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    binding.pagProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.newsAdapter.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}