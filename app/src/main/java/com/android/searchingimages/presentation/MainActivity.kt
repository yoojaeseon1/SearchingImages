package com.android.searchingimages.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.android.searchingimages.R
import com.android.searchingimages.data.ContentRepository
import com.android.searchingimages.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private val contentRepository: ContentRepository by lazy{
        ContentRepository()
    }

    private val binding: ActivityMainBinding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by lazy {
        MainViewModel(contentRepository)
    }

    private lateinit var viewPager: ViewPager2
    private val tabTitles = arrayOf("이미지 검색", "내 보관함")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            tabLayout.addOnTabSelectedListener(this@MainActivity)
            viewPager = pager
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)
            viewPager.offscreenPageLimit = 2
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }


        this.lifecycleScope.launch {
//            val searchResults = viewModel.setSearchResults("아이브")
//            Log.d("mainActivity", "results size = ${searchResults.size}")
//            for (result in searchResults) {
//                Log.d("mainActivity", "${result}")
//            }
        }
//        contentRepository.searchImageFromAPI(this, contentRepository.setRequestParam("아이브"))

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab != null) {
            when(tab.position) {
                0 -> {
                    viewPager.setCurrentItem(0, false)
                }
                else -> {
                    viewPager.setCurrentItem(1, false)
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        if(tab != null) {
            when(tab.position) {
                0 -> {
//                    viewPager.setCurrentItem(0, false)
                }
                else -> {
//                    viewPager.setCurrentItem(1, false)
                }
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {    }
}