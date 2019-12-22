package reservix.projection;

import io.vavr.collection.Seq;
import java.util.LinkedList;
import java.util.List;

public class MeetupsProjectionRepo {

    private static final List<MeetupsProjectionDto> INSTANCES = new LinkedList<>();

    MeetupsProjectionDto save(final MeetupsProjectionDto projectionDto) {
        INSTANCES.add(projectionDto);
        return projectionDto;
    }


    Seq<MeetupsProjectionDto> findAll() {
        return io.vavr.collection.List.ofAll(INSTANCES);
    }

}
