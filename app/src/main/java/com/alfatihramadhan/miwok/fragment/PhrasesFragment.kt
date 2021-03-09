package com.alfatihramadhan.miwok.fragment

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
import com.alfatihramadhan.miwok.adapter.WordAdapter
import com.alfatihramadhan.miwok.data.Word
import com.alfatihramadhan.miwok.R
import java.util.*

class PhrasesFragment : Fragment() {
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
        val rootView = inflater.inflate(R.layout.word_list, container, false)

        //get system service from activity of fragment
        audioManager = activity!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        words = ArrayList()
        words!!.add(Word("Where are you going", "minto wuksus", R.raw.phrase_where_are_you_going))
        words!!.add(Word("What is your name", "tinna oyaase'na", R.raw.phrase_what_is_your_name))
        words!!.add(Word("My name is ..", "oyaaset..", R.raw.phrase_my_name_is))
        words!!.add(Word("How are you feeling?", "michakses?", R.raw.phrase_how_are_you_feeling))
        words!!.add(Word("I'm feeling good", "kuchi achit", R.raw.phrase_im_feeling_good))
        words!!.add(Word("Are you coming?", "eenas'aa?", R.raw.phrase_are_you_coming))
        words!!.add(Word("Yes, I'm coming", "hee'eenem", R.raw.phrase_yes_im_coming))
        words!!.add(Word("I'm coming", "eenem", R.raw.phrase_im_coming))
        words!!.add(Word("Let's go", "yoowutis", R.raw.phrase_lets_go))
        words!!.add(Word("Come here", "anni'nem", R.raw.phrase_come_here))
        val listViewAdapter = WordAdapter(activity, words, R.color.categoryPhrases)
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