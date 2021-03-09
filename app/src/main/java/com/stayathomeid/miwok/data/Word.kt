package com.stayathomeid.miwok.data

/**
 * Created by alfatih on 4/30/2017.
 */
class Word(val englishTranslation: String, val miwokTranslation: String, val audioResourceID: Int) {
    var imageId = NO_IMAGE_PROVIDED
        private set

    constructor(english: String, miwok: String, imageId: Int, audio: Int) : this(english, miwok, audio) {
        this.imageId = imageId
    }

    val noImageProvided: Boolean
        get() = imageId != NO_IMAGE_PROVIDED

    companion object {
        const val NO_IMAGE_PROVIDED = -1
    }

}