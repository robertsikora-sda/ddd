package reservix.projection;

import io.vavr.collection.Set;
import io.vavr.collection.HashSet;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

@Singleton
class MeetupsProjectionInMemoryRepo {

    private static final List<MeetupsProjectionDto> MEETUPS_PROJECTION = new LinkedList<>();

    MeetupsProjectionDto save(final MeetupsProjectionDto projectionDto) {
        MEETUPS_PROJECTION.add(projectionDto);
        return projectionDto;
    }

    Set<MeetupsProjectionDto> findAll() {
        return HashSet.ofAll(MEETUPS_PROJECTION);
    }

}
