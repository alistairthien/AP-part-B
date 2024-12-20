package Controller;

import DAO.ExerciseDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import model.Exercise;
import SceneManager.SceneManager;

import java.time.format.DateTimeFormatter;

public class ActivityRecordController {
    @FXML
    private ListView<Exercise> ongoingExerciseListView;

    @FXML
    private ListView<Exercise> completedExerciseListView;

    private ObservableList<Exercise> ongoingExerciseList;
    private ObservableList<Exercise> completedExerciseList;
    private ExerciseDAO exerciseDAO;

    @FXML
    public void initialize() {
        exerciseDAO = new ExerciseDAO();
        ongoingExerciseList = FXCollections.observableArrayList();
        completedExerciseList = FXCollections.observableArrayList();

        ongoingExerciseListView.setItems(ongoingExerciseList);
        completedExerciseListView.setItems(completedExerciseList);

        ongoingExerciseListView.setCellFactory(param -> new OngoingExerciseListCell());
        completedExerciseListView.setCellFactory(param -> new CompletedExerciseListCell());

        loadExercises();
    }

    private void loadExercises() {
        ongoingExerciseList.clear();
        completedExerciseList.clear();

        ongoingExerciseList.addAll(exerciseDAO.loadOngoing());
        completedExerciseList.addAll(exerciseDAO.loadCompleted());
    }

