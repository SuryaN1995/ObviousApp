package com.example.obvious.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.obvious.R
import com.example.obvious.databinding.ActivityMainBinding
import com.example.obvious.interfaces.RecyclerListener
import com.example.obvious.ui.adapter.ImageAdapter
import com.example.obvious.ui.fragment.ImageDetailFragment
import com.example.obvious.utils.ItemDecoration
import com.example.obvious.viewModel.ImageGridViewModel


class ImageGridScreen : AppCompatActivity(), RecyclerListener {

    override fun onItemClicked(position: Int) {
        viewModel?.position?.value = position
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, ImageDetailFragment())
            .addToBackStack(ImageDetailFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    private var binding: ActivityMainBinding? = null
    var viewModel: ImageGridViewModel? = null
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
        initRv()
        viewModel?.fetchObjectFromJson(assets)
    }

    private fun initRv() {
        binding?.rvData?.apply {
            layoutManager = GridLayoutManager(this@ImageGridScreen, 3)
            mAdapter = ImageAdapter(arrayListOf(), this@ImageGridScreen, false)
            adapter = mAdapter
            addItemDecoration(
                ItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                    3
                )
            )
        }
    }

    private fun initToolbar() {
        binding?.toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(binding?.toolbar)
        setToolbarTitle(getString(R.string.app_name), false)
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
    }

    fun setToolbarTitle(title: String, isBackButton: Boolean) {
        binding?.toolbar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(isBackButton)
        supportActionBar?.setDisplayShowHomeEnabled(isBackButton)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        initToolbar()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.popBackStackImmediate()
                initToolbar()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
