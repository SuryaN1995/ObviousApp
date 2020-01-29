package com.example.obvious.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obvious.R
import com.example.obvious.interfaces.RecyclerListener
import com.example.obvious.databinding.ActivityMainBinding
import com.example.obvious.ui.adapter.ImageAdapter
import com.example.obvious.utils.ItemDecoration
import com.example.obvious.viewModel.ImageGridViewModel

class ImageGridScreen : AppCompatActivity(), RecyclerListener {
    override fun onItemClicked(position: Int) {

    }

    private var binding: ActivityMainBinding? = null
    private var viewModel: ImageGridViewModel? = null
    private var mAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ImageGridViewModel::class.java)
        fetchData()
        initView()
    }

    private fun fetchData() {
        viewModel?.imageData?.observe(this, Observer {
            it?.let {
                mAdapter?.details = it
                mAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun initView() {
        initToolbar()
        initRv()
        viewModel?.fetchObjectFromJson(assets)
    }

    private fun initRv() {
        binding?.rvData?.apply {
            layoutManager = GridLayoutManager(this@ImageGridScreen, 3)
            mAdapter = ImageAdapter(arrayListOf(), this@ImageGridScreen)
            adapter = mAdapter
            addItemDecoration(ItemDecoration(
                resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
               3))
        }
    }

    private fun initToolbar() {
        binding?.toolbar?.title = getString(R.string.app_name)
        binding?.toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}
