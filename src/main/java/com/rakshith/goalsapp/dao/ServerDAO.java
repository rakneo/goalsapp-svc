package com.rakshith.goalsapp.dao;

import com.rakshith.goalsapp.model.ServerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerDAO extends MongoRepository<ServerModel, String> {

    @Query("{'name': { $regex: /.*?0.*/ } }")
    Page<ServerModel> findAllByName(String name, Pageable pageable);
}
