package com.JScore;

import java.util.List;

/**
 * This is an enum containing the 7 modes of the major scale and 4 extra modes being major, harmonic minor, natural minor and chromatic.
 * Pick chromatic if your song/chordProgression has no key.
 * Also, there is no minor harmonic cause not clear when you actually go down.
 */
public enum Mode {

    /**
     * The major scale
     */
    Ionian(List.of(2,2,1,2,2,2,1)),
    /**
     * The major scale with flat 3 and 7
     */
    Dorian(List.of(2,1,2,2,2,1,2)),
    /**
     * The major scale with flat 2, 3, 6 and 7
     */
    Phrygian(List.of(1,2,2,2,1,2,2)),
    /**
     * The major scale with sharp 4
     */
    Lydian(List.of(2,2,2,1,2,2,1)),
    /**
     * The major scale with flat 7
     */
    Mixolydian(List.of(2,2,1,2,2,1,2)),
    /**
     * The natural minor scale or the major scale with a flat 3, 6 and 7
     */
    Aeolian(List.of(2,1,2,2,1,2,2)),
    /**
     * The major scale with flat 2, 3, 5, 6 and 7
     */
    Locrian(List.of(1,2,2,1,2,2,2)),

    /**
     * The normal major scale. It is the same as the Ionian mode.
     */
    Major(List.of(2,2,1,2,2,2,1)),
    /**
     * The normal minor scale. It is the same as the Aeolian mode.
     */
    Minor_Natural(List.of(2,1,2,2,1,2,2)), // The normal minor scale. It is the same as the Aeolian mode.
    /**
     * The natural minor scale with a sharp 7
     */
    Minor_Harmonic(List.of(2,1,2,2,1,3,1)),
    /**
     * The chromatic scale. Pick this one if your chord progression has no specific key.
     */
    Chromatic(List.of(1,1,1,1,1,1,1,1,1,1,1,1));

    private final List<Integer> steps;

    Mode(List<Integer> steps) { this.steps = steps; }

    /**
     * Gets the interval between the notes of this mode
     * @return The interval between the notes of this mode
     */
    public List<Integer> getSteps() { return this.steps; }

}
