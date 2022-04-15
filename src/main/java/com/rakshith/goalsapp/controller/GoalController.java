package com.rakshith.goalsapp.controller;


import com.rakshith.goalsapp.dao.GoalDAO;
import com.rakshith.goalsapp.model.GoalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/goal")
public class GoalController {

    Logger logger = Logger.getLogger(GoalController.class.getName());

    @Autowired
    GoalDAO goalDAO;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createGoal(@RequestBody GoalModel goalModel){
        try {
            Date now = new Date();
            goalModel.setCreatedDateTime(now);
            goalModel.setLastModifiedDateTime(now);
            GoalModel createdGoal = goalDAO.save(goalModel);

            HashMap<String, Object> response = new HashMap<>();
            response.put("data", createdGoal);
            response.put("message", "Success");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {

            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<Map<String, Object>> getAllGoals(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){

        try {

            logger.info("Page: " + page + " Size: " + size);

            List<GoalModel> goals;
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDateTime"));

            Page<GoalModel> goalPage = goalDAO.findAll(pageable);
            goals = goalPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("data", goals);
            response.put("message", "Success");
            response.put("currentPage", goalPage.getNumber());
            response.put("totalItems", goalPage.getTotalElements());
            response.put("totalPages", goalPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> getGoalById(@PathVariable("id") String id){
        try {
            Optional<GoalModel> goalModelObject = goalDAO.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (goalModelObject.isPresent()) {
                response.put("data", goalModelObject.get());
                response.put("message", "Success");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "Not Found");
            response.put("data",null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateGoal(@PathVariable("id") String id, @RequestBody GoalModel goalModel){
        try {
            // get the goal and update it
            Map<String, Object> response = new HashMap<>();

            Optional<GoalModel> goalModelObject = goalDAO.findById(id);
            if (goalModelObject.isPresent()) {
                GoalModel goal = goalModelObject.get();

                goalModel.setId(id);

                if(goalModel.getGoalName() == null) {
                   goalModel.setGoalName(goal.getGoalName());
                }

                if(goalModel.getDescription() == null) {
                    goalModel.setDescription(goal.getDescription());
                }

                if(goalModel.getDueDateTime() == null) {
                    goalModel.setDueDateTime(goal.getDueDateTime());
                }

                goalModel.setCreatedDateTime(goal.getCreatedDateTime());
                goalModel.setLastModifiedDateTime(new Date());

                GoalModel updatedGoal = goalDAO.save(goalModel);



                response.put("data", updatedGoal);
                response.put("message", "Success");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            response.put("message", "Not Found");
            response.put("data",null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            logger.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> deleteGoal(@PathVariable("id") String id){
        try {
            Map<String, Object> response = new HashMap<>();
            goalDAO.deleteById(id);
            response.put("message", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

