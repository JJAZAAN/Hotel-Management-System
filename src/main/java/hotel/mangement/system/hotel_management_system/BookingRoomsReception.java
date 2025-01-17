package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.ResultSet;

public class BookingRoomsReception extends Application {

    private String userfoundId = "";        //needed to be able to enter the userid into the bookings table
    private String userfoundName = "";      //doesn't have to be here, can be in main
    private String userfoundEmail = "";     //
    private String userfoundAddress = "";       //

    private String roomIdSelected = "";     //needed to be able to enter the roomid into the bookings table

    public HBox SearchUser() {

        HBox searchUserHBox = new HBox();
        searchUserHBox.setSpacing(10);
        searchUserHBox.setPadding(new Insets(5, 20, 10, 20));

        //Grid to store all the userSearch infromation
        GridPane searchUserGrid = new GridPane();
        searchUserGrid.setVgap(10);  // Vertical spacing between rows
        searchUserGrid.setHgap(30);  // Horizontal spacing between columns
        searchUserGrid.setPadding(new Insets(20, 20, 20, 20));


        HBox nameHBox = new HBox();
        nameHBox.setSpacing(10);

        Label Name = new Label("Name:");
        Name.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;");
        TextField NameTextField = new TextField();
        NameTextField.setPromptText("Enter Name");
        NameTextField.setMinWidth(300);

        HBox emailHBox = new HBox();
        emailHBox.setSpacing(10);

        Label Email = new Label("Email:");
        Email.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;");
        TextField EmailTextField = new TextField();
        EmailTextField.setPromptText("Email:");
        EmailTextField.setMinWidth(300);

        searchUserGrid.add(Name, 0, 0);  // Column 0, Row 0
        searchUserGrid.add(NameTextField, 1, 0);  // Column 1, Row 0
        searchUserGrid.add(Email, 0, 1);  // Column 0, Row 1
        searchUserGrid.add(EmailTextField, 1, 1);  // Column 1, Row 1

        Button searchButton = new Button("Search");
        searchUserGrid.add(searchButton, 2, 0);  // Add Search button at Column 1, Row 2
        GridPane.setRowSpan(searchButton, 2);  // Makes the button span 2 rows vertically
        searchButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        searchButton.setOnMouseEntered(event -> searchButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        searchButton.setOnMouseExited(e -> searchButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        searchUserHBox.getChildren().addAll(searchUserGrid);


        //observableList is a list that can hold string values. This list notifies any observers(UI components) when the list changes
        ObservableList<String> UserList = FXCollections.observableArrayList();      //This is a factory method that creates an observable list. Obserservablelist aLlows the list to be updated and notifies the combobox
        final ComboBox<String> UsercomboBox = new ComboBox<>(UserList);
        //<String> this tells the combobox what type of objects it should expect. ComboBox<String> is called generic type, the string is a generic type parameter
        // <> this is a type inference, you do not need to write string again, java will infer. It means the combobox will be of type string
        UsercomboBox.setPromptText("User");
        UsercomboBox.setMinWidth(200);

        searchUserGrid.add(UsercomboBox, 3, 0);  // Column 1, Row 0
        GridPane.setRowSpan(UsercomboBox, 2);       //Makes the combobox span 2 rows vertically

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Database connection = new Database();
                    String NameText = NameTextField.getText();
                    String EmailText = EmailTextField.getText();

                    //Find the user from the details provided
                    String query = "SELECT * FROM users WHERE Name = '" + NameText + "' AND Email = '" + EmailText + "'";
                    ResultSet resultSet = connection.statement.executeQuery(query);

                    //if the user is found, then setting global variables with information about user and adding that infromation to the combobox
                    if (resultSet.next()) {
                        userfoundId = resultSet.getString("idusers");
                        userfoundName = resultSet.getString("Name");
                        userfoundEmail = resultSet.getString("Email");
                        userfoundAddress = resultSet.getString("Address");
                        UserList.clear();
                        UserList.add("User: Name: " + userfoundName + "\nEmail: " + userfoundEmail + "\nAddress: " + userfoundAddress);     //adding the items into the observable list

                        UsercomboBox.setPromptText("User Found");
                        UsercomboBox.setItems(UserList);        //populates the combobox with the userlist, the comboxbox will automatically update as the list is observablelist
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Could not find user");

                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        searchUserHBox.setStyle("-fx-background-color: #fff;");

        return searchUserHBox;
    }

    public VBox Booking() {

        VBox bookingTablevBox = new VBox();
        bookingTablevBox.setSpacing(10);
        bookingTablevBox.setPadding( new Insets(2, 10, 10, 10) );

        GridPane BookingDatesGrid = new GridPane();
        BookingDatesGrid.setAlignment(Pos.CENTER);
        BookingDatesGrid.setVgap(10);  // Vertical spacing between rows
        BookingDatesGrid.setHgap(30);  // Horizontal spacing between columns
        BookingDatesGrid.setPadding(new Insets(2, 20, 20, 10));

        //observableList is a list that can hold string values. This list notifies any observers(UI components) when the list changes
        ObservableList<Room> RoomList = FXCollections.observableArrayList();    //This is a factory method that creates an observable list. Observablelist allows the list to be updated and notifies the combobox. The list will be of type Room

        //checkin and check out section
        Label checkInLabel = new Label("Check-In Date:");
        checkInLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;");
        DatePicker checkInDatePicker = new DatePicker();

        Label checkOutLabel = new Label("Check-Out Date:");
        checkOutLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;");
        DatePicker checkOutDatePicker = new DatePicker();

        BookingDatesGrid.add(checkInLabel, 0, 0);
        BookingDatesGrid.add(checkInDatePicker, 1, 0);
        BookingDatesGrid.add(checkOutLabel, 0, 1);
        BookingDatesGrid.add(checkOutDatePicker, 1, 1);

        //to search for rooms from those available dates
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        searchButton.setOnMouseEntered(event -> searchButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        searchButton.setOnMouseExited(e -> searchButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        GridPane.setColumnSpan(searchButton, 2);        //the button will appear beneath the check in date and checkout date in the middle
        GridPane.setHalignment(searchButton, HPos.CENTER);      //makes the button appear in the middle of the column as the button now takes space of two rows but by default will be on the left
        BookingDatesGrid.add(searchButton, 0, 2);

        bookingTablevBox.getChildren().addAll(BookingDatesGrid);


        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RoomList.clear();
                try {
                    java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDatePicker.getValue());
                    java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutDatePicker.getValue());

                    Database connection = new Database();
                    //Selects rooms from rooms table and makes sure that the rooms are not already booked by using idrooms and making sure the status is available and checking if the room is free on the user required dates
                    String query = "SELECT `idrooms`, `Room Number`, `Bed Type`, `Room Type`, `Price`" +
                            " FROM `hotel_management`.`rooms`" +
                            " WHERE `Status` = 'Available' AND `idrooms` NOT IN (" +
                            " SELECT bookings.idrooms" +
                            " FROM bookings WHERE" +
                            " bookings.`Check In Date` <= '"+ checkInDate +"' AND bookings.`Check Out Date` >= '"+ checkOutDate + "')";
                    ResultSet resultSet = connection.statement.executeQuery(query);

                    // resultset checks if there is another row of data in the resultset and if there is, next() returns true and it moves to that row
                    while (resultSet.next()) {      //iterates as long as there is another row and next is true
                        //fetches the value for the current resultset(row) classified by what's in the ""
                        String roomId = resultSet.getString("idrooms");
                        String roomNumber = resultSet.getString("Room Number");
                        String bedType = resultSet.getString("Bed Type");
                        String roomType = resultSet.getString("Room Type");
                        String price = resultSet.getString("Price");

                        RoomList.add(new Room(roomId, roomNumber, bedType, roomType, price));
                        //creates a new room object and adds it into the observablelist RoomList. Items added need to be of type Room as the observablelist expects Room
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        TableView table = new TableView();      //This initialises the table control
        table.setEditable(true);


        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the room ID ,ect...) from each Room object.
        TableColumn<Room, String> roomIdCol = new TableColumn("Room ID");
        TableColumn<Room, String> roomNumberCol = new TableColumn("Room Number");
        TableColumn<Room, String> bedTypeCol = new TableColumn("Bed Type");
        TableColumn<Room, String> roomTypeCol = new TableColumn("Room Type");
        TableColumn<Room, String> priceCol = new TableColumn("Price");

        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        roomIdCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("roomId")
        );

        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("roomNumber")
        );
        bedTypeCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("bedType")
        );
        roomTypeCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("roomType")
        );
        priceCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("Price")
        );

