package com.JScore;

public interface NoteTransformation {

    void transpose(byte semitones);

    void octaveShift(byte nbOfOctaveToShift);
}
