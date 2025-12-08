package model;

public enum Genre {
    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    CLASICA("Clásica"),
    HIPHOP("HipHop"),
    REGGAE("Reggae"),
    ELECTRONICA("Electrónica"),
    BLUES("Blues"),
    COUNTRY("Country"),
    METAL("Metal");

    private final String displayName;

    Genre(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}