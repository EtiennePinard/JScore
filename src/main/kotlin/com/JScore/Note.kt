package com.JScore

import java.lang.IllegalArgumentException

/**
 * This represents a note in music.
 * The note object will have 1 field, the midi key, which will determine the pitch of this note.
 * The note can be transposed by a semitones or octaves.
 * It is also possible to compare two notes based on their midi keys.
 * @param midiKey The midi key of the note
 * @throws IllegalArgumentException If the midi key is not a positive number
 */
class Note(midiKey: Byte) : NoteTransformation(), Comparable<Note> {

    /**
     * Gets the midi key (number between 0 and 127, where 0 is the lowest pitch  and 127 the highest) of this note.
     * A midi key of 69 is the 440 A (concert pitch).
     * So a midi key of 60 is C4 (middle C on a piano).
     * @return The midi key of this note
     */
    var midiKey: Byte = midiKey
        /**
        * Sets the midi key of this note
        * @param midiKey The new midi key of this note
        */
        set(midiKey) {
            require(midiKey >= 0) { "The midiKey needs to be a positive number." }
            field = midiKey
        }

    private val NOTE_NAMES = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    init {
        require(midiKey >= 0) { "The midiKey needs to be a positive number." }
    }


    /**
     * This method will transpose the note by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the note by.
     * @throws IllegalArgumentException If the midi key is not between 0 and 127.
     */
    override fun transpose(semitones: Byte) {
        require(!(midiKey + semitones > 127 || midiKey + semitones < 0)) { "The midiKey of this note is more than 127 or it is less than 0 and so it is out of range." }
        midiKey = (midiKey + semitones).toByte()
    }

    /**
     * This method will compare two notes together based on their midi key.
     * @param other The other notes to compare the note to.
     * @return The result of the midi key minus the other note's midi key.
     */
    override fun compareTo(other: Note): Int {
        return midiKey - other.midiKey
    }

    val octave: Int
        /**
         * Gets the octave of this note based on the midi key
         * @return The octave of this note, which is a number from -1 to 9
         */
        get() = midiKey / 12 - 1

    val note: Int
        /**
         * Gets the note number of this note object, assuming where are in 12 tone equal temperament
         * @return The note number of this note object, which is a number from 0 to 11, where 0 is C and 11 is B
         */
        get() = midiKey % 12

    val pitch: String
        /**
         * Gets the pitch of this note object
         * @return The pitch of this note object, which will only have a sharp or no sharp. There will be no flats :(
         */
        get() = NOTE_NAMES[note] + octave

    /**
     * Gets the string representation of this note object
     * @return The string representation of this note object
     */
    override fun toString(): String {
        return "Note [pitch: $pitch, midiKey: $midiKey]"
    }
}