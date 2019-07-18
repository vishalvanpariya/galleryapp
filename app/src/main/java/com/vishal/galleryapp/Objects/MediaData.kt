package com.vishal.galleryapp.Objects

import android.net.Uri

import java.util.Date

class ImageData(var uri: Uri,var name: String,var date: Date,var foldername:String,var check:Boolean=false)

class VideoData(var uri: Uri?, var name: String?, var date: Date?,var foldername: String, var hr: Int, var min: Int, var sec: Int)


