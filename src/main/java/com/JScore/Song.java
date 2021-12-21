package com.JScore;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This represents a song in music.
 * The song object will have 1 field: a list of the notes of the song.
 * It is possible to add notes, chords and chord progression to the song.
 * You can then generate a midi file from the song you have created
 * It is also possible to convert a midi file to a song object and then easily modify it from there.
 */
public class Song {

    private final List<MidiNote> midiNoteList = new ArrayList<>();
    private final Sequence sequence;
    private final Track track1;

    /**
     *  Creates a new song object using the given resolution
     * @param resolution The number of midi ticks in 1 quarter note (1 beat)
     * @throws InvalidMidiDataException If the midi resolution is invalid
     */
    public Song(int resolution) throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ,resolution);
        this.track1 = sequence.createTrack();
    }

    /**
     * Adds this midi note to the song
     * @param note The midi note to add
     */
    public void addNote(MidiNote note) { midiNoteList.add(note); }

    /**
     * Adds a note to the song
     * @param note The note object to add
     * @param startTime The start time in midi ticks of the note
     * @param length The length in midi ticks of the note
     * @param velocity The velocity of the note
     */
    public void addNote(Note note, long startTime, long length, byte velocity) {
       addNote(new MidiNote(note,startTime,startTime + length,velocity));
    }

    /**
     * Adds a chord to the song
     * @param chord The chord object to add
     * @param startTime The start time in midi ticks of the chord
     * @param length The length in midi ticks of the chord
     * @param velocityForEachNote The velocity for each note of the chord
     */
    public void addChord(Chord chord, long startTime, long length, byte velocityForEachNote) {
        for (Note note : chord.getNotes())
            addNote(note,startTime,length,velocityForEachNote);
    }

    /**
     * Adds a chord progression to the song.
     * @param chordProgression The chord progression object to add
     * @param startTime The start time in midi ticks of the first chord in the progression. Each chord after that will start when the one before ends.
     * @param lengthForEachChord The length in midi ticks of each chord in the progression
     * @param velocityForEachChord The velocity for each note of the chord
     */
    public void addChordProgression(ChordProgression chordProgression, int startTime, int lengthForEachChord, int velocityForEachChord) {
        for (int i = 0; i < chordProgression.getChords().size(); i ++)
            addChord(chordProgression.getChords().get(i), (long) startTime * (i + 1),lengthForEachChord, (byte) velocityForEachChord);
    }

    private void addNoteToSequence(MidiNote midiNote) throws InvalidMidiDataException {
        ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON, midiNote.getNote().getMidiKey(), midiNote.getVelocity());
        ShortMessage end = new ShortMessage(ShortMessage.NOTE_OFF, midiNote.getNote().getMidiKey(), midiNote.getVelocity());
        MidiEvent noteStart = new MidiEvent(start, midiNote.getStartTick());
        MidiEvent noteEnd = new MidiEvent(end, midiNote.getEndTick());
        track1.add(noteStart);
        track1.add(noteEnd);
    }

    /**
     * Gets the all midi notes of this song
     * @return A list of the midi notes of this song
     */
    public List<MidiNote> getMidiNotes() { return midiNoteList; }

    /**
     * Writes the song to a midi file.
     * @param fileToWriteTo The file to write the sequence onto.
     * @param fileType The type of midi file to write
     * @throws IOException If an I/O exception occurs
     */
    public void writeSongToMidiFile(File fileToWriteTo, int fileType) throws IOException, InvalidMidiDataException {
        for (MidiNote midiNote : midiNoteList)
            addNoteToSequence(midiNote);
        MidiSystem.write(sequence,fileType,fileToWriteTo);
    }

    /**
     * Gets the string representation of this song
     * @return The string representation of this song
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("com.com.ejrp.JScore.com.ejrp.JScore.Song: [");
        midiNoteList.stream().sorted(Comparator.comparingLong(MidiNote::getStartTick)).forEach(midiNote -> stringBuilder.append(midiNote).append(", "));
        return stringBuilder.toString();
    }

    /**
     * Converts a midi file to a song object.
     * @param midiFile The midi file to convert
     * @return The converted song object.
     * @throws InvalidMidiDataException If the midi file data is invalid
     * @throws IOException If an I/O  exception occurs
     */
    public static Song convertMidiToSong(File midiFile) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(midiFile);
        Song song = new Song(sequence.getResolution());
        List<MidiNote> startNotes = new ArrayList<>();

        for (Track track : sequence.getTracks()) {

            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    if (sm.getCommand() == ShortMessage.NOTE_ON)
                        startNotes.add(new MidiNote(new Note(sm.getMessage()[1]), event.getTick(), sm.getData2()));
                    else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                        MidiNote midiNote = null;
                        for (MidiNote notesData : startNotes)
                            if (notesData.getNote().getMidiKey() == sm.getData1()) {
                                midiNote = notesData;
                                startNotes.remove(notesData);
                                break;
                            }
                        // Should probably ignore this and just continue.
                        if (midiNote == null)
                            throw new RuntimeException("There is a note off message in your midi file that doesn't match with a note on message.");
                        song.addNote(midiNote);
                    }
                }
            }
        }
        return song;
    }
}
