package com.rakshith.goalsapp.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "goals")
public class GoalModel {
    @Id
    private String id;
    private String goalName;
    private String description;
    private Date dueDateTime;

    @CreatedDate
    private Date createdDateTime;
    @LastModifiedDate
    private Date lastModifiedDateTime;

    public GoalModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(Date dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }


    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(Date lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }


    @Override
    public String toString() {
        return "GoalModel{" +
                "id='" + id + '\'' +
                ", goalName='" + goalName + '\'' +
                ", description='" + description + '\'' +
                ", dueDateTime=" + dueDateTime +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDateTime=" + lastModifiedDateTime +
                '}';
    }
}
