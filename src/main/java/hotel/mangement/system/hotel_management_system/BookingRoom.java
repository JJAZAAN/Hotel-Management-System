package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;

public class BookingRoom extends Application {
    private String roomType;
    private String bedType;
    private String fee;
    private int userid;
    private java.sql.Date checkInDate;
    private java.sql.Date checkOutDate;

    // Constructor to accept room details
    public BookingRoom(String roomType, String bedType, String fee, int userid, java.sql.Date checkindate, java.sql.Date checkoutdate) {
        this.roomType = roomType;
        this.bedType = bedType;
        this.fee = fee;
        this.userid = userid;
        this.checkInDate = checkindate;
        this.checkOutDate = checkoutdate;
    }


    public BookingRoom() {}

    public VBox Logo() {

        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(175);
        imageView.setFitHeight(150);

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.CENTER);
        imagevBox.getChildren().add(imageView);
        imagevBox.setStyle("-fx-background-color: #a09fe5");

        return imagevBox;
    }

    public VBox MainPanel() {
        VBox main = new VBox();
        main.setSpacing(20);
        main.setAlignment(Pos.TOP_CENTER);
        main.setPadding(new Insets(25, 0, 0, 0));

        //Make the roomType and bedType uppercase
        String roomTypeUpper = roomType.substring(0, 1).toUpperCase() + roomType.substring(1, roomType.length());
        String bedTypeUpper = bedType.substring(0, 1).toUpperCase()+ bedType.substring(1, bedType.length());

        //Displays the roomType and bedType in the title
        Text roomTypeText = new Text(roomTypeUpper + " " + bedTypeUpper + " Size Bed");
        roomTypeText.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 20));
        main.getChildren().add(roomTypeText);

        Text roomPriceText = new Text("£" + fee);
        roomPriceText.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 20));
        main.getChildren().add(roomPriceText);

        HBox idBox = new HBox();
        Label idLabel = new Label("What Form of ID Will you Bring: ");
        idLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));

        ObservableList<String> idList = FXCollections.observableArrayList(
                "Driving License",
                "Passport",
                "Birth Certificate"
        );

        final ComboBox idcomboBox = new ComboBox(idList);
        idcomboBox.setPromptText("ID");

        idBox.getChildren().addAll(idLabel, idcomboBox);
        idBox.setAlignment(Pos.CENTER);
        main.getChildren().add(idBox);

        Button submitButton = new Button("Confirm booking");
        submitButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;" +
                "-fx-background-color: #a09fe5;");
        submitButton.setPrefWidth(180);
        submitButton.setPrefHeight(30);
        submitButton.setOnMouseEntered(event ->submitButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" +
                        "-fx-background-color: #c0bff8;"));

        submitButton.setOnMouseExited(e -> submitButton.setStyle(
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" +
                        "-fx-background-color: #a09fe5;"));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{

                    Database connection = new Database();
                    //retrieving the id for the rooms that the user has chosen so that it can be added into bookings. Limit 1 only shows 1 room, as there could be multiple rooms with the same bedType and roomType
                    String GetRoomIDquery = "SELECT idrooms FROM rooms WHERE `Room Type` = '" + roomType + "' AND `Bed Type` = '" + bedType + "' LIMIT 1";
                    ResultSet RoomIDresultSet = connection.statement.executeQuery(GetRoomIDquery);

                    int idusers = userid;
                    int idrooms = -1;
                    if (RoomIDresultSet.next()) {
                        idrooms = RoomIDresultSet.getInt("idrooms");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Room not Found");

                        alert.showAndWait();
                    }
                    String idtype = idcomboBox.getValue().toString();

                    int idverified = 0;     //id will be verified in checkin, when it will turn to 1

                    //Inserting the data about the room that the user chose into the bookings table
                    String Insertquery = "INSERT INTO bookings (`idusers`, `idrooms`, `Check In Date`, `Check Out Date`, `Id Type`, `Id Verified`, `Fee`)" +
                            "VALUES ('" + idusers + "', '" + idrooms + "', '" + checkInDate + "', '" + checkOutDate + "', '" + idtype + "', '" + idverified + "', '" + fee + "')";
                    int rowsaffected = connection.statement.executeUpdate(Insertquery);       //To check if data is added successfully, you check how many rows have been affected


                    if (rowsaffected > 0 && !idtype.isEmpty() && idverified == 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Booking Successful");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Error in Booking");

                        alert.showAndWait();
                    }



                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        main.getChildren().add(submitButton);
        return main;

    }


    public HBox BackButton(Stage primaryStage) {

        HBox BackButtonHBox = new HBox();
        BackButtonHBox.setAlignment(Pos.CENTER_LEFT);
        BackButtonHBox.setPadding(new Insets(10, 10, 10, 10));

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        backBtn.setMinHeight(15);
        backBtn.setMinWidth(100);
        backBtn.setOnMouseEntered(event ->backBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;" +
                        "-fx-background-color: #c0bff8;"));

        backBtn.setOnMouseExited(event -> backBtn.setStyle(
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;" +
                        "-fx-background-color: #a09fe5;"));

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    RoomDisplay roomDisplay = new RoomDisplay();
                    roomDisplay.start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        BackButtonHBox.getChildren().add(backBtn);
        return BackButtonHBox;

    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Logo());      //Displays the logo on the top of the page
        borderPane.setCenter(MainPanel());      //displays the idverified and confirm booking
        borderPane.setBottom(BackButton(primaryStage));

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Booking Room");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
