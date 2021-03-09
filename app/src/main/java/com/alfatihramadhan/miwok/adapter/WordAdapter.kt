package com.alfatihramadhan.miwok.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alfatihramadhan.miwok.data.Word
import com.alfatihramadhan.miwok.R

/**
 * Created by alfatih on 4/30/2017.
 */
class WordAdapter(context: Context?, objects: List<Word>?, private val colorCategory: Int) : ArrayAdapter<Word?>(context!!, 0, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listViewItem = convertView
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val currentWord = getItem(position)
        val english = listViewItem!!.findViewById<View>(R.id.english_textview) as TextView
        english.text = currentWord?.englishTranslation
        val miwok = listViewItem.findViewById<View>(R.id.miwok_textview) as TextView
        miwok.text = currentWord?.miwokTranslation
        val iconImage = listViewItem.findViewById<View>(R.id.icon_imageview) as ImageView
        if (currentWord != null && currentWord.noImageProvided) {
            iconImage.setImageResource(currentWord.imageId)
            iconImage.visibility = View.VISIBLE
        } else {
            iconImage.visibility = View.GONE
        }

        val textContainer = listViewItem.findViewById<View>(R.id.text_container)
        //Convert colorCategory maps to color
        val color = ContextCompat.getColor(context, colorCategory)
        textContainer.setBackgroundColor(color)
        return listViewItem
    }

}