package model;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Song {
	private static int counter = 0;
	private int cod;
	private String title;
	private int duration;
	private String band;
	private Genre genre;
	private ImageIcon image; 

	// constructor
	public Song(String title, int duration, String band, Genre genre) {
		this.cod = counter;
		counter += 1;
		this.title = title;
		this.duration = duration;
		this.band = band;
		this.genre = genre;
		String path = "src/resources/icons/" + title + ".png"; 
		File file = new File(path);

		if(file.exists()){
		    this.image = new ImageIcon(path);
		} else {
		    System.out.println("Song Image file not found: " + path);
		    this.image = new ImageIcon("src/resources/icons/SongIcon.png");
		}
		
	}
	
	public Song(String title, int duration, String band) {
		this.cod = counter;
		counter += 1;
		this.title = title;
		this.duration = duration;
		this.band = band;
		
		String path = "src/resources/icons/" + title + ".png"; 
		File file = new File(path);

		if(file.exists()){
		    this.image = new ImageIcon(path);
		} else {
		    System.out.println("Song Image file not found: " + path);
		    this.image = new ImageIcon("src/resources/icons/SongIcon.png");
		}
		
	}

	// getters and setters
	

	public void setId(int cod) {
		this.cod = cod;
	}
	
	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
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

}
