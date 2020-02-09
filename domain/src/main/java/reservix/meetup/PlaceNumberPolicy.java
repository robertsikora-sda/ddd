package reservix.meetup;

@FunctionalInterface
interface PlaceNumberPolicy {

    PlaceNumber generate(int place);

}
