package com.JScore;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Song {

    private final List<MidiNote> midiNoteList = new ArrayList<>();
    private final Sequence sequence;
    private final Track track1;

    /**
     *  Creates a new song object using the given resolution
     * @param resolution The number of ticks in 1 quarter note (1 beat)
     * @throws InvalidMidiDataException If the midi resolution is invalid
     */
    public Song(int resolution) throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ,resolution);
        this.track1 = sequence.createTrack();
    }

    /**
     * Adds a note to the song
     * @param note The note object to add
     * @param startTime The start time in midi ticks of the note
     * @param length The length in midi ticks of the note
     * @param velocity The velocity of the note
     */
    public void addNote(Note note, long startTime, long length, byte velocity) {
        midiNoteList.add(new MidiNote(note,startTime,startTime + length,velocity));
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
        ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON, midiNote.getNote().getMidiKey(), midiNote.velocity);
        ShortMessage end = new ShortMessage(ShortMessage.NOTE_OFF, midiNote.getNote().getMidiKey(), midiNote.velocity);
        MidiEvent noteStart = new MidiEvent(start, midiNote.startTick);
        MidiEvent noteEnd = new MidiEvent(end, midiNote.endTick);
        track1.add(noteStart);
        track1.add(noteEnd);
    }

    public List<MidiNote> getMidiNotes() { return midiNoteList; }

    /**
     * Writes the sequence to a midi file.
     * @param fileToWriteTo The file to write the sequence onto.
     * @param fileType The type of midi file to write
     * @throws IOException If an I/O exception occurs
     */
    public void writeSongToMidiFile(File fileToWriteTo, int fileType) throws IOException, InvalidMidiDataException {
        for (MidiNote midiNote : midiNoteList)
            addNoteToSequence(midiNote);
        MidiSystem.write(sequence,fileType,fileToWriteTo);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Song: [");
        midiNoteList.stream().sorted((note1, note2) -> Long.compare(note1.getStartTick(),note2.endTick)).forEach(midiNote -> stringBuilder.append(midiNote).append(", "));
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
                            if (notesData.note.getMidiKey() == sm.getData1()) {
                                midiNote = notesData;
                                startNotes.remove(notesData);
                                break;
                            }
                        if (midiNote == null)
                            throw new RuntimeException("There is a note off message in your midi file that doesn't match with a note on message.");
                        song.addNote(midiNote.note, (int) midiNote.startTick, (int) (event.getTick() - midiNote.startTick), (byte) midiNote.velocity);

                    }
                }
            }
        }
        return song;
    }

    public static class MidiNote {
        private Note note;
        private long startTick;
        private long endTick;
        private int velocity;

        public MidiNote(Note note, long startTick, int velocity) {
            this.note = note;
            this.startTick = startTick;
            this.velocity = velocity;
        }

        public MidiNote(Note note, long startTick, long endTick, int velocity) {
            this.note = note;
            this.startTick = startTick;
            this.endTick = endTick;
            this.velocity = velocity;
        }

        public Note getNote() { return note; }
        public void setNote(Note note) { this.note = note; }

        public long getStartTick() { return startTick; }
        public void setStartTick(long startTick) { this.startTick = startTick; }

        public long getEndTick() { return endTick; }
        public void setEndTick(long endTick) { this.endTick = endTick; }

        public int getVelocity() { return velocity; }
        public void setVelocity(int velocity) { this.velocity = velocity; }

        @Override
        public String toString() {
            return "MidiNote: [" + note + ", startTick: " + startTick + ", endTick: " + endTick + ", velocity: " + velocity + "]";
        }
    }
}
