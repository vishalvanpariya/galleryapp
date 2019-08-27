package com.vishal.galleryapp.MainScreen.photos

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import java.io.Serializable
import java.util.*

class Section(var context:Context,var key:String,var list:LinkedList<ImageData>,var startpos:Int,var mainlist:LinkedList<ImageData>):
    StatelessSection(
        SectionParameters.builder()
            .itemResourceId(R.layout.photossubitem)
            .headerResourceId(R.layout.recyclerheaderitem)
            .build()
    ) {

    override fun getContentItemsTotal(): Int {
        return list.size
    }

    override fun getItemViewHolder(view: View?): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ItemHolder(view!!)
    }
    override fun onBindItemViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, position: Int) {
        var itemHolder = holder as ItemHolder
        Glide.with(context)
            .load(list[position].uri.toString())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(itemHolder.image)

        holder.image.setOnClickListener {
            var intent = Intent(context,photoview_activity::class.java)
            intent.putExtra("list",mainlist)
            intent.putExtra("position","${position+startpos}")
            val pairimage = Pair.create<View,String>(it,"itemimage")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity,pairimage)
            context.startActivity(intent,options.toBundle())
        }
    }

    override fun getHeaderViewHolder(view: View?): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return HeaderHolder(view!!)
    }
    override fun onBindHeaderViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder?) {
        var headerHolder=holder as HeaderHolder
        headerHolder.headertext.text = key
    }


}

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image:ImageView
    var blackview:View
    var check_image:ImageView
    init {
        image = itemView.findViewById(R.id.photoitemimage)
        blackview = itemView.findViewById(R.id.black_view)
        check_image =itemView.findViewById(R.id.check_icon_image)
    }
}

class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var headertext:TextView
    init {
        headertext = itemView.findViewById(R.id.headertext)
    }
}
