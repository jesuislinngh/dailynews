package com.example.android.mynewsapp

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.android.mynewsapp.databinding.FragmentNewsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class FragmentNews : Fragment() {
    private lateinit var viewModel: DailyNewsViewModel
    //private var param1: String? = null
    // var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders
            .of(
                requireActivity(),
                DailyNewsViewModel.FACTORY(this.requireContext().applicationContext as Application)
            )
            .get(DailyNewsViewModel::class.java)

        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.newsFeed.adapter =
            AdapterNewsFeed(AdapterNewsFeed.OnClickListener {
                //viewModel.setDefinition(it.defid)

                // TODO here we'll implement the logic to open the web browser.
            })

        return binding.root
    }

    /* companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentNews.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentNews().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    } */
}
