package com.JScore;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Song {

    private final Sequence sequence;
    private final Track track1;

    public Song(int resolution) throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ,resolution);
        this.track1 = sequence.createTrack();
    }

    /**
     * Adds a note to a midi sequence.
     * @param note The note object to add
     * @param startTime The start time of the note
     * @param lengthInMidiTicks The length of the note
     * @param velocity The velocity of the note
     * @throws InvalidMidiDataException If the midi data is invalid
     */
    public void addNote(Note note, int startTime, int lengthInMidiTicks, int velocity) throws InvalidMidiDataException {
        ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON,note.getMidiKey(),velocity);
        ShortMessage end = new ShortMessage(ShortMessage.NOTE_OFF,note.getMidiKey(),velocity);
        MidiEvent noteStart = new MidiEvent(start,startTime);
        MidiEvent noteEnd = new MidiEvent(end,startTime + lengthInMidiTicks);
        track1.add(noteStart);
        track1.add(noteEnd);
    }

    /**
     * Adds a chord to a midi sequence.
     * @param chord The chord object to add
     * @param startTime The start time of the chord
     * @param lengthInMidiTicks The length of the chord
     * @param velocityForEachNote The velocity for each note of the chord
     * @throws InvalidMidiDataException If the midi data is invalid
     */
    public void addChord(Chord chord, int startTime, int lengthInMidiTicks, int velocityForEachNote) throws InvalidMidiDataException {
        for (Note note : chord.getNotes())
            addNote(note, startTime, lengthInMidiTicks, velocityForEachNote);
    }

    /**
     * Adds a chord to a midi sequence.
     * @param chordProgression The chord progression object to add
     * @param startTime The start time of the first chord in the progression. Each chord after that will start when the one before ends.
     * @param lengthInMidiTicksForEachChord The length of each chord in the progression
     * @param velocityForEachChord The velocity for each note of the chord
     * @throws InvalidMidiDataException If the midi data is invalid
     */
    public void addChordProgression(ChordProgression chordProgression, int startTime, int lengthInMidiTicksForEachChord, int velocityForEachChord) throws InvalidMidiDataException {
        for (int i = 0; i < chordProgression.getChords().size(); i ++)
            addChord(chordProgression.getChords().get(i),startTime * (i + 1),lengthInMidiTicksForEachChord,velocityForEachChord);
    }

    public Sequence getSequence() { return sequence; }

    /**
     * Writes the sequence to a midi file.
     * @param fileToWriteTo The file to write the sequence onto.
     * @param fileType The type of midi file to write
     * @throws IOException If an I/O exception occurs
     */
    public void writeSongToMidiFile(File fileToWriteTo, int fileType) throws IOException {
        MidiSystem.write(sequence,fileType,fileToWriteTo);
    }
}
