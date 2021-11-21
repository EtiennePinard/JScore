package com.JScore;

public class Note implements NoteTransformation, Comparable<Note> {

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
     */
    @Override
    public void transpose(byte semitones) {
        if (this.midiKey + semitones > 127 || this.midiKey + semitones < 0)
            throw new IllegalArgumentException("The midiKey of this note is more than 127 or it is less than 0 and so it is out of range.");
        this.midiKey += semitones;
    }

    /**
     * This method will shift the note by a certain amount of octaves (12 semitones).
     * @param nbOfOctaveToShift The number of octaves to shift the note by.
     */
    @Override
    public void octaveShift(byte nbOfOctaveToShift) { transpose((byte) (nbOfOctaveToShift * 12)); }

    /**
     * This method will compare the midikey of the notes.
     * @param o The other notes to compare the note to.
     * @return The result of the midikey - the other note's midikey.
     */
    @Override
    public int compareTo(Note o) { return this.midiKey - o.midiKey; }

    public byte getMidiKey() { return this.midiKey; }
    public void setMidiKey(byte midiKey) { this.midiKey = midiKey; }

    public int getOctave() { return midiKey / 12 - 1; }

    public int getNote() { return midiKey % 12; }

    public String getPitch() { return NOTE_NAMES[getNote()] + getOctave(); }

    @Override
    public String toString() { return "Note [pitch: " + getPitch() + ", midiKey: " + midiKey + "]"; }

}
