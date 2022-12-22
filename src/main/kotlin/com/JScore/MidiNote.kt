package com.JScore

/**
 * This class represents a note that can be put in a midi file.
 * It is not a replacement for the note class, it's sole purpose is to go give a duration and a velocity to a note.
 * @param note The note object associated to the midi note
 * @param startTick The starting midi tick of this midi note
 * @param endTick The ending midi tick of this midi note
 * @param velocity The velocity of this note
 * @throws IllegalArgumentException If the start tick is bigger than the end tick
 *
 */
data class MidiNote(
    val note: Note,
    val startTick: Long,
    val endTick: Long,
    val velocity: Int
) {
    val lengthInMidiTicks: Long
        /**
         * Calculates the length of this midi note in midi ticks.
         * @return The length of this midi note in midi ticks.
         */
        get() = endTick - startTick

    init {
        require(startTick <= endTick) { "The start tick is bigger than the end tick!" }
    }

    /**
     * Gets the string representation of this midi note
     * @return The string representation of this midi note
     */
    override fun toString(): String {
        return "MidiNote: [Note: $note, startTick: $startTick, endTick: $endTick, velocity: $velocity]"
    }
}