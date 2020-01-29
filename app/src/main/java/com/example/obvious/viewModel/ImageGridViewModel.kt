package com.example.obvious.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obvious.model.ImageDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream


/**
 * Created by SURYA N on 29/1/20.
 */
class ImageGridViewModel : ViewModel() {

    val imageData = MutableLiveData<List<ImageDetails>>().apply {
        postValue(null)
    }

    val position = MutableLiveData<Int>().apply {
        postValue(null)
    }

    fun fetchObjectFromJson(assets: AssetManager) {
        viewModelScope.launch(Dispatchers.IO) {
            val input: InputStream
            try {
                input = assets.open("data.json")
                val size = input.available()
                val buffer = ByteArray(size)
                input.read(buffer)
                input.close()

                // byte buffer into a string
                val response = String(buffer)
                val type = object : TypeToken<List<ImageDetails>>() {}.type
                withContext(Dispatchers.Main) {
                    imageData.value = Gson().fromJson<List<ImageDetails>>(response, type)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}