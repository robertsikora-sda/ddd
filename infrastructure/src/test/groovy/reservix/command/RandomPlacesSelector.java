package reservix.command;

import reservix.projection.MeetupPlaceProjection;

import java.util.*;
import java.util.function.Supplier;

class RandomPlacesSelector {

    static Supplier<Set<String>> randomSelector(List<MeetupPlaceProjection> places, int desiredPlaces) {

        return () -> {

            Set<String> selectedPlaces = new HashSet<>();
            do {

                int randomPlace = new Random().nextInt(places.size());
                selectedPlaces.add(places.get(randomPlace).getPlaceId());

            } while (selectedPlaces.size() < desiredPlaces);

            return selectedPlaces;

        };
    }

}
