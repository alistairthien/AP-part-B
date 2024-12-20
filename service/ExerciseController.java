package Controller;

import DAO.ExerciseDAO;
import SceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import model.Exercise;

import java.time.LocalDateTime;

public class ExerciseController {
    @FXML
    private ImageView profileIcon;

    @FXML
    private TextField nameField;

    @FXML
    private TextField distanceRepsField;

    @FXML
    private TextField durationField;

    @FXML
    private ListView<Exercise> exerciseListView;

    @FXML
    private Button cardioButton;

    @FXML
    private Button strengthButton;

    @FXML
    private Button flexibilityButton;

    @FXML
    private Button balanceButton;

    @FXML
    private Label errorLabel;

    private String selectedCategory;
    private ObservableList<Exercise> exerciseList;
    private ExerciseDAO exerciseDAO;

    @FXML
    public void initialize() {
        exerciseDAO = new ExerciseDAO();
        exerciseList = FXCollections.observableArrayList();
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(param -> new ExerciseListCell());
        exerciseListView.setSelectionModel(new NoSelectionModel<>()); // Make list entries not selectable
        loadOngoingExercises();

        // Add focus listeners to the text fields
        addFocusListener(nameField);
        addFocusListener(distanceRepsField);
        addFocusListener(durationField);
    }

