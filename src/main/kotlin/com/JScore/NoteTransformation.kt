package com.JScore

/**
 * This interface has methods to transpose notes
 */
abstract class NoteTransformation {
    /**
     * Transpose the note by the desired amount of semitones
     * @param semitones The amount of semitones to transpose the note by
     */
    abstract fun transpose(semitones: Byte)

    /**
     * Shifts the note by the desired amount of octaves
     * @param nbOfOctaveToShift The number of octave to shift the note by
     */
    fun octaveShift(nbOfOctaveToShift: Byte) = transpose((nbOfOctaveToShift * 12).toByte())
}