package com.JScore

import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors

/**
 * This represents a chord progression in music.
 * The chord progression object will have 2 field, a list of chords of the chord progression and the key of the chord progressions
 * If the chord progression does not have a key, you can put the mode for the key as chromatic.
 * You can easily add chords to the chord progression.
 * You can also get chords based on the scale degrees of the key of the chord progression.
 * You can transpose the chord progression using semitones or octaves.
 * @param key The key of the chord progression.
 */
class ChordProgression(
    /**
     * Gets the key associated with this chord progression
     * @return The key associated with this chord progression
     */
    val key: Key
) : NoteTransformation() {
    /**
     * Gets the list of chords of this chord progression.
     * @return The list of chord of this chord progression
     */
    var chords = ArrayList<Chord>()
        private set

    /**
     * This allows to modify a specific chord in the progression.
     * @param index The index the chord is at.
     * @param modification The modification to apply to the chord.
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds.
     */
    fun modifySpecificChord(index: Int, modification: Function<Chord?, Chord>) {
        val chord = chords[index]
        chords.removeAt(index)
        chords.add(index, modification.apply(chord))
    }

    /**
     * This method will transpose the chord progression by the number of semitones.
     * @param semitones The number of semitones to transpose the chord progression by.
     */
    override fun transpose(semitones: Byte) {
        key.transpose(semitones)
        chords = chords.stream()
            .peek { chord: Chord -> chord.transpose(semitones) }
            .collect(Collectors.toList()) as ArrayList<Chord>
    }

    /**
     * Add the chord of the specified degree in the chord progression scale.
     * @param degree The degree of the key. This will be the root note of the chord.
     * @throws RuntimeException If the key equals No_Key.
     * @throws IllegalArgumentException If the scale degree is not between 1 and 7
     * @return The chord progression object with the added chord or no new chord (if something weird happens)
     */
    fun addChordBasedOnScaleDegree(degree: Int): ChordProgression {
        return addAChord(key.getChordByDegree(degree))
    }

    /**
     * Adds a major chord to the end of the progression.
     * @param rootNote RootNote of the major chord
     * @return The chord progression object
     */
    fun addMajorChord(rootNote: Note?): ChordProgression {
        return addAChord(Chord(rootNote!!).appendMajorChord())
    }

    /**
     * Adds a minor chord to the end of the progression.
     * @param rootNote RootNote of the minor chord
     * @return The chord progression object
     */
    fun addMinorChord(rootNote: Note?): ChordProgression {
        return addAChord(Chord(rootNote!!).appendMinorChord())
    }

    /**
     * Adds a chord to the end of the chord progression.
     * @param chord The chord to add to the progression.
     * @return The chord progression object.
     */
    fun addAChord(chord: Chord): ChordProgression {
        chords.add(chord)
        return this
    }

    /**
     * Gets the string representation of this chord progression
     * @return The string representation of this chord progression
     */
    override fun toString(): String {
        val string = StringBuilder("Chord progression: [").append("Key: ").append(key)
        chords.forEach(Consumer { chord: Chord? -> string.append(", ").append(chord) })
        return string.append("]").toString()
    }
}