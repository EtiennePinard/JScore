package com.JScore

import java.util.function.Consumer

/**
 * This represents a scale in music.
 * The scale object will have 2 fields: a list of notes of the scale and a list of chords of this scale.
 * Note: The chords field can be null if the mode of the key is the chromatic mode.
 * @param key The key of this scale
 */
class Scale(key: Key) {

    /**
     * Gets the notes of this scale, starting from the tonic to the leading tone / subtonic
     * @return The notes of this scale
     */
    val scaleNotes = ArrayList<Note>()

    /**
     * Gets the chords of this scale, in a chord progression object, starting from the tonic chord to the leading tone / subtonic chord
     * This will return null if the mode of this scale key is the chromatic mode
     * @return The chords of this scale
     */
    var scaleChords: ChordProgression? = null
        private set

    init {
        generateScaleNotes(key)
        if (key.mode != Mode.Chromatic) generateScaleChords(key)
    }

    /**
     * Generate the notes of the scale based on the steps of said scale
     * @param key The key of the scale
     */
    private fun generateScaleNotes(key: Key) {
        scaleNotes.clear()
        scaleNotes.add(key.tonic)
        for (i in key.mode.steps.indices) scaleNotes.add(Note((scaleNotes[i].midiKey + key.mode.steps[i]).toByte()))
    }

    /**
     * Generate the chords of the scale based on the steps of said scale
     * @param key The key of the scale
     */
    private fun generateScaleChords(key: Key) {
        scaleChords = ChordProgression(key)
        for (i in 1..scaleNotes.size) when (i) {
            1, 4, 5 -> scaleChords!!.addAChord(Chord(scaleNotes[i - 1]).appendMajorChord())
            2, 3, 6 -> scaleChords!!.addAChord(Chord(scaleNotes[i - 1]).appendMinorChord())
            7 -> scaleChords!!.addAChord(Chord(scaleNotes[i - 1]).addMinor3().addMinor3())
        }
    }

    /**
     * Gets the string representation of this scale
     * @return The string representation of this scale
     */
    override fun toString(): String {
        val string = StringBuilder("Scale: [")
        scaleNotes.forEach(Consumer { note: Note -> string.append(note.pitch).append(", ") })
        string.setLength(string.length - 2)
        return string.append("]").toString()
    }
}