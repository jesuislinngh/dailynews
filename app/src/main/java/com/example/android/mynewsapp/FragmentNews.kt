package com.example.android.mynewsapp

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.myapplication.databinding.FragmentNewsBinding


/**
 * A simple [Fragment] subclass.
 */
class FragmentNews : Fragment() {
    private val TAG = FragmentNews::class.java.canonicalName
    private lateinit var viewModel: DailyNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(
                this,
                DailyNewsViewModel.FACTORY(this.requireContext().applicationContext as Application))[DailyNewsViewModel::class.java]

        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.newsFeed.adapter =
            AdapterNewsFeed(AdapterNewsFeed.OnClickListener {
                val url = it.url
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            })

        return binding.root
    }
}
