package people.musicians;

import people.Person;
import utils.SoundSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Violinist extends Person implements Musician {                                         //Creating a class which extends the Person class and implements the Musician Interface
    private int instrumentID;                                                                       //Creating a variable which stores the instrument ID
    private List<Integer> notes;                                                                    //Creating a variable which stores the notes
    private Iterator<Integer> nextNote;                                                             //Creating an iterator
    private SoundSystem soundSystem;                                                                //Creating a variable of data type SoundSystem
    private int seat;                                                                               //Creating a variable which stores the seat
    private int loudness;                                                                           //Creating a variable which stores the value of loudness

    public Violinist(String name, SoundSystem soundSystem)  {                                       //Creating a constructor
        super(name);
        this.soundSystem = soundSystem;
        this.instrumentID=41;
        this.notes=new ArrayList<>();

    }
    public Violinist(String name, SoundSystem soundSystem,int seat){                                //Creating a constructor
        super(name);
        this.soundSystem = soundSystem;
        this.instrumentID=41;
        this.notes=new ArrayList<>();
        this.seat=seat;
    }
    @Override
    public void setSeat(int seat) {                                                                 //Implementing setSeat() method which sets the seat of the musician
        soundSystem.setInstrument(seat,instrumentID);
        this.seat=seat;
    }

    @Override
    public void readScore(int[] notesList, boolean soft) {                                          //Implementing the readScore() method
        for (int n: notesList){
            notes.add(n);                                                                           //Adding notes to List<Integer>
        }
        if(soft){
            loudness=50;                                                                            //If it is soft, loudness is assigned this value
        }
        else{
            loudness=100;                                                                           //If it is not soft, loudness is assigned this value
        }
        nextNote=notes.iterator();                                                                  //Initializing the iterator
    }

    @Override
    public void playNextNote() {                                                                    //Implementing the playNextNote() method which plays the next note
        if(nextNote.hasNext()){
            Integer note = nextNote.next();
            soundSystem.playNote(seat,note,loudness);                                               //Playing the note with the help of SoundSystem
        }
    }
    public int getInstrumentID(){                                                                   //Implementing the getInstrumentID() method which returns the instrument ID
        return instrumentID;
    }
}
