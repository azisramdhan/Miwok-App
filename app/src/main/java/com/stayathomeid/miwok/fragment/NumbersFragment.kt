package com.stayathomeid.miwok.fragment

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.stayathomeid.miwok.adapter.WordAdapter
import com.stayathomeid.miwok.data.Word
import com.stayathomeid.miwok.R
import java.util.*

/**
 * Created by alfatih on 5/12/2017.
 */
class NumbersFragment : Fragment() {
    //android class to interact with system service API
    var audioManager: AudioManager? = null

    //declare custom OnCompleteListener Class
    private val completionListener = OnCompletionListener { releaseMediaPlayer() }

    //declare and instansiation AudioManager.OnAudioFocusChangeListener object
    var onAudioFocusChangeListener = OnAudioFocusChangeListener { focusChange ->

        //implement interface method, focusChange mean what focus type happen when audio focus state change
        //if focues type happen is AUDIOFOCUS_LOSS_TRANSIENT  or AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK (you can read the documentation about this static constant variable)
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            //callback method for media player to pause audio file
            mediaPlayer!!.pause()
            //jika media player dimainkan akan memulia dari 0 (awal) karena di app ini audio sangat pendek sehingga user akan lebih ingin mendengarkan audionya dari awal dibandingkan dengan mendengarkan sisanya
            mediaPlayer!!.seekTo(0)
            //or if focus type happen is AUDIOFOCUS_GAIN
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            //callback method for media player to start audio file
            mediaPlayer!!.start()
            //or if focus type happen is AUDIOFOCUS_LOSS
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            //when audio focus change state to AUDIOFOCUS_LOSS, mean that we don't need audio focus anymore
            releaseMediaPlayer()
        }
    }

    //atribut must be declared global or final local to be accesed by inner class anonymous class (OnItemClickListener)
    var words: ArrayList<Word>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState);
        val rootView = inflater.inflate(R.layout.word_list, container, false)


        //add up icon on action bar
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        //get system service
        audioManager = activity!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        words = ArrayList()
        words!!.add(Word("one", "lutti", R.drawable.number_one, R.raw.number_one))
        words!!.add(Word("two", "otiiko", R.drawable.number_two, R.raw.number_two))
        words!!.add(Word("three", "tolookasu", R.drawable.number_three, R.raw.number_three))
        words!!.add(Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four))
        words!!.add(Word("five", "massokka", R.drawable.number_five, R.raw.number_five))
        words!!.add(Word("six", "temmoka", R.drawable.number_six, R.raw.number_six))
        words!!.add(Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven))
        words!!.add(Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight))
        words!!.add(Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine))
        words!!.add(Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten))
        val listViewAdapter = WordAdapter(activity, words, R.color.numberCategory)
        val numberListView = rootView.findViewById<View>(R.id.listview_number) as ListView
        numberListView.adapter = listViewAdapter
        numberListView.onItemClickListener = OnItemClickListener { _, _, i, _ ->

            //i is position item on listview
            //adapterView is ilst view adapter
            //l is id
            //view is single item on listview
            val currentWord = words!![i]
            val audioResource = currentWord.audioResourceID

            //cleaning up media player resource if it still playing an audio file
            releaseMediaPlayer()

            //request audio focus
            //return value must be an AudioManager.AUDIO_REQUEST_FAILED or AudioManager.AUDIO_REQUEST_GRANTED, this two is constant static variable
            val result = audioManager!!.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

            //audio can't play if there is no audio focus
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                //We have audio focus now, then create media player
                mediaPlayer = MediaPlayer.create(activity, audioResource)
                //start media player to play an audio file
                mediaPlayer?.start()
                //if media player finished playing an audio file, call media player callback method OnCompletionListener
                mediaPlayer?.setOnCompletionListener(completionListener)
            }
        }
        return rootView
    }

    //when fragment lifecycle in stop state
    override fun onStop() {
        //when implementing superclass method we must call it first using command super.method();
        super.onStop()
        //call releaseMediaPlayer method
        releaseMediaPlayer()
    }

    //cleaning up media resource
    fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            //clean up memory
            mediaPlayer!!.release()
            //clean up variable
            mediaPlayer = null
            //abandon audio focus (audio focus doesn't needed anymore)
            audioManager!!.abandonAudioFocus(onAudioFocusChangeListener)
        }
    }

    companion object {
        //declare an android class to manage audio file
        var mediaPlayer: MediaPlayer? = null
    }
}