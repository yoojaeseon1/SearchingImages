package com.android.searchingimages.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.android.searchingimages.R
import com.android.searchingimages.data.ContentRepository
import com.android.searchingimages.databinding.FragmentListBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!



//    private val viewModel: MainViewModel by lazy {
//        MainViewModel()
//    }

    private val viewModel: MainViewModel by activityViewModels()

    private val contentAdapter: ContentListAdapter by lazy{
        ContentListAdapter{ contentItem, holder ->
            viewModel.saveFavorite(requireActivity(), contentItem)
            contentItem.isFavorite = true

            when(holder) {
                is ContentListAdapter.ImageHolder -> {
                    val castedHolder = holder as ContentListAdapter.ImageHolder
                    castedHolder.favorite.isVisible = true
                }
                else -> {
                    val castedHolder = holder as ContentListAdapter.VideoHolder
                    castedHolder.favorite.isVisible = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ListFragment", "onCreateView")
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
//        return inflater.inflate(R.layout.fragment_list, container, false)

        binding.recyclerViewSearchResult.adapter = contentAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ListFragment", "onViewCreated")
        val searchKeyword = viewModel.getSearchKeyword(requireActivity())
        Log.d("ListFragment", "searchKeyword = ${searchKeyword.length}")
        binding.etSearch.setText(searchKeyword)

        val searchResults = viewModel.searchResults.value
        Log.d("ListFragment", "searchResult size  = ${searchResults?.size}")


        if(searchResults != null && searchResults.size > 0) {
            Log.d("ListFragment", "searchKeyword is not empty")
            contentAdapter.submitList(viewModel.searchResults.value)
        }

        binding.recyclerViewSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(!recyclerView.canScrollVertically(1)) {
                        Log.d("ListFragment", "end scroll")
//                        viewModel.addSearchResults()
                        if (viewModel.imagePage <= 8) {
                            this@ListFragment.lifecycleScope.launch {
                                viewModel.setEntireResults(
                                    searchKeyword,
                                    this@ListFragment.requireActivity()
                                )
                                Log.d(
                                    "ListFragment",
                                    "entireResult size = ${viewModel.entireResults.size}"
                                )
                                contentAdapter.submitList(viewModel.entireResults.toMutableList())
                            }
                        }
                        Log.d("ListFragment", "last content enriteResult size = ${viewModel.entireResults.size}")
                    }
                }
            }
        )

        binding.btnScrollTop.setOnClickListener {
            binding.recyclerViewSearchResult.smoothScrollToPosition(0)
        }

        binding.btnSearchOk.setOnClickListener {
            val keyword = binding.etSearch.text.toString()

            viewModel.insertSearchKeyword(requireActivity(), keyword)

            this.lifecycleScope.launch {
                viewModel.setEntireResults(keyword, requireActivity())
//                viewModel.setSearchResults()

//                binding.recyclerViewSearchResult.adapter = contentAdapter
//                contentAdapter.submitList(viewModel.searchResults.value)

                requireActivity().runOnUiThread {
//                    Log.d("ListFragment", "searchResults size= ${viewModel.searchResults.value!!.size}")
//                    for (contentItem in viewModel.searchResults.value!!) {
//                        Log.d("ListFragment", "${contentItem}")
//                    }


//                    contentAdapter.submitList(viewModel.searchResults.value)
                    contentAdapter.submitList(viewModel.entireResults)

                    val inputMethodManager =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                    inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

//                    contentAdapter.submitList(viewModel.searchResults)
                }

            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ListFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ListFragment", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ListFragment", "onPause")
    }


}