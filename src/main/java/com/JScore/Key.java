package com.JScore;

/**
 * This represents a key in music.
 * The key object will have three fields: a mode, a tonic note and therefore a scale.
 * The key can be transposed by a semitones or octaves.
 * It is also possible to get chords based on a specified degrees of the scale.
 */
public class Key extends NoteTransformation {

    private final Mode mode;
    private Note tonic;
    private Scale scale;

    /**
     * Creates a new key with the inputted mode and tonic note
     * @param mode The mode of the key
     * @param tonic The tonic note of the key
     */
    public Key(Mode mode, Note tonic) {
        this.mode = mode;
        this.tonic = tonic;
        this.scale = new Scale(this);
    }

    /**
     * This method will transpose the key by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the key by.
     */
    @Override
    public void transpose(byte semitones) {
        this.tonic = new Note((byte) (this.tonic.getMidiKey() + semitones));
        this.scale = new Scale(this);
    }

    /**
     * Gets the chord at the specified degree of this scale
     * @param degree The degree of the chord
     * @return The chord at the specified degree of this scale
     * @throws IllegalArgumentException If the mode of this key is the chromatic mode or the degree is bigger than the length of the scale or smaller than 0
     */
    public Chord getChordByDegree(int degree) throws IllegalArgumentException {
        if (mode == Mode.Chromatic)
            throw new IllegalArgumentException("You cannot harmonize the chromatic scale! At least I do not know how.");
        if (degree < 1 || degree > scale.getScaleNotes().size())
            throw new IllegalArgumentException("There are " + (scale.getScaleNotes().size() - 1) +  " degrees in the " + tonic.getPitch()  + " " + mode + ", from 1 to " + (scale.getScaleNotes().size() - 1) + "!");
        return scale.getScaleChords().getChords().get(degree - 1);
    }

    /**
     * Gets the scale of this key
     * @return The scale object of this key
     */
    public Scale getScale() { return scale; }

    /**
     * Gets the mode object of this key
     * @return The mode object of this key
     */
    public Mode getMode() { return mode; }

    /**
     * Gets the tonic note of this key
     * @return The tonic note of this key
     */
    public Note getTonic() { return tonic; }

    /**
     * Gets a string representation of this key
     * @return The string representation of this key
     */
    @Override
    public String toString() { return "Key: [mode: " + mode + ", tonic: " + tonic + ", scale: " + scale + ']'; }
}
