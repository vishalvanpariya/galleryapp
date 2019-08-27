package com.vishal.galleryapp.Objects

import android.net.Uri
import java.io.Serializable

import java.util.Date


class ImageData(var uri: String,var name: String,var date: Date,var foldername:String,var check:Boolean=false):Serializable

class VideoData(var uri: Uri?, var name: String?, var date: Date?,var foldername: String, var hr: Int, var min: Int, var sec: Int)


