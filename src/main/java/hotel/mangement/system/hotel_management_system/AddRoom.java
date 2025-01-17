package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.collections.ObservableList;

public class AddRoom extends Application {

    public VBox MainAddRoom(Stage primaryStage) {

        VBox mainVbox = new VBox();
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setPadding(new Insets(25, 25, 25, 25));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);

        HBox titleHBox = new HBox();
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setPadding(new Insets(0, 0, 25, 0));


        Text title = new Text("Add Room");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;");
        titleHBox.getChildren().addAll(title);
        mainVbox.getChildren().addAll(titleHBox);

        Label Room_Number = new Label("Room Number: ");
        Room_Number.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(Room_Number, 0, 0);

        TextField Room_NumberTextField = new TextField();
        Room_NumberTextField.setPromptText("Room Number");
        grid.add(Room_NumberTextField, 1, 0);

        Label Bed_Type = new Label("Bed Type: ");
        Bed_Type.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(Bed_Type, 0, 1);

        ObservableList<String> Bed_TypeList = FXCollections.observableArrayList(
                "Single",
                "Double",
                "King"
        );

        final ComboBox Bed_TypecomboBox = new ComboBox(Bed_TypeList);
        Bed_TypecomboBox.setPromptText("Bed Type");
        grid.add(Bed_TypecomboBox, 1, 1);

        Label Room_Type = new Label("Room Type: ");
        Room_Type.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(Room_Type, 0, 2);

        ObservableList<String> Room_TypeList= FXCollections.observableArrayList(
                "Standard Room",
                "Deluxe Room",
                "Suite"
        );

        final ComboBox Room_TypecomboBox = new ComboBox(Room_TypeList);
        Room_TypecomboBox.setPromptText("Room Type");
        grid.add(Room_TypecomboBox, 1, 2);


        Label Room_Price = new Label("Room Price: ");
        Room_Price.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(Room_Price, 0, 4);

        TextField Room_PriceTextField = new TextField();
        Room_PriceTextField.setPromptText("Room Price per Night");
        grid.add(Room_PriceTextField, 1, 4);

        Button add_roomBtn = new Button("Add Room");
        add_roomBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        add_roomBtn.setStyle("-fx-background-color: #a09fe5;");
        add_roomBtn.setOnMouseEntered(event -> add_roomBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        add_roomBtn.setOnMouseExited(e -> add_roomBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        add_roomBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                try {
                    Database connection = new Database();
                    String Room_NumberText = Room_NumberTextField.getText();
                    String Bed_TypeText = Bed_TypecomboBox.getValue().toString();
                    String Room_TypeText = Room_TypecomboBox.getValue().toString();
                    String Room_PriceText = Room_PriceTextField.getText();


                    int CleaningStatus = 1;

                    String query = "INSERT INTO rooms (`Room Number`, `Bed Type`, `Room Type`, `Price`, `Cleaning Status`) " +
                            "VALUES ('" + Room_NumberText + "', '" + Bed_TypeText + "', '" + Room_TypeText + "', '" + Room_PriceText + "', '" + CleaningStatus + "')";  //inserting the data retrieved from user into the table
                    int rowsaffected = connection.statement.executeUpdate(query);       //To check if data is added successfully, you check how many rows have been affected

                    if (rowsaffected > 0 && !Room_NumberText.isEmpty() && !Bed_TypeText.isEmpty() && !Room_TypeText.isEmpty() && !Room_PriceText.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Room Added Successfully");
                        alert.showAndWait();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Enter all the fields");
                    }

                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });

        Button backBtn = new Button("Back");
        backBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        backBtn.setStyle("-fx-background-color: #a09fe5;");
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Admin Admin = new Admin();
                    Admin.start(primaryStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        HBox buttonbox = new HBox();
        buttonbox.setAlignment(Pos.CENTER);
        buttonbox.setSpacing(10);
        buttonbox.setPadding( new Insets(10, 0, 0, 0));

        buttonbox.getChildren().addAll(add_roomBtn, backBtn);
        grid.setColumnSpan(buttonbox, 2);
        grid.setHalignment(buttonbox, HPos.CENTER); // By default, the button will span to the left of the cell when you span across multiple columns so you need this to make it be in the center
        grid.add(buttonbox, 0, 5);

        mainVbox.getChildren().add(grid);

        return mainVbox;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(MainAddRoom(primaryStage));

        Scene scene = new Scene(borderpane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Room");
        primaryStage.show();

        primaryStage.setX(1100);
        primaryStage.setY(400);

    }
}
