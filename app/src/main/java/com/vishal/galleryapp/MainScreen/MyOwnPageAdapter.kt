package com.vishal.galleryapp.MainScreen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vishal.galleryapp.MainScreen.albums.albumfrag
import com.vishal.galleryapp.MainScreen.photos.photosfrag
import com.vishal.galleryapp.MainScreen.videos.videofrag

class MyOwnPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val data = arrayOf("PHOTOS", "VIDEOS", "ALBUMS")

    override fun getItem(i: Int): Fragment {
        return if (i == 0) {
            photosfrag()
        } else if (i == 1)
            videofrag()
        else if (i == 2)
            albumfrag()
        else
            photosfrag()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position]
    }
}
