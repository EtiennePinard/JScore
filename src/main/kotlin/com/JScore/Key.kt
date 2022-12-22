package com.JScore

/**
 * This represents a key in music.
 * The key object will have three fields: a mode, a tonic note and therefore a scale.
 * The key can be transposed by a semitones or octaves.
 * It is also possible to get chords based on a specified degrees of the scale.
 * @param mode The mode of the key
 * @param tonic The tonic note of the key
 */
class Key(
    /**
     * Gets the mode object of this key
     * @return The mode object of this key
     */
    val mode: Mode,
    tonic: Note
) : NoteTransformation() {

    /**
     * Gets the tonic note of this key
     * @return The tonic note of this key
     */
    var tonic = tonic
        private set

    /**
     * Gets the scale of this key
     * @return The scale object of this key
     */
     var scale: Scale = Scale(this)
        private set

    /**
     * This method will transpose the key by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the key by.
     */
    override fun transpose(semitones: Byte) {
        tonic = Note((tonic.midiKey + semitones).toByte())
        scale = Scale(this)
    }

    /**
     * Gets the chord at the specified degree of this scale
     * @param degree The degree of the chord
     * @return The chord at the specified degree of this scale
     * @throws IllegalArgumentException If the mode of this key is the chromatic mode or the degree is bigger than the length of the scale or smaller than 0
     */
    @Throws(IllegalArgumentException::class)
    fun getChordByDegree(degree: Int): Chord {
        require(mode !== Mode.Chromatic) { "You cannot harmonize the chromatic scale! At least I do not know how." }
        require(!(degree < 1 || degree > scale.scaleNotes.size)) {
            "There are " + (scale.scaleNotes.size - 1) + " degrees in the " + tonic.pitch + " " + mode + ", from 1 to " + (scale.scaleNotes.size - 1) + "!"
        }
        return scale.scaleChords!!.chords[degree - 1]
    }

    /**
     * Gets a string representation of this key
     * @return The string representation of this key
     */
    override fun toString(): String {
        return "Key: [mode: $mode, tonic: $tonic, scale: $scale]"
    }
}