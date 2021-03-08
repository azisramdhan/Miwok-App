package com.alfatihramadhan.miwok.Category;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alfatihramadhan.miwok.R;
import com.alfatihramadhan.miwok.Data.Word;
import com.alfatihramadhan.miwok.Adapter.WordAdapter;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    //declare an android class to manage audio file
    public static MediaPlayer mediaPlayer;
    AudioManager audioManager;
    //declare custom OnCompleteListener Class
    private final MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //get system service
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        words = new ArrayList<>();
        words.add(new Word("Where are you going","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name","tinna oyaase'na",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is ..","oyaaset..",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michakses?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","eenas'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming","hee'eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming","eenem",R.raw.phrase_im_coming));
        words.add(new Word("Let's go","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here","anni'nem",R.raw.phrase_come_here));

        WordAdapter listViewAdapter = new WordAdapter(this,words,R.color.categoryPhrases);

        ListView numberListView = (ListView) findViewById(R.id.listview_number);

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
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),audioResource);
                    //start media player to play an audio file
                    mediaPlayer.start();
                    //if media player finished playing an audio file, call media player callback method OnCompletionListener
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });


    }

    //when activity lifecycle in stop state
    @Override
    protected void onStop() {
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


