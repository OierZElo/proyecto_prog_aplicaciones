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
	private List<Song> l_songs;
	
	public Playlist(String name, int n_songs, int user_id, int duration, List<Song> l_songs) {
		super();
		this.cod = counter;
		counter += 1;
		this.name = name;
		this.n_songs = 0;
		this.user_id = user_id;
		this.duration = 0;
		this.l_songs = new ArrayList<Song>();
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

	public void setDuration(int duration) {
		this.duration = duration;
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
	
	public String getDurationFormat() {
        int min = duration / 60;
        int sec = duration % 60;
        return String.format("%d:%02d", min, sec);
    }
}
