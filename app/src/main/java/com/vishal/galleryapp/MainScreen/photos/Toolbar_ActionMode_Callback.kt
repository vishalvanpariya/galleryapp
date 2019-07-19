package com.vishal.galleryapp.MainScreen.photos

import android.content.Context
import androidx.appcompat.view.ActionMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.vishal.galleryapp.R
import java.util.*
import kotlin.collections.ArrayList

class Toolbar_ActionMode_Callback(
    private val context: Context,
    private val photosfrag: photosfrag
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.action_main_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionmode_delet -> {

            }
            R.id.actionmode_movemediavalute -> Toast.makeText(context, "Delet", Toast.LENGTH_SHORT).show()
            R.id.actionmode_share -> Toast.makeText(context, "Delet", Toast.LENGTH_SHORT).show()
            R.id.actionmode_copy -> Toast.makeText(context, "Delet", Toast.LENGTH_SHORT).show()
            R.id.actionmode_move -> Toast.makeText(context, "Delet", Toast.LENGTH_SHORT).show()
            R.id.actionmode_selectall -> {
            }
            R.id.actionmode_unselectall ->onDestroyActionMode(mode)
        }
        return false
    }



    override fun onDestroyActionMode(mode: ActionMode) {

    }
}
