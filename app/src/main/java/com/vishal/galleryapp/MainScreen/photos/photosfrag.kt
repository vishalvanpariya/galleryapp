package com.vishal.galleryapp.MainScreen.photos


import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishal.galleryapp.Objects.ImageData

import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishal.galleryapp.R
import java.io.File
import androidx.appcompat.view.ActionMode
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.mlsdev.animatedrv.AnimatedRecyclerView
import com.vishal.galleryapp.MainActivity
import com.vishal.galleryapp.Objects.CustomLinearLayoutManager
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlin.collections.ArrayList
import kotlin.collections.HashMap





class photosfrag : Fragment() {

    var num = 0

    private lateinit var v: View

    var deletlist:ArrayList<Int> = ArrayList()
    lateinit var list:LinkedList<ImageData>
    lateinit var keylist:LinkedList<String>
    lateinit var sectionAdapter: SectionedRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.photosfrag, container, false)

        keylist = LinkedList()
        sectionAdapter = SectionedRecyclerViewAdapter();
        setrecycler()

        return v
    }



    fun setrecycler(){
        var tempmap = getImagemap()
        var mainlist  = LinkedList<ImageData>()
        var templist  = LinkedList<ImageData>()
        for (key in keylist) {
            mainlist.addAll(tempmap[key]!!)
        }
        for (key in keylist) {
            sectionAdapter.addSection(Section(context = context!!,key = key,list = tempmap[key]!!,startpos = templist.size,mainlist = mainlist))
            templist.addAll(tempmap[key]!!)
        }
        var recyclerView = v.findViewById<AnimatedRecyclerView>(R.id.photos_recyclerview)
        var glm = GridLayoutManager(context,3)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (sectionAdapter.getSectionItemViewType(position)) {
                    0 -> return 3
                    else -> return 1
                }
            }
        }
        glm.initialPrefetchItemCount = 50
        recyclerView.layoutManager = glm
        recyclerView.adapter = sectionAdapter

        sectionAdapter


    }

    fun getImagemap():HashMap<String,LinkedList<ImageData>>{
        var tempmap = HashMap<String,LinkedList<ImageData>>()
        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_MODIFIED)
        var orderBy = MediaStore.Images.Media.DATE_TAKEN
        var cursor = context!!.getContentResolver().query(uri, projection, null, null, orderBy + " DESC")

        var dataindex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        var bucketnameindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var nameindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        var dateindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

        while (cursor.moveToNext()){
            var file = File(Uri.parse(cursor.getString(dataindex)).toString())
            var date = Date(file.lastModified())
            var key = "${getday(date.day)},${getmonth(date.month)} ${date.date},${date.year+1900}"
            var today = Date(System.currentTimeMillis())
            var todaykey = "${getday(today.day)},${getmonth(today.month)} ${today.date},${today.year+1900}"
            if (todaykey.equals(key)){
                key = "today"
            }
            if (!keylist.contains(key)){
                keylist.add(key)
            }
            if (tempmap.containsKey(key)){
                tempmap[key]!!.add(
                    ImageData(
                        uri = Uri.parse(cursor.getString(dataindex)).toString(),
                        name = cursor.getString(nameindex),
                        date = Date(file.lastModified()),
                        foldername = cursor.getString(bucketnameindex)
                    )
                )
            }
            else{
                var list = LinkedList<ImageData>()
                list.add(
                    ImageData(
                        uri = Uri.parse(cursor.getString(dataindex)).toString(),
                        name = cursor.getString(nameindex),
                        date = Date(file.lastModified()),
                        foldername = cursor.getString(bucketnameindex)
                    )
                )
                tempmap[key] = list
            }
        }
        return tempmap;
    }

    fun getday(day: Int): String {
        when (day) {
            0 -> return "Sunday"
            1 -> return "Monday"
            2 -> return "Tuesday"
            3 -> return "Wednesday"
            4 -> return "Thursday"
            5 -> return "Friday"
            6 -> return "Saturday"
        }
        return "Sunday"
    }
    private fun getmonth(month: Int): String {
        when(month){
            0-> return "Jan"
            1-> return "Feb"
            2-> return "Mar"
            3-> return "Apr"
            4-> return "May"
            5-> return "June"
            6-> return "July"
            7-> return "Aug"
            8-> return "Sept"
            9-> return "Oct"
            10-> return "Nov"
            11-> return "Dec"
        }
        return "Jan"
    }

}