        roomIdCol.setMinWidth(120);
        roomNumberCol.setMinWidth(120);
        bedTypeCol.setMinWidth(120);
        roomTypeCol.setMinWidth(120);
        priceCol.setMinWidth(120);

        table.getColumns().addAll(roomIdCol, roomNumberCol, bedTypeCol, roomTypeCol, priceCol);     //adds all the columns into the table
        table.setItems(RoomList);       //set the items inside the table as the observablelist Roomlist, which contains Room objects

        VBox tableVbox = new VBox();
        tableVbox.setPadding( new Insets( 3, 89, 3, 89 ) );
        tableVbox.getChildren().add(table);

        bookingTablevBox.getChildren().addAll(tableVbox);

        Label roomSelectedLabel = new Label("Selected Room");
        roomSelectedLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");


        //This will display the label and textfield of the room that the user has selected
        GridPane roomSelectedGrid = new GridPane();
        roomSelectedGrid.setVgap(3);  // Vertical spacing between rows
        roomSelectedGrid.setHgap(30);  // Horizontal spacing between columns
        roomSelectedGrid.setPadding(new Insets(10, 10, 10, 10));


        Label roomIdLabel = new Label("Room ID: ");
        TextField roomIdTextField = new TextField();
        Label roomNumberLabel = new Label("Room Number: ");
        TextField roomNumberTextField = new TextField();
        Label bedTypeLabel = new Label("Bed Type: ");
        TextField bedTypeTextField = new TextField();
        Label roomTypeLabel = new Label("Room Type: ");
        TextField roomTypeTextField = new TextField();
        Label priceLabel = new Label("Price: ");
        TextField priceTextField = new TextField();

