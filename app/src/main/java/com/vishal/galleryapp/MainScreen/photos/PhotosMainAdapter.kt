package com.vishal.galleryapp.MainScreen.photos

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider
import com.mlsdev.animatedrv.AnimatedRecyclerView
import com.vishal.galleryapp.Objects.CustomGridLayoutManager
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.R
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.log

class PhotosMainAdapter(var context: Context, var map: HashMap<String,LinkedList<ImageData>>,var keylist:LinkedList<String>) :
    RecyclerView.Adapter<PhotosMainAdapter.Holder>(), SectionTitleProvider {

    lateinit var adapter :PhotosSubAdapter

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.photositem, viewGroup, false))
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        holder.textView.text = keylist[i]
        adapter = PhotosSubAdapter(context = context,list = map[keylist[i]]!!)
        holder.animatedRecyclerView.adapter = adapter
        holder.animatedRecyclerView.setHasFixedSize(true)
        holder.animatedRecyclerView.layoutManager = CustomGridLayoutManager(context,3)
    }




    override fun getItemCount(): Int {
        return map.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var animatedRecyclerView: AnimatedRecyclerView
        internal var textView: TextView

        init {
            animatedRecyclerView = itemView.findViewById(R.id.photos_sub_recyclerview)
            textView = itemView.findViewById(R.id.recyclerhead)
        }
    }

    override fun getSectionTitle(position: Int): String {
        return keylist[position]
    }
}
