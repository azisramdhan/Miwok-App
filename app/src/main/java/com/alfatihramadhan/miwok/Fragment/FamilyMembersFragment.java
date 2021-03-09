package com.alfatihramadhan.miwok.Fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alfatihramadhan.miwok.Adapter.WordAdapter;
import com.alfatihramadhan.miwok.Data.Word;
import com.alfatihramadhan.miwok.R;

import java.util.ArrayList;

/**
 * Created by alfatih on 5/16/2017.
 */

public class FamilyMembersFragment extends Fragment {


    //declare an android class to manage audio file
    public static MediaPlayer mediaPlayer;
    AudioManager audioManager;
    //declare custom OnCompleteListener Class
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    //declare and instansiation AudioManager.OnAudioFocusChangeListener object
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        //implement interface method, focusChange mean what focus type happen when audio focus state change
        @Override
        public void onAudioFocusChange(int focusChange) {
            //if focues type happen is AUDIOFOCUS_LOSS_TRANSIENT  or AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK (you can read the documentation about this static constant variable)
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //callback method for media player to pause audio file
                mediaPlayer.pause();
                //jika media player dimainkan akan memulia dari 0 (awal) karena di app ini audio sangat pendek sehingga user akan lebih ingin mendengarkan audionya dari awal dibandingkan dengan mendengarkan sisanya
                mediaPlayer.seekTo(0);
                //or if focus type happen is AUDIOFOCUS_GAIN
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //callback method for media player to start audio file
                mediaPlayer.start();
                //or if focus type happen is AUDIOFOCUS_LOSS
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //when audio focus change state to AUDIOFOCUS_LOSS, mean that we don't need audio focus anymore
                releaseMediaPlayer();
            }
        }
    };

    //atribut must be declared global or final local to be accesed by inner class anonymous class (OnItemClickListener)
    ArrayList<Word> words;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list,container,false);

        //get system service
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        words = new ArrayList<>();
        words.add(new Word("father","ede", R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","eta",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolitti",R.drawable.family_older_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter listViewAdapter = new WordAdapter(getActivity(),words,R.color.categoryFamily);

        ListView numberListView = (ListView) rootView.findViewById(R.id.listview_number);

        numberListView.setAdapter(listViewAdapter);

        numberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //i is position item on listview
            //adapterView is ilst view adapter
            //l is id
            //view is single item on listview
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = words.get(i);
                int audioResource = currentWord.getAudioResourceID();

                //cleaning up media player resource if it still playing an audio file
                releaseMediaPlayer();

                //request audio focus
                //return value must be an AudioManager.AUDIO_REQUEST_FAILED or AudioManager.AUDIO_REQUEST_GRANTED, this two is constant static variable
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //audio can't play if there is no audio focus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //We have audio focus now, then create media player
                    mediaPlayer = MediaPlayer.create(getActivity(),audioResource);
                    //start media player to play an audio file
                    mediaPlayer.start();
                    //if media player finished playing an audio file, call media player callback method OnCompletionListener
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });

        return rootView;
    }

    //when activity lifecycle in stop state
    @Override
    public void onStop() {
        //when implementing superclass method we must call it first using command super.method();
        super.onStop();
        //call releaseMediaPlayer method
        releaseMediaPlayer();
    }

    //cleaning up media resource
    public void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            //clean up memory
            mediaPlayer.release();
            //clean up variable
            mediaPlayer = null;
            //abandon audio focus (audio focus doesn't needed anymore)
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

}
