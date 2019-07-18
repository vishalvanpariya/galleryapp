package com.vishal.galleryapp.MainScreen.photos

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.R

import java.util.LinkedList

class PhotosSubAdapter(var context: Context, var list: LinkedList<ImageData>) :
    RecyclerView.Adapter<PhotosSubAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PhotosSubAdapter.Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.photossubitem, viewGroup, false))
    }

    override fun onBindViewHolder(holder: PhotosSubAdapter.Holder, i: Int) {
        Glide.with(context)
            .load(list[i].uri.toString())
            .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
            .centerCrop()
            .into(holder.imageView)

        if (list[i].check){
            holder.checkimage.visibility = View.VISIBLE
            holder.black_view.visibility = View.VISIBLE
        }
        else{
            holder.checkimage.visibility = View.GONE
            holder.black_view.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val imageView: ImageView
        internal val checkimage:ImageView
        internal val black_view:View

        init {
            imageView = itemView.findViewById(R.id.photoitemimage)
            checkimage = itemView.findViewById(R.id.check_icon_image)
            black_view = itemView.findViewById(R.id.black_view)
        }
    }
}
