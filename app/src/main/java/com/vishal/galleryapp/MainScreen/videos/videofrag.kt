package com.vishal.galleryapp.MainScreen.videos


import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishal.galleryapp.Objects.VideoData

import com.vishal.galleryapp.R
import java.util.*

class videofrag : Fragment() {

    private lateinit var v :View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.videofrag, container, false)
        var a = VideoList()

        return v
    }

    fun VideoList():LinkedList<VideoData>{
        var templist = LinkedList<VideoData>()
        var uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.DURATION)
        var orderBy = MediaStore.Video.Media.DATE_TAKEN
        var cursor = context!!.getContentResolver().query(uri, projection, null, null, orderBy + " DESC")

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

        return templist
    }

}
