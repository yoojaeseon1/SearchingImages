package com.android.searchingimages.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.searchingimages.R
import com.android.searchingimages.databinding.FragmentFavoriteBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: MainViewModel by lazy {
//        MainViewModel()
//    }

    private val viewModel: MainViewModel by activityViewModels()

//    private val favoriteAdapter: ContentListAdapter by lazy {
//        ContentListAdapter{
//            Log.d("contentListAdapter", "${it}")
//        }
//    }

    private val favoriteAdapter: ContentListAdapter by lazy {
        ContentListAdapter{ contentItem, holder ->
//            Log.d("favoriteFragment", "${it}")
            viewModel.deleteFavorite(requireActivity(), contentItem)
//            viewModel.saveFavorite(requireActivity(), contentItem)
//            contentItem.isFavorite = false

//            when(holder) {
//                is ContentListAdapter.ImageHolder -> {
//                    val castedHolder = holder as ContentListAdapter.ImageHolder
//                    castedHolder.favorite.isVisible = false
//                }
//                else -> {
//                    val castedHolder = holder as ContentListAdapter.VideoHolder
//                    castedHolder.favorite.isVisible = false
//                }
//            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FavoriteFragment", "onCreate()")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
//        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        binding.recyclerViewFavorites.adapter = favoriteAdapter
        viewModel.setFavorites(requireActivity())
        Log.d("FavoriteFragment", "favorites size = ${viewModel.favorites.value?.size}")
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it.toMutableList())
//            favoriteAdapter.notifyDataSetChanged()
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("FavoriteFragment", "onViewCreated()")

        val requireActivity = requireActivity() as MainActivity

        requireActivity.hideViewPager()


//        for (contentItem in viewModel.favorites.value!!) {
//            Log.d("FavoriteFragment", "${contentItem}")
//        }

//        Log.d("FavoriteFragment", "favorites size = ${viewModel.favorites.value?.size}")


////        contentAdapter.submitList(viewModel.favorites.value)
//        favoriteAdapter.submitList(viewModel.favorites.value)
//        favoriteAdapter.submitList(viewModel.favorites.value?.toMutableList())

//        favoriteAdapter.submitList(viewModel.favorites.value)
//        viewModel.favorites.observe(requireActivity(), Observer {
//            favoriteAdapter.submitList(it?.toMutableList())
////            favoriteAdapter.notifyDataSetChanged()
//        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        Log.d("FavoriteFragment", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FavoriteFragment", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FavoriteFragment", "onStop()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FavoriteFragment", "onStart()")
    }



}