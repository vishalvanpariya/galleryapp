package com.vishal.galleryapp.MainScreen.photos


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.vishal.galleryapp.Objects.ImageData
import com.vishal.galleryapp.R
import kotlinx.android.synthetic.main.photoview.*
import kotlin.collections.ArrayList
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.content_photoview.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class photoview_activity : AppCompatActivity() {

    var isfullscreen=false
    private var mSlideUp: Animation? = null
    private var mSlideDown: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photoview)
        var toolbar= findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)

        mSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        mSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        var i=intent
        var list = i.getSerializableExtra("list") as ArrayList<ImageData>
        var position = intent.getStringExtra("position").toInt()
        photoviewpager.adapter = PhotoViewPagerAdapter(this, list)
        photoviewpager.setCurrentItem(position)
        var obj=list.get(position)
        var hr =obj.date.hours
        var min = 0
        var sec = 0
        var timeeriod ="AM"
        if (obj.date.hours>12){
            hr=hr-12
            timeeriod="PM"
        }
        imagedetail.setText("${obj.name} \n ${getmonth(obj.date.month)} ${obj.date.date},${obj.date.year+1900} ${hr}:${obj.date.minutes}:${obj.date.seconds} ${timeeriod}")

        photoviewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                var obj=list.get(position)
                var hr =obj.date.hours
                var min = 0
                var sec = 0
                var timeeriod ="AM"
                if (obj.date.hours>12){
                    hr=hr-12
                    timeeriod="PM"
                }
                imagedetail.setText("${obj.name} \n ${getmonth(obj.date.month)} ${obj.date.date},${obj.date.year+1900} ${hr}:${obj.date.minutes}:${obj.date.seconds} ${timeeriod}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.photoview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.detail_menu -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun hide(){
        if (isfullscreen) {
            window.decorView.systemUiVisibility =(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            isfullscreen=false
            imagedetail.visibility= View.VISIBLE
            toolbar2.startAnimation(mSlideDown);
            getSupportActionBar()!!.show();
        }
        else{
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            isfullscreen=true
            imagedetail.visibility=View.GONE
            toolbar2.startAnimation(mSlideUp);
            getSupportActionBar()!!.hide()
        }
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
