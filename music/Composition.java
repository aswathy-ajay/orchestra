package music;

import java.util.List;

public interface Composition {                                                                      //Creating an interface Composition
    public String getName();                                                                        //Creating a method which returns the name of the composition

    public void addScore(String instrumentName, List<String> notes, boolean soft);                  //Creating a method which converts MIDI notes to Integer list of notes

    public MusicScore[] getScores();                                                                //Creating a method which returns the MusicScore[]

    public int getLength();                                                                         //Creating a method which returns the length of the composition

    public int getNoteLength();                                                                     //Creating a method which returns the length of each note based on the tempo

}