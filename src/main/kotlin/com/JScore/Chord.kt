package com.JScore

import org.jetbrains.annotations.Contract
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors

/**
 * This represents a chord in music.
 * The chord object will have 1 field, a list of notes of the chord.
 * You can easily add intervals on top of the chord.
 * You can utilise the multiple inversion of the chord.
 * You can also stack two chords on top of one another.
 * You can transpose the chord using semitones or octaves.
 * @param rootNote The root note of the chord
 */
class Chord(rootNote: Note) : NoteTransformation() {
    /**
     * Get the notes of this chord.
     * @return The notes of this chord.
     */
    var notes = ArrayList<Note>()
        private set

    init {
        notes.add(rootNote)
    }

    /**
     * This allows to modify a specific note in the chord.
     * @param index The index the note is at.
     * @param modification The modification to apply to the chord.
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds.
     */
    fun modifySpecificNote(index: Int, modification: Function<Note, Note>) {
        val note = notes[index]
        notes.removeAt(index)
        notes.add(index, modification.apply(note))
    }

    /**
     * This method will add a minor second after the last note of the chord.
     * @return The chord object
     */
    fun addMinor2(): Chord {
        return appendANoteSemitonesApartFromLastOne(1.toByte())
    }

    /**
     * This method will add a major second after the last note of the chord.
     * @return The chord object
     */
    fun addMajor2(): Chord {
        return appendANoteSemitonesApartFromLastOne(2.toByte())
    }

    /**
     * This method will add a minor third after the last note of the chord.
     * @return The chord object
     */
    fun addMinor3(): Chord {
        return appendANoteSemitonesApartFromLastOne(3.toByte())
    }

    /**
     * This method will add a major third after the last note of the chord.
     * @return The chord object
     */
    fun addMajor3(): Chord {
        return appendANoteSemitonesApartFromLastOne(4.toByte())
    }

    /**
     * This method will add a fourth after the last note of the chord.
     * @return The chord object
     */
    fun addPerfect4(): Chord {
        return appendANoteSemitonesApartFromLastOne(5.toByte())
    }

    /**
     * This method will add a tritone (augmented fourth) after the last note of the chord.
     * @return The chord object
     */
    fun addTritone(): Chord {
        return appendANoteSemitonesApartFromLastOne(6.toByte())
    }

    /**
     * This method will add a fifth after the last note of the chord.
     * @return The chord object
     */
    fun addPerfect5(): Chord {
        return appendANoteSemitonesApartFromLastOne(7.toByte())
    }

    /**
     * This is will append a note to the chord a certain amount of semitones away from the last note of the chord.
     * @param semitonesApart The amount of semitones away from the last note of the chord
     * @return The chord object
     */
    private fun appendANoteSemitonesApartFromLastOne(semitonesApart: Byte): Chord {
        return appendANewNote(Note((notes[notes.size - 1].midiKey + semitonesApart).toByte()))
    }

    /**
     * This method will add a new note after the last note of
     * the chord.
     * @param noteToAdd The note to add
     * @return The chord object
     */
    fun appendANewNote(noteToAdd: Note): Chord {
        notes.add(noteToAdd)
        return this
    }

    /**
     * Create a major chord with the last note of the chord.
     * @return The chord object
     */
    fun appendMajorChord(): Chord {
        return addMajor3().addMinor3()
    }

    /**
     * Create a minor chord with the last note of the chord.
     * @return The chord object
     */
    fun appendMinorChord(): Chord {
        return addMinor3().addMajor3()
    }

    /**
     * This method will transpose the chord by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the chord by.
     */
    override fun transpose(semitones: Byte) {
        notes = notes.stream()
            .peek { note: Note -> note.transpose(semitones) }
            .collect(Collectors.toList()) as ArrayList<Note>
    }

    /**
     * This method will do an octave shift on certain notes depending on the rootNoteIndex number.
     * @param rootNoteIndex The number of notes starting from the first note to shift by one octave. In other words, the new root note
     * @throws IllegalArgumentException If the rootNoteIndex number is not between 0 and the chord length.
     * @return The chord object
     */
    @Throws(IllegalArgumentException::class)
    fun invertChord(rootNoteIndex: Int): Chord {
        require(!(rootNoteIndex > notes.size || rootNoteIndex < 0)) { "The number of inversion this chord has is " + notes.size }
        for (i in 0 until rootNoteIndex) {
            modifySpecificNote(i) { note: Note ->
                note.octaveShift(1.toByte())
                note
            }
        }
        return this
    }

    /**
     * Gets the string representation of the chord
     * @return The string representation of the chord
     */
    override fun toString(): String {
        notes.sortWith { obj: Note, other: Note -> obj.compareTo(other) }
        val string = StringBuilder("Chord: [")
        notes.forEach(Consumer { note: Note -> string.append(note.pitch).append(", ") })
        string.setLength(string.length - 2)
        return string.append("]").toString()
    }

    companion object {
        /**
         * This method will stack two chords on top of one another.
         * @param bottomChord The chord at the bottom
         * @param topChord The chord at the top
         * @return The bottom chord object with the top chord on top
         */
        @Contract("_, _ -> param1")
        fun stackChords(bottomChord: Chord, topChord: Chord): Chord {
            topChord.notes.forEach(Consumer { noteToAdd: Note -> bottomChord.appendANewNote(noteToAdd) })
            return bottomChord
        }
    }
}