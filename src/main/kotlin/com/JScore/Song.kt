package com.JScore

import java.io.File
import java.io.IOException
import javax.sound.midi.*
import javax.sound.midi.InvalidMidiDataException

/**
 * This represents a song in music.
 * The song object will have 1 field: a list of the notes of the song.
 * It is possible to add notes, chords and chord progression to the song.
 * You can then generate a midi file from the song you have created
 * It is also possible to convert a midi file to a song object and then easily modify it from there.
 * @param resolution The number of midi ticks in 1 quarter note (1 beat)
 * @throws InvalidMidiDataException If the midi resolution is invalid
 */
class Song(resolution: Int) {
    private val midiNoteList: MutableList<MidiNote> = ArrayList()
    private val sequence: Sequence = Sequence(Sequence.PPQ, resolution)
    private val track1: Track = sequence.createTrack()

    /**
     * Gets the all midi notes of this song
     * @return A list of the midi notes of this song
     */
    val midiNotes: List<MidiNote> = midiNoteList.toList()

    /**
     * Adds this midi note to the song
     * @param note The midi note to add
     */
    fun addNote(note: MidiNote) {
        midiNoteList.add(note)
    }

    /**
     * Adds a note to the song
     * @param note The note object to add
     * @param startTime The start time in midi ticks of the note
     * @param length The length in midi ticks of the note
     * @param velocity The velocity of the note
     */
    fun addNote(note: Note, startTime: Long, length: Long, velocity: Byte) {
        addNote(MidiNote(note, startTime, startTime + length, velocity.toInt()))
    }

    /**
     * Adds a chord to the song
     * @param chord The chord object to add
     * @param startTime The start time in midi ticks of the chord
     * @param length The length in midi ticks of the chord
     * @param velocityForEachNote The velocity for each note of the chord
     */
    fun addChord(chord: Chord, startTime: Long, length: Long, velocityForEachNote: Byte) {
        for (note in chord.notes) addNote(note, startTime, length, velocityForEachNote)
    }

    /**
     * Adds a chord progression to the song.
     * @param chordProgression The chord progression object to add
     * @param startTime The start time in midi ticks of the first chord in the progression. Each chord after that will start when the one before ends.
     * @param lengthForEachChord The length in midi ticks of each chord in the progression
     * @param velocityForEachChord The velocity for each note of the chord
     */
    fun addChordProgression(
        chordProgression: ChordProgression,
        startTime: Long,
        lengthForEachChord: Long,
        velocityForEachChord: Byte
    ) {
        for (i in chordProgression.chords.indices) addChord(
            chordProgression.chords[i],
            startTime + i * lengthForEachChord,
            lengthForEachChord,
            velocityForEachChord
        )
    }

    /**
     * Converts a MidiNote to midi messages and adds them to the sequence.
     * @param midiNote The midi note to add.
     * @throws InvalidMidiDataException If the midiNote is invalid.
     */
    @Throws(InvalidMidiDataException::class)
    private fun addNoteToSequence(midiNote: MidiNote) {
        val start = ShortMessage(ShortMessage.NOTE_ON, midiNote.note.midiKey.toInt(), midiNote.velocity)
        val end = ShortMessage(ShortMessage.NOTE_OFF, midiNote.note.midiKey.toInt(), midiNote.velocity)
        val noteStart = MidiEvent(start, midiNote.startTick)
        val noteEnd = MidiEvent(end, midiNote.endTick)
        track1.add(noteStart)
        track1.add(noteEnd)
    }

    /**
     * Writes the song to a midi file.
     * @param fileToWriteTo The file to write the sequence onto.
     * @param fileType The type of midi file to write
     * @throws IOException If an I/O exception occurs
     * @throws InvalidMidiDataException If the midi data is invalid.
     */
    @Throws(IOException::class, InvalidMidiDataException::class)
    fun writeSongToMidiFile(fileToWriteTo: File, fileType: Int) {
        for (midiNote in midiNoteList) addNoteToSequence(midiNote)
        MidiSystem.write(sequence, fileType, fileToWriteTo)
    }

    /**
     * Gets the string representation of this song
     * @return The string representation of this song
     */
    override fun toString(): String {
        val stringBuilder = StringBuilder("Song: [")
        midiNoteList.stream().sorted(Comparator.comparingLong { obj: MidiNote -> obj.startTick })
            .forEach { midiNote: MidiNote? -> stringBuilder.append(midiNote).append(", ") }
        return stringBuilder.toString()
    }

    companion object {
        /**
         * Converts a midi file to a song object.
         * @param midiFile The midi file to convert
         * @return The converted song object.
         * @throws InvalidMidiDataException If the midi file data is invalid.
         * @throws IOException If an I/O  exception occurs
         */
        @Throws(InvalidMidiDataException::class, IOException::class)
        fun convertMidiToSong(midiFile: File): Song {
            val sequence = MidiSystem.getSequence(midiFile)
            val song = Song(sequence.resolution)
            val startNotes: MutableList<MidiNote> = ArrayList()
            for (track in sequence.tracks) {
                for (i in 0 until track.size()) {
                    val event = track[i]
                    val message = event.message
                    if (message is ShortMessage) {
                        val sm = message
                        if (sm.command == ShortMessage.NOTE_ON) startNotes.add(
                            MidiNote(
                                Note(sm.message[1]),
                                event.tick,
                                0L,
                                sm.data2
                            )
                        ) else if (sm.command == ShortMessage.NOTE_OFF) {
                            var midiNote: MidiNote? = null
                            for (notesData in startNotes) {
                                if (notesData.note.midiKey.toInt() == sm.data1) {
                                    midiNote = notesData
                                    startNotes.remove(notesData)
                                    break
                                }
                            }
                            if (midiNote != null) song.addNote(midiNote)
                            // If midi note is null than this means that there is not a Note OFF message that corresponds to a Note On message.
                        }
                    }
                }
            }
            return song
        }
    }
}