package com.JScore;

/**
 * This class represents a note that can be put in a midi file.
 * It is not a replacement for the note class, it's sole purpose is to go give a duration and a velocity to a note.
 */
public class MidiNote {
    private long startTick;
    private long endTick;
    private int velocity;
    private Note note;

    /**
     * Creates a midi note object with the specified parameters. The end tick will be equals to the start tick, making the length zero.
     * @param note The note object associated to the midi note
     * @param startTick The starting midi tick of this midi note
     * @param velocity The velocity of this note
     */
    public MidiNote(Note note, long startTick, int velocity) {
        this.note = note;
        this.startTick = startTick;
        this.velocity = velocity;
        this.endTick = startTick;
    }

    /**
     * Creates a midi note object with the specified parameters
     * @param note The note object associated to the midi note
     * @param startTick The starting midi tick of this midi note
     * @param endTick The ending midi tick of this midi note
     * @param velocity The velocity of this note
     * @throws IllegalArgumentException If the start tick is bigger than the end tick
     */
    public MidiNote(Note note, long startTick, long endTick, int velocity) throws IllegalArgumentException {
        if (startTick > endTick)
            throw new IllegalArgumentException("The start tick is  bigger than the end tick!");
        this.note = note;
        this.startTick = startTick;
        this.endTick = endTick;
        this.velocity = velocity;
    }

    /**
     * Calculates the length of this midi note in midi ticks.
     * @return The length of this midi note in midi ticks.
     */
    public long getLengthInMidiTicks() { return endTick - startTick; }

    /**
     * Gets the note associated to this midi note
     * @return The note associated to this midi note
     */
    public Note getNote() { return note; }

    /**
     * Sets the note associated to this midi note
     * @param note The new note associated to this midi note
     */
    public void setNote(Note note) { this.note = note; }

    /**
     * Gets the start midi tick of this midi note
     * @return The start midi tick of this midi note
     */
    public long getStartTick() { return startTick; }

    /**
     * Sets the start midi tick of this midi note
     * @param startTick The new start midi tick of this midi note
     */
    public void setStartTick(long startTick) { this.startTick = startTick; }

    /**
     * Gets the endTick midi tick of this midi note
     * @return The endTick midi tick of this midi note
     */
    public long getEndTick() { return endTick; }

    /**
     * Sets the end tick midi tick of this midi
     * @param endTick The new end tick of this midi note
     */
    public void setEndTick(long endTick) { this.endTick = endTick; }

    /**
     * Gets the velocity of this midi note
     * @return The velocity of this midi note
     */
    public int getVelocity() { return velocity; }

    /**
     * Sets the velocity of this midi note
     * @param velocity The new velocity of this midi note
     */
    public void setVelocity(int velocity) { this.velocity = velocity; }

    /**
     * Gets the string representation of this midi note
     * @return The string representation of this midi note
     */
    @Override
    public String toString() {
        return "MidiNote: [Note: " + note + ", startTick: " + startTick + ", endTick: " + endTick + ", velocity: " + velocity + "]";
    }
}
