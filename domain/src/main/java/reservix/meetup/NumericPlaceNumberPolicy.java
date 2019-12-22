package reservix.meetup;

class NumericPlaceNumberPolicy implements PlaceNumberAssignPolicy {

    private int number = 1;

    @Override
    public PlaceNumber next() {
        return new PlaceNumber(String.valueOf(number++));
    }

}
