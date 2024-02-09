/***************************************************************************************************
 * Copyright (c) 2012,2022 University of Southampton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Contributor:
 *   University of Southampton - Initial API and implementation
 **************************************************************************************************/
package utils;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

/**
 * In order to remove some of the complexity of using MIDI in this coursework we have supplied you
 * with a class that carries out the interactions with the MIDI system. MIDI is a set of standard
 * commands that enables devices to communicate musical information to each other. If you are
 * interested feel free to read more about <a href="http://en.wikipedia.org/wiki/MIDI">MIDI</a> (but
 * it should not be necessary to use this class.
 * <p>
 * To use this class you will need to initialise it correctly and pass an instance of the class to
 * your musicians. To setup the <code>SoundSystem</code>, you will need to:
 * <ul>
 * <li>Create a new instance of the <code>SoundSystem</code> using:
 * <code>soundSystem = new SoundSystem();</code></li>
 * <li>You can reinitialise the <code>SoundSystem</code> (and this will stop the current music
 * playing using <code>soundSystem.init()</code></li>
 * <li>You can get the <code>SoundSystem</code> work in silent mode by calling
 * <code>soundSytem.setSilentMote(true)</code></li>
 * <li>You can also enable the text mode (which the <code>SoundSystem</code> will write the notes to
 * the standard output</li>
 * <li>You can use <code>setIntrument(int, int)</code> method to tell the <code>SoundSystem</code>
 * which instrument it is and which seat it is sitting.</li>
 * <li>Finally, you can call <code>playNote(int, int, int)</code> to play a note for a particular
 * instrument</li>
 * </ul>
 *
 * @author Mark Weal - v1.0.0 - Initial API and implementation
 * @author Son Hoang - v2.0.0 - Ensure encapsulation
 * @author Son Hoang - v2.1.0 - Do not reset the operating modes in {@link #init()}.
 */
public class SoundSystem {

  /**
   * The maximum number of instruments.
   */
  public static int MAX_INSTRUMENTS = 16;

  // The synthesizer that will "plays" the sounds.
  private Synthesizer synth;

  // The different MIDI channels (at least 16 channels)
  private MidiChannel[] midiChannels;

  // The last note played on the 16 MIDI channels
  private int[] lastNotes;

  // If silent mode is True, no sounds will be play
  private boolean silentMode;

  // If text mode is True, playing/stopping messages will be printed to the consoles.
  private boolean textMode;

  /**
   * The array list of instruments. Each instrument is represented by an @code{int}. See
   * <a href=https://en.wikipedia.org/wiki/General_MIDI#Program_change_events>Wikipedia</a>
   * for more details.
   */
  private int[] instruments = new int[MAX_INSTRUMENTS];

  /**
   * Create a new synthesizer and open it ready for playing. Add in the instruments from the default
   * soundbank.
   *
   * @throws MidiUnavailableException if the synthesizer is not available due to resource
   *                                  restrictions, or no synthesizer is installed in the system
   */
  public SoundSystem() throws MidiUnavailableException {

    // This should be good enough as a starting point.
    Synthesizer synth = MidiSystem.getSynthesizer();
    if (synth != null) {
      synth.open();
      midiChannels = synth.getChannels();
      Soundbank soundbank = synth.getDefaultSoundbank();
      if (soundbank != null) {
        Instrument[] instruments = soundbank.getInstruments();
        for (Instrument instrument : instruments) {
          synth.loadInstrument(instrument);
        }
      }
    }

    // They want to hear the pretty music
    silentMode = false;
    // They do not want to see any text
    textMode = false;

    // Initialise the sound system
    init();
  }

  /**
   * Method to (re-)initialise the sound system.
   * <ul>
   * <li>The silent mode is False</li>
   * <li>The text mode is False</li>
   * <li>Stop the current playing, if any</li>
   * <li>Reset information about last notes played</li>
   * </ul>
   */
  public void init() {
    // Stop the current playing
    if (lastNotes != null) {
      for (int i = 0; i < MAX_INSTRUMENTS; i++) {
        if (lastNotes[i] != 0) {
          stopNote(i, lastNotes[i]);
        }
      }
    }
    // Reset the lastNotes
    lastNotes = new int[MAX_INSTRUMENTS];
    for (int i = 0; i < MAX_INSTRUMENTS; i++) {
      lastNotes[i] = 0;
    }
  }

  /**
   * Method to set the instrument to the Sound System.
   *
   * @param instrumentPosition The position of the instrument in the Sound System. This must be
   *                           between 0 and 15.
   * @param instrument         The ID of the input instrument. See
   *                           <a href=https://en.wikipedia.org/wiki/General_MIDI#Program_change_events>Wikipedia</a>
   *                           for instrument IDs.
   */
  public void setInstrument(int instrumentPosition, int instrument) {
    midiChannels[instrumentPosition].programChange(instrument);
    instruments[instrumentPosition] = instrument;
  }

  /**
   * Play the input note using the instrument at the given <code>instrumentPosition</code>. If
   * silent mode is enabled then no sounds will be heard. If text mode is enabled then messages will
   * be printed to the console.
   *
   * @param instrumentPosition The position of the instrument in the Sound System. This must be
   *                           between 0 and 15.
   * @param note               The note to be played on the sound system. This is a MIDI note number
   *                           (between 0 and 127).
   * @param loudness           The loudness level of the note to be played.
   */
  public void playNote(int instrumentPosition, int note, int loudness) {
    //If we have MIDI then stop the previous note if there was one and play the new one.
    if (!silentMode) {
      if (lastNotes[instrumentPosition] != 0) {
        stopNote(instrumentPosition, lastNotes[instrumentPosition]);
      }
      if (note != 0) {
        midiChannels[instrumentPosition].noteOn(note, loudness);
      }
      lastNotes[instrumentPosition] = note;
    }
    if (textMode) {
      if (note != 0) {
        System.out.println(
                "Playing note " + note + " on instrument " + instruments[instrumentPosition]);
      } else {
        System.out.println(
                "Silent on instrument " + instruments[instrumentPosition]);
      }
    }
  }

  /**
   * Stop playing the input note for the instrument at the input position immediately (if silent
   * mode is off).
   *
   * @param position The input position of the instrument.
   * @param note     The note to be turned off immediately.
   */
  private void stopNote(int position, int note) {
    if (!silentMode) {
      midiChannels[position].noteOff(note, 0);
    }
    if (textMode) {
      System.out.println(
              "Stop note " + note + " on instrument " + instruments[position]);
    }
  }

  /**
   * Mutator method for setting the silent mode.
   *
   * @param silentMode <code>true</code> to enable the silent mode and <code>false</code> to
   *                   disable the silent mode.
   */
  public void setSilentMode(boolean silentMode) {
    this.silentMode = silentMode;
  }

  /**
   * Mutator method for setting the text mode.
   *
   * @param textMode <code>true</code> to enable the text mode and <code>false</code> to disable
   *                 the text mode.
   */
  public void setTextMode(boolean textMode) {
    this.textMode = textMode;
  }

}