package com.JScore

/**
 * This is an enum containing the 7 modes of the major scale and 4 extra modes being major, harmonic minor, natural minor and chromatic.
 * Pick chromatic if your song/chordProgression has no key.
 * Also, there is no minor harmonic cause not clear when you actually go down.
 */
enum class Mode(
    /**
     * Gets the interval between the notes of this mode
     * @return The interval between the notes of this mode
     */
    val steps: List<Int>
) {
    /**
     * The major scale
     */
    Ionian(listOf(2, 2, 1, 2, 2, 2, 1)),

    /**
     * The major scale with flat 3 and 7
     */
    Dorian(listOf(2, 1, 2, 2, 2, 1, 2)),

    /**
     * The major scale with flat 2, 3, 6 and 7
     */
    Phrygian(listOf(1, 2, 2, 2, 1, 2, 2)),

    /**
     * The major scale with sharp 4
     */
    Lydian(listOf(2, 2, 2, 1, 2, 2, 1)),

    /**
     * The major scale with flat 7
     */
    Mixolydian(listOf(2, 2, 1, 2, 2, 1, 2)),

    /**
     * The natural minor scale or the major scale with a flat 3, 6 and 7
     */
    Aeolian(listOf(2, 1, 2, 2, 1, 2, 2)),

    /**
     * The major scale with flat 2, 3, 5, 6 and 7
     */
    Locrian(listOf(1, 2, 2, 1, 2, 2, 2)),

    /**
     * The normal major scale. It is the same as the Ionian mode.
     */
    Major(listOf(2, 2, 1, 2, 2, 2, 1)),

    /**
     * The normal minor scale. It is the same as the Aeolian mode.
     */
    Minor_Natural(listOf(2, 1, 2, 2, 1, 2, 2)),  // The normal minor scale. It is the same as the Aeolian mode.

    /**
     * The natural minor scale with a sharp 7
     */
    Minor_Harmonic(listOf(2, 1, 2, 2, 1, 3, 1)),

    /**
     * The chromatic scale. Pick this one if your chord progression has no specific key.
     */
    Chromatic(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))

}