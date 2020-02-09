package reservix.meetup;

@FunctionalInterface
public interface PlaceNumberPolicy {

    PlaceNumber generate(int place);

}
