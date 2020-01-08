package reservix.command;

import reservix.projection.MeetupPlacesProjectionDto;

import java.util.*;
import java.util.function.Supplier;

class RandomPlacesSelector {

    static Supplier<Set<String>> randomSelector(List<MeetupPlacesProjectionDto> places, int desiredPlaces) {

        return () -> {

            Set<String> selectedPlaces = new HashSet<>();
            do {

                int randomPlace = new Random().nextInt(places.size() - 1);
                selectedPlaces.add(places.get(randomPlace).getPlaceId());

            } while (selectedPlaces.size() < desiredPlaces);

            return selectedPlaces;

        };
    }

}
