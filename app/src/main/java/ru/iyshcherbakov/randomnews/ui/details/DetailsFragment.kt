package ru.iyshcherbakov.randomnews.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import retrofit2.http.Url
import ru.iyshcherbakov.randomnews.R
import ru.iyshcherbakov.randomnews.databinding.FragmentDetailsBinding
import ru.iyshcherbakov.randomnews.databinding.FragmentMainBinding

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding

    private val bundleArgs: DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity)
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let{article ->
            article.urlToImage.let{
                Glide.with(this).load(article.urlToImage).into(binding.headerImage)
            }
            binding.headerImage.clipToOutline= true
            binding.articleDetailsTitle.text = article.title
            binding.articleDetailsDescriptionText.text = article.description
            binding.articleDetailsButton
                .setOnClickListener{
                    try{
                        Intent()
                            .setAction(Intent.ACTION_VIEW)
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                            .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url)}
                                ?.let {
                                    article.url
                                }?: "https://google.com"))
                        .let {
                            ContextCompat.startActivity(requireContext(), it, null)
                        }

                    }catch (e: Exception){
                        Toast.makeText(context, "The device doesn't have any browser to view the document!", Toast.LENGTH_SHORT)
                    }
                }
        }
    }
}