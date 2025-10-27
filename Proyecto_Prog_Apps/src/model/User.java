package utils;

import java.util.List;

import model.Song;

import java.util.ArrayList;


public class Utils {
	
// lists generated with AI
    // list of music genres 
	
    public static final List<String> generos = List.of(
        "Rock", "Pop", "Jazz", "Clásica", "Hip-Hop",
        "Reggae", "Electrónica", "Blues", "Country", "Metal"
    );

    //list of songs
    
    public static final List<Song> canciones = new ArrayList<>();

    // Block that add songs
    static {
        canciones.add(new Song("Rock", "Bohemian Rhapsody", "Queen", null, "A Night at the Opera", "5:55"));
        canciones.add(new Song("Pop", "Thriller", "Michael Jackson", null, "Thriller", "5:57"));
        canciones.add(new Song("Jazz", "Take Five", "Dave Brubeck", null, "Time Out", "5:24"));
        canciones.add(new Song("Clásica", "Für Elise", "Beethoven", null, "N/A", "2:53"));
        canciones.add(new Song("Hip-Hop", "Lose Yourself", "Eminem", null, "8 Mile", "5:20"));
        canciones.add(new Song("Reggae", "No Woman No Cry", "Bob Marley", null, "Natty Dread", "7:08"));
        canciones.add(new Song("Electrónica", "One More Time", "Daft Punk", null, "Discovery", "5:20"));
        canciones.add(new Song("Blues", "The Thrill Is Gone", "B.B. King", null, "Completely Well", "5:24"));
        canciones.add(new Song("Country", "Jolene", "Dolly Parton", null, "Jolene", "2:42"));
        canciones.add(new Song("Metal", "Enter Sandman", "Metallica", null, "Metallica", "5:31"));
        canciones.add(new Song("Rock", "Stairway to Heaven", "Led Zeppelin", null, "Led Zeppelin IV", "8:02"));
        canciones.add(new Song("Pop", "Like a Prayer", "Madonna", null, "Like a Prayer", "5:41"));
        canciones.add(new Song("Jazz", "So What", "Miles Davis", null, "Kind of Blue", "9:22"));
        canciones.add(new Song("Clásica", "Moonlight Sonata", "Beethoven", null, "N/A", "15:00"));
        canciones.add(new Song("Hip-Hop", "Juicy", "The Notorious B.I.G.", null, "Ready to Die", "5:02"));
        canciones.add(new Song("Reggae", "Three Little Birds", "Bob Marley", null, "Exodus", "3:00"));
        canciones.add(new Song("Electrónica", "Animals", "Martin Garrix", null, "N/A", "5:03"));
        canciones.add(new Song("Blues", "Crossroads", "Robert Johnson", null, "N/A", "2:35"));
        canciones.add(new Song("Country", "Take Me Home, Country Roads", "John Denver", null, "Poems, Prayers & Promises", "3:10"));
        canciones.add(new Song("Metal", "Paranoid", "Black Sabbath", null, "Paranoid", "2:48"));
    }


}
