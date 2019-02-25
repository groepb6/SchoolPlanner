package tiled.dver;

public enum Thing {

    bookID(-1),
    book2ID(-1),
    book3ID(-1),
    bookStackID(-1),
    laptopID(-1),
    pcID(-1);

    private int number;

    Thing(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

}