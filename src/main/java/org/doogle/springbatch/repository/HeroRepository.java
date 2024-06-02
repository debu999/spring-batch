package org.doogle.springbatch.repository;

import org.bson.types.ObjectId;
import org.doogle.springbatch.entity.Hero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "hero", path = "hero")
public interface HeroRepository extends MongoRepository<Hero, ObjectId> {

    List<Hero> findByName(@Param("name") String name);

    List<Hero> findByPowersAndPicture(@Param("powers") String powers, @Param("picture") String picture);
}