    private void addFocusListener(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.setStyle("-fx-background-color: #9ea5e0; -fx-text-fill: white; -fx-prompt-text-fill: transparent; -fx-font-size: 12;");
            } else {
                textField.setStyle("-fx-background-color: rgba(57, 69, 175, 1); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 12;");
            }
        });
    }

    @FXML
    private void handleReturn() {
        SceneManager.loadScene("/View/HomePage.fxml");
    }

    @FXML
    private void redirectToProfile() {
        SceneManager.loadScene("/View/ProfileView.fxml");
    }

    @FXML
    private void selectCategoryCardio() {
        toggleCategorySelection("Cardio", cardioButton);
    }

    @FXML
    private void selectCategoryStrength() {
        toggleCategorySelection("Strength", strengthButton);
    }

    @FXML
    private void selectCategoryFlexibility() {
        toggleCategorySelection("Flexibility", flexibilityButton);
    }

    @FXML
    private void selectCategoryBalance() {
        toggleCategorySelection("Balance", balanceButton);
    }

    private void toggleCategorySelection(String category, Button button) {
        if (category.equals(selectedCategory)) {
            selectedCategory = null;
            button.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
            distanceRepsField.setDisable(false);
        } else {
            selectedCategory = category;
            updateCategoryButtonStyles(button);
            if (category.equals("Flexibility") || category.equals("Balance")) {
                distanceRepsField.setDisable(true);
            } else {
                distanceRepsField.setDisable(false);
            }
        }
    }

    @FXML
    private void addExercise() {
        errorLabel.setText("");

        if (selectedCategory == null) {
            errorLabel.setText("Please select a category.");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        String exerciseName = nameField.getText();
        String distanceReps = distanceRepsField.getText();
        String durationText = durationField.getText();

        if (exerciseName.isEmpty()) {
            errorLabel.setText("Exercise name cannot be empty.");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        if (!distanceRepsField.isDisabled() && distanceReps.isEmpty()) {
            errorLabel.setText("Distance/Reps cannot be empty.");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Duration must be a number.");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        Exercise exercise = new Exercise();
        exercise.setCategory(selectedCategory);
        exercise.setName(exerciseName);
        exercise.setDistanceReps(distanceRepsField.isDisabled() ? "" : distanceReps);
        exercise.setDuration(duration);
        exercise.setCaloriesBurned(calculateCaloriesBurned(selectedCategory, duration, distanceReps));
        exercise.setCompleted(false);

        exerciseDAO.saveOngoing(exercise);
        exerciseList.add(exercise);

        nameField.clear();
        distanceRepsField.clear();
        durationField.clear();
        selectedCategory = null;
        cardioButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        strengthButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        flexibilityButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        balanceButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        distanceRepsField.setDisable(false);
    }

    @FXML
    private void goToActivityRecord() {
        SceneManager.loadScene("/View/ActivityRecord.fxml");
    }

    private void loadOngoingExercises() {
        exerciseList.clear();
        exerciseList.addAll(exerciseDAO.loadOngoing());
    }

    private int calculateCaloriesBurned(String category, int duration, String distanceReps) {
        int caloriesBurned = 0;
        if ("Cardio".equalsIgnoreCase(category)) {
            double distance = Double.parseDouble(distanceReps.split(" ")[0]);
            caloriesBurned = (int) (distance * 50 + duration * 5);
        } else if ("Strength".equalsIgnoreCase(category)) {
            int reps = Integer.parseInt(distanceReps.split(" ")[0]);
            caloriesBurned = reps * 2 + duration * 4;
        } else {
            caloriesBurned = duration * 3;
        }
        return caloriesBurned;
    }

    private void updateCategoryButtonStyles(Button selectedButton) {
        cardioButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        strengthButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        flexibilityButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        balanceButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12;");
        selectedButton.setStyle("-fx-background-color: #8e44ad; -fx-font-size: 12;");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class ExerciseListCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);
            if (exercise != null && !empty) {
                VBox vBox = new VBox();

                // Top row with Category and Duration
                HBox topRow = new HBox();
                Label categoryLabel = new Label("Category: " + exercise.getCategory());
                categoryLabel.setTextFill(Color.WHITE);
                Pane spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                Label durationLabel = new Label("Duration: " + exercise.getDuration() + " min");
                durationLabel.setStyle("-fx-background-color: #d1e5ff; -fx-background-radius: 40; -fx-padding: 5; -fx-text-fill: #191e4d;");
                topRow.getChildren().addAll(categoryLabel, spacer, durationLabel);
                topRow.setPadding(new Insets(0, 10, 0, 10));

                // Middle row with Exercise Name
                Label nameLabel = new Label("Exercise: " + exercise.getName());
                nameLabel.setTextFill(Color.WHITE);
                nameLabel.setPadding(new Insets(0, 10, 0, 10));

                // Bottom row with Distance/Reps if applicable and Calories Burned
                Label distanceRepsLabel = null;
                Label caloriesBurnedLabel = new Label("Calories Burned: " + exercise.getCaloriesBurned());
                caloriesBurnedLabel.setTextFill(Color.WHITE);
                caloriesBurnedLabel.setPadding(new Insets(0, 10, 0, 10));
                if (!exercise.getDistanceReps().isEmpty()) {
                    distanceRepsLabel = new Label("Distance/Reps: " + exercise.getDistanceReps());
                    distanceRepsLabel.setTextFill(Color.WHITE);
                    distanceRepsLabel.setPadding(new Insets(0, 10, 0, 10));
                }

                // Bottom row with Completed checkbox and Delete button
                HBox bottomRow = new HBox(10);
                CheckBox completedCheckBox = new CheckBox("Completed");
                completedCheckBox.setSelected(exercise.isCompleted());
                completedCheckBox.setTextFill(Color.WHITE);
                completedCheckBox.setOnAction(event -> {
                    exercise.setCompleted(completedCheckBox.isSelected());
                    if (completedCheckBox.isSelected()) {
                        exercise.setCompletedDateTime(LocalDateTime.now());
                        exerciseDAO.moveToCompleted(exercise);
                        exerciseList.remove(exercise);
                        showAlert("Exercise Completed", "Exercise '" + exercise.getName() + "' has been marked as completed.");
                    }
                });
                Button deleteButton = new Button("Delete");
                deleteButton.setStyle("-fx-background-color: #c38fd1; -fx-font-size: 12; -fx-text-fill: WHITE;");
                deleteButton.setOnAction(event -> {
                    exerciseList.remove(exercise);
                    exerciseDAO.deleteOngoing(exercise);
                    showAlert("Exercise Deleted", "Exercise '" + exercise.getName() + "' has been deleted.");
                });
                bottomRow.getChildren().addAll(completedCheckBox, deleteButton);
                bottomRow.setPadding(new Insets(0, 10, 0, 10));

                vBox.getChildren().addAll(topRow, nameLabel);
                if (distanceRepsLabel != null) {
                    vBox.getChildren().add(distanceRepsLabel);
                }
                vBox.getChildren().addAll(caloriesBurnedLabel, bottomRow);

                vBox.setSpacing(5);
                vBox.setPadding(new Insets(10, 12, 10, 17));
                vBox.setStyle("-fx-background-color: rgba(93, 46, 125, 1); -fx-background-radius: 40;");

                setGraphic(vBox);
            } else {
                setGraphic(null);
            }
        }
    }

    // Custom NoSelectionModel to make ListView non-selectable
    private static class NoSelectionModel<T> extends MultipleSelectionModel<T> {
        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
            // Do nothing
        }

        @Override
        public void selectAll() {
            // Do nothing
        }

        @Override
        public void selectFirst() {
            // Do nothing
        }

        @Override
        public void selectLast() {
            // Do nothing
        }

        @Override
        public void clearAndSelect(int index) {
            // Do nothing
        }

        @Override
        public void select(int index) {
            // Do nothing
        }

        @Override
        public void select(T obj) {
            // Do nothing
        }

        @Override
        public void clearSelection(int index) {
            // Do nothing
        }

        @Override
        public void clearSelection() {
            // Do nothing
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void selectPrevious() {
            // Do nothing
        }

        @Override
        public void selectNext() {
            // Do nothing
        }
    }
}