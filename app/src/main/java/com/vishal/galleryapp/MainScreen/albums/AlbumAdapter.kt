package com.vishal.galleryapp.MainScreen.albums

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.Objects.VideoData
import com.vishal.galleryapp.R
import java.util.*

class AlbumAdapter(
    var context: Context,
    var list: LinkedList<Any>,
    var foldernamelist: LinkedList<String>,
    var sizelist: LinkedList<Int>
) : RecyclerView.Adapter<AlbumAdapter.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.albumitem, viewGroup, false))
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        if (list.get(i).javaClass.equals(ImageData(uri = Uri.parse("f").toString(),name = "df",date = Date(),foldername = "fs").javaClass)){
            var temp = list.get(i) as ImageData
            Glide.with(context)
                .load(temp.uri.toString())
                .error(ContextCompat.getDrawable(context,R.mipmap.ic_launcher))
                .centerCrop()
                .into(holder.imageView)
        }
        else{
            var temp = list.get(i) as VideoData
            Glide.with(context)
                .load(temp.uri.toString())
                .error(ContextCompat.getDrawable(context,R.mipmap.ic_launcher))
                .centerCrop()
                .into(holder.imageView)
        }
        holder.foldername.text = foldernamelist.get(i)
        holder.size.text = "${sizelist.get(i)}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var imageView: ImageView
        internal var foldername: TextView
        internal var size: TextView

        init {
            imageView = itemView.findViewById(R.id.albumitemimage)
            foldername = itemView.findViewById(R.id.foldername_text)
            size = itemView.findViewById(R.id.size_text)
        }
    }
}
