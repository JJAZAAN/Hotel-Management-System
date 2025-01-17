package hotel.mangement.system.hotel_management_system;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserPreviousBookings extends Application {

    public class PreviousBookings {
        private String checkinDate;
        private String checkoutDate;
        private String roomNumber;
        private String bedType;
        private String roomType;
        private String fee;


        public PreviousBookings(String checkinDate, String checkoutDate, String roomNumber, String bedType, String roomType, String fee) {
            this.checkinDate = checkinDate;
            this.checkoutDate = checkoutDate;
            this.roomNumber = roomNumber;
            this.bedType = bedType;
            this.roomType = roomType;
            this.fee = fee;
        }

        public String getCheckinDate() {
            return checkinDate;
        }

        public String getCheckoutDate() {
            return checkoutDate;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public String getBedType() {
            return bedType;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getFee() {
            return fee;
        }
    }

    public VBox Logo() {
        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(200);

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.CENTER);
        imagevBox.getChildren().add(imageView);
        imagevBox.setStyle("-fx-background-color: #a09fe5");

        return imagevBox;
    }

    public VBox MainTable() {

        VBox MainTableVBox = new VBox();
        MainTableVBox.setPadding(new Insets(30));
        MainTableVBox.setAlignment(Pos.CENTER);
        MainTableVBox.setSpacing(10);

        Label bookingsLabel = new Label("Bookings");
        bookingsLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;");

        MainTableVBox.getChildren().add(bookingsLabel);

        ObservableList<PreviousBookings> PreviousBookingsList = FXCollections.observableArrayList();

        try{

            Database connection = new Database();
            String query = "SELECT " +
                    "bookings.`Check In Date` AS checkindate, " +
                    "bookings.`Check Out Date` AS checkoutdate, " +
                    "rooms.`Room Number` AS roomnumber, " +
                    "rooms.`Bed Type` AS bedtype, " +
                    "rooms.`Room Type` AS roomtype, " +
                    "bookings.`Fee` AS fee " +
                    "FROM `hotel_management`.`bookings` AS bookings " +
                    "JOIN `hotel_management`.`rooms` AS rooms " +
                    "ON bookings.`idrooms` = rooms.`idrooms` " +
                    "WHERE bookings.`idusers` = '" + UserSession.getUserid() + "'";

            Statement statement = connection.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String checkInDate = resultSet.getString("checkindate");
                String checkOutDate = resultSet.getString("checkoutdate");
                String roomNumber = resultSet.getString("roomnumber");
                String bedType = resultSet.getString("bedtype");
                String roomType = resultSet.getString("roomtype");
                String fee = resultSet.getString("fee");

                PreviousBookingsList.add(new PreviousBookings(checkInDate, checkOutDate, roomNumber, bedType, roomType, fee));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        TableView table = new TableView();      //This initialises the table control
        table.setEditable(true);

        VBox tableVbox = new VBox();
        tableVbox.getChildren().add(table);

        MainTableVBox.getChildren().add(tableVbox);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the room ID ,ect...) from each Room object.
        TableColumn<PreviousBookings, String> checkinDateCol = new TableColumn("Check In Date");
        TableColumn<PreviousBookings, String> checkoutDateCol = new TableColumn("Check Out Date");
        TableColumn<PreviousBookings, String> roomNumberCol = new TableColumn("Room Number");
        TableColumn<PreviousBookings, String> bedTypeCol = new TableColumn("Bed Type");
        TableColumn<PreviousBookings, String> roomTypeCol = new TableColumn("Room Type");
        TableColumn<PreviousBookings, String> feeCol = new TableColumn("Fee");

        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        checkinDateCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("checkinDate")
        );

        checkoutDateCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("checkoutDate")
        );

        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("roomNumber")
        );
        bedTypeCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("bedType")
        );
        roomTypeCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("roomType")
        );
        feeCol.setCellValueFactory(
                new PropertyValueFactory<PreviousBookings, String>("fee")
        );

        checkinDateCol.setMinWidth(123);
        checkoutDateCol.setMinWidth(123);
        roomNumberCol.setMinWidth(123);
        bedTypeCol.setMinWidth(123);
        roomTypeCol.setMinWidth(123);
        feeCol.setMinWidth(123);


        table.getColumns().addAll(checkinDateCol, checkoutDateCol, roomNumberCol, bedTypeCol, roomTypeCol, feeCol);     //adds all the columns into the table
        table.setItems(PreviousBookingsList);       //set the items inside the table as the observablelist PreviousBookingsList, which contains previous bookings
        MainTableVBox.getChildren().add(table);


        return MainTableVBox;

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


    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Logo());
        borderPane.setCenter(MainTable());
        borderPane.setBottom(BackButton(primaryStage));

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }

}