    private class OngoingExerciseListCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);
            if (exercise != null && !empty) {
                HBox hBox = new HBox();
                VBox leftBox = new VBox();
                VBox rightBox = new VBox();

                // Apply drop shadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.rgb(0, 0, 0, 0.3));
                dropShadow.setRadius(2);
                dropShadow.setOffsetX(0);
                dropShadow.setOffsetY(1);
                hBox.setEffect(dropShadow);

                // Labels on the left
                Label nameLabel = new Label("Exercise: " + exercise.getName());
                nameLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0)); // Move down

                Label categoryLabel = new Label("Category: " + exercise.getCategory());
                categoryLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(categoryLabel, new Insets(5, 0, 0, 0)); // Move down

                Label distanceRepsLabel = new Label("Distance/Reps: " + exercise.getDistanceReps());
                distanceRepsLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(distanceRepsLabel, new Insets(5, 0, 0, 0)); // Move down

                leftBox.getChildren().addAll(nameLabel, categoryLabel, distanceRepsLabel);
                leftBox.setSpacing(3); // Reduce vertical spacing

                // Duration and Calories on the right
                Label durationLabel = new Label("Duration: " + exercise.getDuration() + " min");
                durationLabel.setStyle("-fx-background-color: rgba(16, 22, 76, 1); -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 5; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px;");

                Label caloriesLabel = new Label("Calories Burned:");
                caloriesLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: white;");

                Label caloriesBurnedValue = new Label(String.valueOf(exercise.getCaloriesBurned()));
                caloriesBurnedValue.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: white;");

                VBox caloriesBurnedBox = new VBox(caloriesLabel, caloriesBurnedValue);
                caloriesBurnedBox.setStyle("-fx-background-color: #e17509; -fx-background-radius: 10; -fx-padding: 5;");
                caloriesBurnedBox.setSpacing(2);
                caloriesBurnedBox.setAlignment(javafx.geometry.Pos.CENTER); // Center the text

                rightBox.getChildren().addAll(durationLabel, caloriesBurnedBox);
                rightBox.setSpacing(5);
                VBox.setMargin(rightBox, new Insets(10, 0, 0, 0)); // Align horizontally

                hBox.getChildren().addAll(leftBox, rightBox);
                HBox.setHgrow(leftBox, javafx.scene.layout.Priority.ALWAYS);
                hBox.setSpacing(20);
                hBox.setPadding(new Insets(10, 30, 10, 25)); // Adjust padding for more inside alignment
                hBox.setStyle("-fx-background-color: white; -fx-background-radius: 40;");

                setGraphic(hBox);
                // Disable selection
                setMouseTransparent(true);
            } else {
                setGraphic(null);
            }
        }
    }

    private class CompletedExerciseListCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);
            if (exercise != null && !empty) {
                VBox vBox = new VBox();
                HBox topBox = new HBox();
                VBox dateTimeBox = new VBox();

                // Apply drop shadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.rgb(0, 0, 0, 0.3));
                dropShadow.setRadius(2);
                dropShadow.setOffsetX(0);
                dropShadow.setOffsetY(2);
                vBox.setEffect(dropShadow);

                // Format the completed date and time
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                String formattedDate = exercise.getCompletedDateTime() != null ? exercise.getCompletedDateTime().format(dateFormatter) : "N/A";
                String formattedTime = exercise.getCompletedDateTime() != null ? exercise.getCompletedDateTime().format(timeFormatter) : "N/A";

                Label completedDateLabel = new Label("Completed Date: " + formattedDate);
                completedDateLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(completedDateLabel, new Insets(0, 0, 0, 0)); // Move up

                Label completedTimeLabel = new Label("Completed Time: " + formattedTime);
                completedTimeLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(completedTimeLabel, new Insets(0, 0, 0, 0)); // Move up

                dateTimeBox.getChildren().addAll(completedDateLabel, completedTimeLabel);
                dateTimeBox.setSpacing(5);

                // Duration at the top right
                Label durationLabel = new Label("Duration: " + exercise.getDuration() + " min");
                durationLabel.setStyle("-fx-background-color: rgba(16, 22, 76, 1); -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 5; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px;");

                // Calories burned in a box below duration, with a different background color
                Label caloriesLabel = new Label("Calories Burned:");
                caloriesLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: white;");

                Label caloriesBurnedValue = new Label(String.valueOf(exercise.getCaloriesBurned()));
                caloriesBurnedValue.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: white;");

                VBox caloriesBurnedBox = new VBox(caloriesLabel, caloriesBurnedValue);
                caloriesBurnedBox.setStyle("-fx-background-color: #e17509; -fx-background-radius: 10; -fx-padding: 5;");
                caloriesBurnedBox.setSpacing(2);
                caloriesBurnedBox.setAlignment(javafx.geometry.Pos.CENTER); // Center the text

                VBox durationAndCaloriesBox = new VBox(durationLabel, caloriesBurnedBox);
                durationAndCaloriesBox.setSpacing(5);

                topBox.getChildren().addAll(dateTimeBox, durationAndCaloriesBox);
                topBox.setSpacing(10);
                HBox.setMargin(durationAndCaloriesBox, new Insets(0, 0, 0, 10));
                topBox.setStyle("-fx-alignment: top-left;");

                Label nameLabel = new Label("Exercise: " + exercise.getName());
                nameLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(nameLabel, new Insets(0, 0, 5, 0)); // Move up

                Label categoryLabel = new Label("Category: " + exercise.getCategory());
                categoryLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(categoryLabel, new Insets(0, 0, 5, 0)); // Move up

                Label distanceRepsLabel = new Label("Distance/Reps: " + exercise.getDistanceReps());
                distanceRepsLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 12px; -fx-text-fill: #191e4d;");
                VBox.setMargin(distanceRepsLabel, new Insets(0, 0, 5, 0)); // Move up

                vBox.getChildren().addAll(topBox, nameLabel, categoryLabel, distanceRepsLabel);
                vBox.setSpacing(5); // Reduce vertical spacing
                vBox.setPadding(new Insets(15, 25, 10, 30)); // Adjust padding for more inside alignment
                vBox.setStyle("-fx-background-color: white; -fx-background-radius: 40;");

                setGraphic(vBox);
                // Disable selection
                setMouseTransparent(true);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    private void handleReturn() {
        SceneManager.loadScene("/View/Exercise.fxml");
    }

    @FXML
    private void redirectToProfile() {
        SceneManager.loadScene("/View/ProfileView.fxml");
    }
}