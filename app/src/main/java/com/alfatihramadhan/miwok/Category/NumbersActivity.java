package com.alfatihramadhan.miwok.Category;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alfatihramadhan.miwok.R;
import com.alfatihramadhan.miwok.Data.Word;
import com.alfatihramadhan.miwok.Adapter.WordAdapter;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    //declare an android class to manage audio file
    public static MediaPlayer mediaPlayer;
    //android class to interact with system service API
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

        //add up icon on action bar
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        //get system service
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        words = new ArrayList<>();
        words.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookasu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmoka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter listViewAdapter = new WordAdapter(this,words,R.color.numberCategory);

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

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // it finishes the current activity and starts (or resumes) the appropriate parent activity
                NavUtils.navigateUpFromSameTask(this);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }*/

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