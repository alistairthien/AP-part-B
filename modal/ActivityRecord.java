package model;

import java.time.LocalDateTime;

public class ActivityRecord {
    private String category;
    private String name;
    private String distanceReps;
    private int duration;
    private int caloriesBurned;
    private boolean isCompleted;
    private LocalDateTime completedDateTime;

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistanceReps() {
        return distanceReps;
    }

    public void setDistanceReps(String distanceReps) {
        this.distanceReps = distanceReps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    @Override
    public String toString() {
        return "ActivityRecord{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", distanceReps='" + distanceReps + '\'' +
                ", duration=" + duration +
                ", caloriesBurned=" + caloriesBurned +
                ", isCompleted=" + isCompleted +
                ", completedDateTime=" + completedDateTime +
                '}';
    }
}