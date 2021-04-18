package pl.merdala.cleantimetracker.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface EntityMapper<ENTITY, DOMAIN_OBJECT> {

    ENTITY toEntity(DOMAIN_OBJECT domainObject);

    default List<ENTITY> toEntities(List<DOMAIN_OBJECT> domainObjects) {
        return domainObjects.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    DOMAIN_OBJECT toDomainObject(ENTITY entity);

    default List<DOMAIN_OBJECT> toDomainObjects(Iterable<ENTITY> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toDomainObject)
                .collect(Collectors.toList());
    }

}
