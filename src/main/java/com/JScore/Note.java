package com.JScore;

import org.jetbrains.annotations.NotNull;

/**
 * This represents a note in music.
 * The note object will have 1 field, the midi key, which will determine the pitch of this note.
 * The note can be transposed by a semitones or octaves.
 * It is also possible to compare two notes based on their midi keys.
 */
public class Note extends NoteTransformation implements Comparable<Note> {

    private byte midiKey;
    private final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    /**
     * Creates a note with the specified midi key.
     * @param midiKey The midi key of the note
     * @throws IllegalArgumentException If the midi key is not a positive number
     */
    public Note(byte midiKey) {
        if (midiKey < 0)
            throw new IllegalArgumentException("The midiKey needs to be a positive number.");
        this.midiKey = midiKey;
    }

    /**
     * This method will transpose the note by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the note by.
     * @throws IllegalArgumentException If the midi key is not between 0 and 127.
     */
    @Override
    public void transpose(byte semitones) {
        if (this.midiKey + semitones > 127 || this.midiKey + semitones < 0)
            throw new IllegalArgumentException("The midiKey of this note is more than 127 or it is less than 0 and so it is out of range.");
        this.midiKey += semitones;
    }

    /**
     * This method will compare two notes together based on their midi key.
     * @param o The other notes to compare the note to.
     * @return The result of the midi key minus the other note's midi key.
     */
    @Override
    public int compareTo(@NotNull Note o) { return this.midiKey - o.midiKey; }

    /**
     * Gets the midi key (number between 0 and 127, where 0 is the lowest pitch  and 127 the highest) of this note.
     * A midi key of 69 is the 440 A (concert pitch).
     * So a midi key of 60 is C4 (middle C on a piano).
     * @return The midi key of this note
     */
    public byte getMidiKey() { return this.midiKey; }

    /**
     * Sets the midi key of this note
     * @param midiKey The new midi key of this note
     */
    public void setMidiKey(byte midiKey) { this.midiKey = midiKey; }

    /**
     * Gets the octave of this note based on the midi key
     * @return The octave of this note, which is a number from -1 to 9
     */
    public int getOctave() { return midiKey / 12 - 1; }

    /**
     * Gets the note number of this note object, assuming where are in 12 tone equal temperament
     * @return The note number of this note object, which is a number from 0 to 11, where 0 is C and 11 is B
     */
    public int getNote() { return midiKey % 12; }

    /**
     * Gets the pitch of this note object
     * @return The pitch of this note object, which will only have a sharp or no sharp. There will be no flats :(
     */
    public String getPitch() { return NOTE_NAMES[getNote()] + getOctave(); }

    /**
     * Gets the string representation of this note object
     * @return The string representation of this note object
     */
    @Override
    public String toString() { return "Note [pitch: " + getPitch() + ", midiKey: " + midiKey + "]"; }

}
