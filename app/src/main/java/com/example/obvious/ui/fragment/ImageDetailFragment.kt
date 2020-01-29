package com.example.obvious.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.obvious.R
import com.example.obvious.databinding.FragmentImageDetailBinding
import com.example.obvious.databinding.ItemOptionBinding
import com.example.obvious.model.ImageDetails
import com.example.obvious.ui.activity.ImageGridScreen
import com.example.obvious.utils.getPopUpWindow
import com.example.obvious.viewModel.ImageGridViewModel
import kotlin.math.abs


/**
 * Created by SURYA N on 29/1/20.
 */
class ImageDetailFragment : Fragment() {

    private var popupMenu: PopupWindow ?= null
    private var binding: FragmentImageDetailBinding? = null
    private var viewModel: ImageGridViewModel? = null
    private var imageData: ImageDetails? = null
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail, container, false)
        viewModel = (activity as ImageGridScreen).viewModel
        fetchPositionUpdate()
        initView()
        return binding?.root
    }

    private fun fetchPositionUpdate() {
        viewModel?.position?.observe(this, Observer {
            it?.let {
                position = it
                updateData()
            }
        })
        binding?.setClickHandler {
            val view = DataBindingUtil.inflate<ItemOptionBinding>(
                LayoutInflater.from(context), R.layout.item_option, null, false
            )
            view.imageData = imageData
            popupMenu = context?.applicationContext?.let { it1 ->
                binding?.root?.let { it2 ->
                    binding?.option?.let { it3 ->
                        getPopUpWindow(
                            it1,
                            view.root,
                            it3,
                            it2,
                            false,
                            450
                        )
                    }
                }
            }
        }
        position = viewModel?.position?.value ?: 0
        imageData = viewModel?.imageData?.value?.get(position)
    }

    private fun initView() {
        binding?.size = 1000
        binding?.imageUrl = imageData?.url ?: ""
        binding?.root?.setOnTouchListener { _, p1 -> gesture.onTouchEvent(p1) }

    }

    private val gesture = GestureDetector(
        activity,
        object : GestureDetector.SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                velocityY: Float
            ): Boolean {
                val SWIPE_MIN_DISTANCE = 50
                val SWIPE_THRESHOLD_VELOCITY = 50
                try {
                    if ((e1.x - e2.x) > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (position in 0 until (viewModel?.imageData?.value?.size ?: 0) - 1)
                            viewModel?.position?.value = ++position
                    } else if ((e2.x - e1.x) > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (position in 1 until (viewModel?.imageData?.value?.size ?: 0))
                            viewModel?.position?.value = --position
                    }
                } catch (e: Exception) {
                    // nothing
                }

                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

    private fun updateData() {
        imageData = viewModel?.imageData?.value?.get(position)
        initToolBar()
        binding?.imageUrl = imageData?.url ?: ""
    }

    private fun initToolBar() {
        (activity as ImageGridScreen).setToolbarTitle(imageData?.title ?: "", true)
    }


    override fun onPause() {
        super.onPause()
        popupMenu?.dismiss()
    }

}