package people.musicians;

public interface Musician {                                                                         //Creating an interface Musician
    public void setSeat(int seat);                                                                  //Creating a setSeat() method which assigns a seat to the musician

    public void readScore(int[] notes, boolean soft);                                               //Creating a readScore() method which adds the notes to List<Integer>

    public void playNextNote();                                                                     //Creating a playNextNote() method which plays the next note

    public int getInstrumentID();                                                                   //Creating a getInstrumentID() method which returns the instrument ID

}
