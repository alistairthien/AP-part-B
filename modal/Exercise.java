package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Exercise {
    private String category;
    private String name;
    private String distanceReps;
    private int duration;
    private int caloriesBurned;
    private boolean completed;
    private LocalDateTime completedDateTime;

    // Getter and setter methods for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for distanceReps
    public String getDistanceReps() {
        return distanceReps;
    }

    public void setDistanceReps(String distanceReps) {
        this.distanceReps = distanceReps;
    }

    // Getter and setter methods for duration
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Getter and setter methods for caloriesBurned
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    // Getter and setter methods for completed status
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Getter and setter methods for completedDateTime
    public LocalDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    // Override equals method for comparing two Exercise objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return duration == exercise.duration && caloriesBurned == exercise.caloriesBurned && completed == exercise.completed && Objects.equals(category, exercise.category) && Objects.equals(name, exercise.name) && Objects.equals(distanceReps, exercise.distanceReps) && Objects.equals(completedDateTime, exercise.completedDateTime);
    }

    // Override hashCode method for generating hash code based on exercise properties
    @Override
    public int hashCode() {
        return Objects.hash(category, name, distanceReps, duration, caloriesBurned, completed, completedDateTime);
    }
}