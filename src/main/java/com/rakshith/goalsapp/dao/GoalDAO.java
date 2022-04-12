package com.rakshith.goalsapp.dao;

import com.rakshith.goalsapp.model.GoalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalDAO extends MongoRepository<GoalModel, String> {
}
