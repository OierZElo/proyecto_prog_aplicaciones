package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private static int counter = 0;
	private int cod;
	private String name;
	private int n_songs;
	private int user_id;
	private int duration;
	public List<Song> l_songs;

	public Playlist(String name, int user_id) {
		super();
		this.cod = counter;
		counter += 1;
		this.name = name;
		this.user_id = user_id;
		this.l_songs = new ArrayList<Song>();
	}

	public List<Song> getSongs() {
		return l_songs;
	}
	
	public int getCod() {
		return cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getN_songs() {
		return n_songs;
	}

	public void setN_songs(int n_songs) {
		this.n_songs = n_songs;
	}

	public int getUser_id() {
		return user_id;
	}

	public int getDuration() {
		return duration;
	}

	public List<Song> getL_songs() {
		return l_songs;
	}

	public void addSong(Song s) {
		l_songs.add(s);
		n_songs++;
		duration += s.getDuration();
	}

	public void removeCancion(Song s) {
		if (l_songs.remove(s)) {
			n_songs--;
			duration -= s.getDuration();
		}
	}

	public static String getDurationFormat(int duration) {
		if(duration >= 3600) {
			int hour = duration / 3600;
			int min = (duration % 3600) / 60;
			int sec = duration % 60;
		    return String.format("%d:%02d:%02d", hour, min, sec);}
		else {}
		
		int min = duration / 60;
		int sec = duration % 60;
		return String.format("%d:%02d", min, sec);
	}
	

	public static int parseDuration(String duration) {
		String[] parts = duration.split(":");
		return (60 * (Integer.parseInt(parts[0])) + Integer.parseInt(parts[1]));
	}
}
