package com.vishal.galleryapp.MainScreen.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.R
import kotlin.collections.ArrayList
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.File
import kotlin.math.log


class PhotoViewPagerAdapter(var context:Context,var list:ArrayList<ImageData>): PagerAdapter() {
    lateinit var layoutInflater:LayoutInflater
    init {
        layoutInflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = layoutInflater.inflate(R.layout.viewpageritem,container,false)
        var image = view.findViewById<ImageView>(R.id.image)

//        var uri = Uri.parse(list[position].uri)
//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = true
//        BitmapFactory.decodeFile(File(uri.getPath()).getAbsolutePath(), options)
//        val imageHeight = options.outHeight
//        val imageWidth = options.outWidth
//        Log.d("xxxx","${imageHeight} ${imageWidth}")


        Glide.with(context)
            .load(list[position].uri)
            .into(image)
        container.addView(view)

        view.setOnClickListener {
            (context as photoview_activity).hide()
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}