        roomSelectedGrid.add(roomIdLabel, 0, 0);
        roomSelectedGrid.add(roomIdTextField, 1, 0);
        roomSelectedGrid.add(roomNumberLabel, 2, 0);
        roomSelectedGrid.add(roomNumberTextField, 3, 0);
        roomSelectedGrid.add(bedTypeLabel, 0, 1);
        roomSelectedGrid.add(bedTypeTextField, 1, 1);
        roomSelectedGrid.add(roomTypeLabel, 2, 1);
        roomSelectedGrid.add(roomTypeTextField, 3, 1);
        roomSelectedGrid.add(priceLabel, 0, 2);
        roomSelectedGrid.add(priceTextField, 1, 2);



        bookingTablevBox.getChildren().addAll(roomSelectedLabel, roomSelectedGrid);

        //When an item is selected in the table, the labels will display details of the item selected
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //table.getSelectionModel().selectedItemProperty()  returns a property representing the currently selected item in the table
            //.addListener((observable, oldValue, newValue) -> {: adds a listener to the selecteditemproperty, the listener will be triggered whenever the selected item in the table changes
            //addlistener takes 3 parameters: observable(this is the property being observed in this case selecteditemproperty), oldValue(this is the old value(room or row) before the selection change happened, null if no selection made), newValue(the newly selected item)
            if (newValue != null) {
                // Get the selected room object
                Room roomSelected = (Room) newValue;        //(Room) newValue uses casting to treat newValue which is an object as room object. This allows us to access room specific methods

                // Update the labels with the selected room's details and store the values in a variable
                roomIdTextField.setText(roomSelected.getRoomId());
                roomIdSelected = roomSelected.getRoomId();
                roomNumberTextField.setText(roomSelected.getRoomNumber());
                bedTypeTextField.setText(roomSelected.getBedType());
                roomTypeTextField.setText(roomSelected.getRoomType());
                priceTextField.setText(roomSelected.getPrice());
            }
        });



        VBox idBox = new VBox();
        Label idLabel = new Label("What form of Id will you bring: ");
        idLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");


        ObservableList<String> idList = FXCollections.observableArrayList(
                "Driving License",
                "Passport",
                "Birth Certificate"
        );

        final ComboBox idcomboBox = new ComboBox(idList);
        idcomboBox.setPromptText("ID");
        idBox.getChildren().addAll(idLabel, idcomboBox);
        bookingTablevBox.getChildren().addAll(idBox);

        Button BookingButton = new Button("Booking");
        bookingTablevBox.getChildren().addAll(BookingButton);
        BookingButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        BookingButton.setOnMouseEntered(event -> BookingButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        BookingButton.setOnMouseExited(e -> BookingButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        //Create the booking
        BookingButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent actionEvent) {
            java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDatePicker.getValue());
            java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutDatePicker.getValue());
            String idtype = idcomboBox.getValue().toString();
            int idverified = 0;     //id not verified until checkin

            try {
                Database connection = new Database();
                //Insert the details into the bookings table
                String Insertquery = "INSERT INTO bookings (`idusers`, `idrooms`, `Check In Date`, `Check Out Date`, `Id Type`, `Id Verified`)" +
                        "VALUES ('" + userfoundId + "', '" + roomIdSelected + "', '" + checkInDate + "', '" + checkOutDate + "', '" + idtype + "', '" + idverified + "')";
                int rowsaffected = connection.statement.executeUpdate(Insertquery);       //To check if data is added successfully, you check how many rows have been affected
                if (rowsaffected > 0) {
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



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        });

        bookingTablevBox.setStyle("-fx-background-color: #fff;");

        return bookingTablevBox;
    }

    //class created to represent the table
    public class Room {
        private String roomId;
        private String roomNumber;
        private String bedType;
        private String roomType;
        private String price;

        public Room(String roomId, String roomNumber, String bedType, String roomType, String price) {
            this.roomId = roomId;
            this.roomNumber = roomNumber;
            this.bedType = bedType;
            this.roomType = roomType;
            this.price = price;
        }

        public String getRoomId() {
            return roomId;
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

        public String getPrice() {
            return price;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(SearchUser());
        borderPane.setCenter(Booking());

        Scene scene = new Scene(borderPane, 800, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Room");

        primaryStage.setX(1075);
        primaryStage.setY(200);

        primaryStage.show();
    }
}
