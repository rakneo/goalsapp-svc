package com.rakshith.goalsapp.controller;


import com.rakshith.goalsapp.dao.ServerDAO;
import com.rakshith.goalsapp.model.ServerModel;
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
@RequestMapping("/server")
public class ServerController {
    Logger logger = Logger.getLogger(ServerController.class.getName());

    @Autowired
    ServerDAO serverDAO;

    @PostMapping("/createServer")
    public ResponseEntity<Map<String, Object>> createServer(@RequestBody ServerModel body) {
        try {
            Date now = new Date();
            body.setCreatedDateTime(now);
            body.setLastModifiedDateTime(now);
            ServerModel createdServer = serverDAO.save(body);

            HashMap<String, Object> response = new HashMap<>();
            response.put("data", createdServer);
            response.put("message", "Success");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllServers")
    public ResponseEntity<Map<String, Object>> getAllServers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String searchQuery
    ) {

        try {

            List<ServerModel> servers;
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDateTime"));
//            if (!searchQuery.isEmpty()) {
//                Query query = new Query(new Criteria("name").regex("/.*" + searchQuery + ".*/")).with(pageable);
//                servers = serverDAO.find(query, ServerModel.class);
//            }
            Page<ServerModel> serverPage = serverDAO.findAllByName(searchQuery, pageable);

            servers = serverPage.getContent();

            if (servers.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "No servers found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", servers);
            response.put("message", "Success");
            response.put("currentPage", serverPage.getNumber());
            response.put("totalItems", serverPage.getTotalElements());
            response.put("totalPages", serverPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getServerById/{id}")
    public ResponseEntity<Map<String, Object>> getServerById(@PathVariable("id") String id) {
        try {
            Optional<ServerModel> server = serverDAO.findById(id);

            if (server.isPresent()) {

                Map<String, Object> response = new HashMap<>();
                response.put("data", server);
                response.put("message", "Success");

                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "No server found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateServer/{id}")
    public ResponseEntity<Map<String, Object>> updateServer(@PathVariable("id") String id, @RequestBody ServerModel server) {
        try {
            Optional<ServerModel> serverModelObject = serverDAO.findById(id);

            if (serverModelObject.isPresent()) {

                ServerModel serverModel = serverModelObject.get();

                server.setId(id);

                if(server.getName() == null) {
                    server.setName(serverModel.getName());
                }

                if(server.getFramework() == null){
                    server.setFramework(serverModel.getFramework());
                }

                if (server.getLanguage() == null) {
                    server.setLanguage(serverModel.getLanguage());
                }

                server.setCreatedDateTime(serverModel.getCreatedDateTime());
                server.setLastModifiedDateTime(new Date());

                ServerModel updatedServer = serverDAO.save(server);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Success");
                response.put("data", updatedServer);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "No server found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteServer/{id}")
    public ResponseEntity<Map<String, Object>> deleteServer(@PathVariable("id") String id) {
        try {
            Optional<ServerModel> serverModel = serverDAO.findById(id);

            if (serverModel.isPresent()) {
                serverDAO.deleteById(id);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Success");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "No server found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.info(e.getMessage());
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Failure");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
