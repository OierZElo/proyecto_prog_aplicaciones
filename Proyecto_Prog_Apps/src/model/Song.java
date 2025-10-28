package model;

public class Song {
	private static int counter = 0;
	private int cod;
	private String title;
	private int duration;
	private String band;

	// constructor
	public Song(String title, int duration, String band) {
		this.cod = counter;
		counter += 1;
		this.title = title;
		this.duration = duration;
		this.band = band;
	}

	// getters and setters
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

}
