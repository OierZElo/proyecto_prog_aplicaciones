package model;

import java.util.ArrayList;


public class Queue {
	private static ArrayList<Song> queue;

	public Queue() {
		queue = new ArrayList<>();
		
	}

	public void enqueue(Song song) {
		queue.add(song);
	}

	public ArrayList<Song> getQueue() {
		return queue;
	}

	public void moveUp(int index) {
		Song temp = queue.get(index - 1);
		queue.set(index - 1, queue.get(index));
		queue.set(index, temp);
	}

	public void moveDown(int index) {
		Song temp = queue.get(index + 1);
		queue.set(index + 1, queue.get(index));
		queue.set(index, temp);
	}

	public static void remove(int index) {
		queue.remove(index);
	}
	
	public boolean contains(Song s) {
		boolean exists = false;
		for(Song song: queue) {
			if(song.getCod()==s.getCod()) {
				exists = true;
			}
		}
		return exists;
	}

}
