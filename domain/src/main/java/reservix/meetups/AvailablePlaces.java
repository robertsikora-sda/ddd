package reservix.meetups;

class AvailablePlaces {

    private int number;

    public AvailablePlaces(int number) {
        if(number < 1) {
            throw new IllegalArgumentException("");
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
