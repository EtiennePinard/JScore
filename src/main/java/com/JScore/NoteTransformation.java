package com.JScore;

/**
 * This interface has methods to transpose notes
 */
public abstract class NoteTransformation {

    /**
     * Transpose the note by the desired amount of semitones
     * @param semitones The amount of semitones to transpose the note by
     */
    public abstract void transpose(byte semitones);

    /**
     * Shifts the note by the desired amount of octaves
     * @param nbOfOctaveToShift The number of octave to shift the note by
     */
     public void octaveShift(byte nbOfOctaveToShift) { transpose((byte) (nbOfOctaveToShift * 12)); }
}
