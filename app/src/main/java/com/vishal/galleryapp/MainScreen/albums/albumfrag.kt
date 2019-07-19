package com.vishal.galleryapp.MainScreen.albums


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mlsdev.animatedrv.AnimatedRecyclerView
import com.vishal.galleryapp.MainScreen.albums.AlbumAdapter
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.Objects.VideoData

import com.vishal.galleryapp.R
import java.util.*

class albumfrag : Fragment() {

    private lateinit var v:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.albumfrag, container, false)
        var imagelist = ImageList()
        var videolist = VideoList()

        var myList = LinkedList<Any>()
        var backetnamelist = LinkedList<String>()
        var sizelist = LinkedList<Int>()

        val sharedPref: SharedPreferences = context!!.getSharedPreferences("temp.txt",MODE_PRIVATE)
        var editor = sharedPref.edit()
        for (i in 0..imagelist.size-1){
            if (i==0) {
                myList.add(imagelist.get(i))
                backetnamelist.add(imagelist.get(i).foldername)
                editor.putInt(imagelist.get(i).foldername,1)
                editor.apply()
            }
            else {
                if (!backetnamelist.contains(imagelist.get(i).foldername)) {
                    myList.add(imagelist.get(i))
                    backetnamelist.add(imagelist.get(i).foldername)
                    editor.putInt(imagelist.get(i).foldername, 1)
                    editor.apply()
                }
                else {
                    editor.putInt(imagelist.get(i).foldername, sharedPref.getInt(imagelist.get(i).foldername, 0) + 1)
                    editor.apply()
                }
            }
        }
        for (i in 0..videolist.size-1) {
            if (myList.size==0) {
                myList.add(videolist.get(i))
                backetnamelist.add(videolist.get(i).foldername)
                editor.putInt(videolist.get(i).foldername,1)
                editor.apply()
            } else {
                if (!backetnamelist.contains(videolist.get(i).foldername)) {
                    myList.add(videolist.get(i))
                    backetnamelist.add(videolist.get(i).foldername)
                    editor.putInt(videolist.get(i).foldername, 1)
                    editor.apply()
                }
                else {
                    editor.putInt(videolist.get(i).foldername, sharedPref.getInt(videolist.get(i).foldername, 0) + 1)
                    editor.apply()
                }
            }
        }
        for (i in 0..backetnamelist.size-1){
            sizelist.add(sharedPref.getInt(backetnamelist.get(i),0))
        }

        var recycler = v.findViewById<AnimatedRecyclerView>(R.id.albums_recyclerview)
        var adapter = AlbumAdapter(
            context = context!!,
            list = myList,
            foldernamelist = backetnamelist,
            sizelist = sizelist
        )
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
        recycler.scheduleLayoutAnimation()

        return v
    }

    fun ImageList(): LinkedList<ImageData> {
        var templist = LinkedList<ImageData>()
        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_MODIFIED)
        var orderBy = MediaStore.Images.Media.DATE_TAKEN
        var order = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        var cursor = context!!.getContentResolver().query(uri, projection, null, null, order + " DESC")

        var dataindex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        var bucketnameindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var nameindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        var dateindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

        while (cursor.moveToNext()){
            templist.add(
                ImageData(
                    uri = Uri.parse(cursor.getString(dataindex)),
                    name = cursor.getString(nameindex),
                    date = Date(cursor.getLong(dateindex)),
                    foldername = cursor.getString(bucketnameindex)
                )
            )

        }
        Collections.reverse(templist)
        return templist
    }

    fun VideoList():LinkedList<VideoData>{
        var templist = LinkedList<VideoData>()
        var uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.DURATION)
        var order = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        var orderBy = MediaStore.Video.Media.DATE_TAKEN
        var cursor = context!!.getContentResolver().query(uri, projection, null, null, order + " DESC")

        var dataindex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        var bucketnameindex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        var nameindex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
        var dateindex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
        var durationindex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

        while (cursor.moveToNext()){
            var temptime = cursor.getLong(durationindex)
            var tempsec = temptime.div(1000).toInt()
            var tempmin = tempsec.div(60).toInt()
            var temphr = tempmin.div(60).toInt()

            var min = tempmin - temphr*60
            var sec = tempsec - tempmin*60

            templist.add(
                VideoData(
                    uri = Uri.parse(cursor.getString(dataindex)),
                    name = cursor.getString(nameindex),
                    date = Date(cursor.getLong(dateindex)),
                    hr = temphr,
                    min = min,
                    sec = sec,
                    foldername = cursor.getString(bucketnameindex)
                )
            )
        }
        Collections.reverse(templist)
        return templist
    }

}
