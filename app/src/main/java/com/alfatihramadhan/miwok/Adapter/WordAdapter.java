package com.alfatihramadhan.miwok.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfatihramadhan.miwok.R;
import com.alfatihramadhan.miwok.Data.Word;

import java.util.List;

/**
 * Created by alfatih on 4/30/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorCategory;

    public WordAdapter(Context context,List<Word> objects, int colorCategory) {
        super(context, 0, objects);
        this.colorCategory = colorCategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        TextView english = (TextView)listViewItem.findViewById(R.id.english_textview);
        english.setText(currentWord.getEnglishTranslation());

        TextView miwok = (TextView)listViewItem.findViewById(R.id.miwok_textview);
        miwok.setText(currentWord.getMiwokTranslation());

        ImageView iconImage = (ImageView)listViewItem.findViewById(R.id.icon_imageview);
        if(currentWord.getNoImageProvided()){
            iconImage.setImageResource(currentWord.getImageId());
            iconImage.setVisibility(View.VISIBLE);
        }else{
            iconImage.setVisibility(View.GONE);
        }

        View textContainer = listViewItem.findViewById(R.id.text_container);
        //Convert colorCategory maps to color
        int color = ContextCompat.getColor(getContext(),colorCategory);
        textContainer.setBackgroundColor(color);

        return listViewItem;
    }
}
