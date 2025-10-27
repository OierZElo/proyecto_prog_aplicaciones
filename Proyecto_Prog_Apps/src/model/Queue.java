package model;

import java.util.ArrayList;

public class Queue {
    private ArrayList<Song> queue;
    private int currentIndex;
    
    public Queue() {
    	queue = new ArrayList<>();
        currentIndex = 0;
    }
    
    public void enqueue(Song song) {
        queue.add(song);
    }
    
    public Song getCurrentSong() {
        if (currentIndex >= 0 && currentIndex < queue.size()) {
            return queue.get(currentIndex);
        }
        return null;
    }
    
    public void moveUp(int index) {}
    
    public void moveDown(int index) {}
    
    public void remove(int index) {}

}
