package com.JScore;

/**
 * This interface has methods to transpose notes
 */
public interface NoteTransformation {

    /**
     * Transpose the note by the desired amount of semitones
     * @param semitones The amount of semitones to transpose the note by
     */
    void transpose(byte semitones);

    /**
     * Shifts the note by the desired amount of octaves
     * @param nbOfOctaveToShift The number of octave to shift the note by
     */
    void octaveShift(byte nbOfOctaveToShift);
}
