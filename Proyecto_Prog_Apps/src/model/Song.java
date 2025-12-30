package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Song implements Comparable<Song> {
	private static int counter = 0;
	private int cod;
	private String title;
	private int duration;
	private String band;
	private Genre genre;

	// constructor
	public Song(String title, int duration, String band, Genre genre) {
		this.cod = counter;
		counter += 1;
		this.title = title;
		this.duration = duration;
		this.band = band;
		this.genre = genre;
		
		
	}
	
	public Song(String title, int duration, String band) {
		this.cod = counter;
		counter += 1;
		this.title = title;
		this.duration = duration;
		this.band = band;
		
		
		
	}

	// getters and setters
	

	public void setId(int cod) {
		this.cod = cod;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public int getCod() {
		return cod;
	}
	
	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public int compareTo(Song o) {
        return this.title.compareToIgnoreCase(o.title);
	}



	@Override
	public int hashCode() {
		return Objects.hash(band, duration, genre, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		return Objects.equals(band, other.band) && duration == other.duration && genre == other.genre
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return title +" - "+ band;
	}


	

}
