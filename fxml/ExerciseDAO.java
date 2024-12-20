package DAO;

import model.Exercise;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {
    private static final String ONGOING_FILE_PATH = "src/main/data/ongoing_exercise.txt";
    private static final String COMPLETED_FILE_PATH = "src/main/data/completed_exercise.txt";

    public void saveOngoing(Exercise exercise) {
        saveToFile(exercise, ONGOING_FILE_PATH);
    }

    public void saveCompleted(Exercise exercise) {
        saveToFile(exercise, COMPLETED_FILE_PATH);
    }

    private void saveToFile(Exercise exercise, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(exercise.getCategory() + "," + exercise.getName() + "," +
                    exercise.getDistanceReps() + "," + exercise.getDuration() + "," +
                    exercise.getCaloriesBurned() + "," + exercise.isCompleted() + "," +
                    (exercise.getCompletedDateTime() != null ? exercise.getCompletedDateTime().toString() : "") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> loadOngoing() {
        return loadFromFile(ONGOING_FILE_PATH);
    }

    public List<Exercise> loadCompleted() {
        return loadFromFile(COMPLETED_FILE_PATH);
    }

    private List<Exercise> loadFromFile(String filePath) {
        List<Exercise> exercises = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Exercise exercise = new Exercise();
                exercise.setCategory(parts[0]);
                exercise.setName(parts[1]);
                exercise.setDistanceReps(parts[2]);
                exercise.setDuration(Integer.parseInt(parts[3]));
                exercise.setCaloriesBurned(Integer.parseInt(parts[4]));
                exercise.setCompleted(Boolean.parseBoolean(parts[5]));
                if (parts.length > 6 && !parts[6].isEmpty()) {
                    exercise.setCompletedDateTime(LocalDateTime.parse(parts[6]));
                }
                exercises.add(exercise);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public void deleteOngoing(Exercise exercise) {
        deleteFromFile(exercise, ONGOING_FILE_PATH);
    }

    public void deleteCompleted(Exercise exercise) {
        deleteFromFile(exercise, COMPLETED_FILE_PATH);
    }

    private void deleteFromFile(Exercise exercise, String filePath) {
        List<Exercise> exercises = loadFromFile(filePath);
        exercises.removeIf(ex -> ex.equals(exercise));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Exercise ex : exercises) {
                writer.write(ex.getCategory() + "," + ex.getName() + "," +
                        ex.getDistanceReps() + "," + ex.getDuration() + "," +
                        ex.getCaloriesBurned() + "," + ex.isCompleted() + "," +
                        (ex.getCompletedDateTime() != null ? ex.getCompletedDateTime().toString() : "") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveToCompleted(Exercise exercise) {
        deleteOngoing(exercise);
        saveCompleted(exercise);
    }
}