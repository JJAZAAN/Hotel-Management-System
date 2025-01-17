package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Statement;

import java.sql.ResultSet;

public class CheckIn extends Application {

    //this is required by checkin button, the others such as roomid are not
        String bookingID = "";
        String roomID = "";
        String roomNumber = "";
        String checkInDate = "";
        String checkOutDate = "";
        String idType = "";
        String idVerified = "";

        String userfoundId = "";        //needed to be able to enter the userid into the bookings table
        String userfoundName = "";      //doesn't have to be here, can be in main
        String userfoundEmail = "";     //
        String userfoundAddress = "";

        private TableView<Booking> table = new TableView<>();       //global so it can be used by all methods
        private ObservableList<Booking> BookingList = FXCollections.observableArrayList();



    public class Booking {
        private String bookingId = "";
        private String roomId = "";
        private String roomNumber = "";
        private String checkInDate = "";
        private String checkOutDate = "";
        private String idType = "";
        private String idVerified = "";

        public Booking(String bookingId, String roomId, String roomNumber, String checkInDate, String checkOutDate, String idType, String idVerified) {
            this.bookingId = bookingId;
            this.roomId = roomId;
            this.roomNumber = roomNumber;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.idType = idType;
            this.idVerified = idVerified;
        }

        public String getBookingId() {
            return bookingId;
        }
        public String getRoomId() {
            return roomId;
        }
        public String getRoomNumber() {
            return roomNumber;
        }
        public String getCheckInDate() {
            return checkInDate;
        }
        public String getCheckOutDate() {
            return checkOutDate;
        }
        public String getIdType() {
            return idType;
        }
        public String getIdVerified() {
            return idVerified;
        }

    }

    public HBox SearchUser() {
        HBox searchUserHBox = new HBox();
        searchUserHBox.setSpacing(10);
        searchUserHBox.setPadding(new Insets(5, 20, 10, 20));

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
        GridPane.setRowSpan(UsercomboBox, 2);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Database connection = new Database();
                    String NameText = NameTextField.getText();
                    String EmailText = EmailTextField.getText();

                    //Searches for the user
                    Statement userStatement = connection.connection.createStatement();
                    String Userquery = "SELECT * FROM users WHERE Name = '" + NameText + "' AND Email = '" + EmailText + "'";
                    ResultSet UserresultSet = userStatement.executeQuery(Userquery);

                    //If the user is found, then it populates the combobox with the user's details and it populates the table
                    if (UserresultSet.next()) {
                        userfoundId = UserresultSet.getString("idusers");
                        userfoundName = UserresultSet.getString("Name");
                        userfoundEmail = UserresultSet.getString("Email");
                        userfoundAddress = UserresultSet.getString("Address");
                        UserList.clear();
                        UserList.add("User ID:" + userfoundId + "\nUser Name: " + userfoundName + "\nEmail: " + userfoundEmail + "\nAddress: " + userfoundAddress);     //adding the items into the observable list

                        UsercomboBox.setPromptText("User Found");
                        UsercomboBox.setItems(UserList);        //populates the combobox with the userlist, the comboxbox will automatically update as the list is observablelist

                        //Selects the user's bookings where the id has not been verified. if the id is verified then the user would have already checked in
                        String Bookingquery = "SELECT * FROM bookings " +
                                "WHERE idusers = '" + userfoundId + "' " +
                                "AND `Id Verified` = 0 " +
                                "ORDER BY `Check In Date` DESC ";
                        Statement bookingStatement = connection.connection.createStatement();
                        ResultSet BookingresultSet = bookingStatement.executeQuery(Bookingquery);

                        while (BookingresultSet.next()) {
                            bookingID = BookingresultSet.getString("idbookings");

                            roomID = BookingresultSet.getString("idrooms");

                            checkInDate = BookingresultSet.getString("Check In Date");

                            checkOutDate = BookingresultSet.getString("Check Out Date");

                            idType = BookingresultSet.getString("Id Type");

                            idVerified = BookingresultSet.getString("Id Verified");

                            //the table contains the roon number and the room number is not in the bookings table, so we have to do another query to retrieve the room number
                            String RetrieveRoomNumberQuery = "SELECT `Room Number` FROM rooms" +
                                    " WHERE idrooms = '" + roomID + "'";
                            Statement roomStatement = connection.connection.createStatement();
                            ResultSet RetrieveRoomNumberResultSet = roomStatement.executeQuery(RetrieveRoomNumberQuery);

                            if (RetrieveRoomNumberResultSet.next()) {
                                roomNumber = RetrieveRoomNumberResultSet.getString("Room Number");
                            } else {
                                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                alert1.setTitle("Error Dialog");
                                alert1.setHeaderText("Could not find booking");
                            }
                            RetrieveRoomNumberResultSet.close(); // Close ResultSet
                            roomStatement.close(); // Close Statement

                            BookingList.add(new Booking(bookingID, roomID, roomNumber, checkInDate, checkOutDate, idType, idVerified));     //adds the data recieved about the booking into the observable list, which will automatically update the table
                        }
                        BookingresultSet.close(); // Close ResultSet
                        bookingStatement.close(); // Close Statement

                        table.setItems(BookingList);        //sets the observable list in the table

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Could not find user");

                        alert.showAndWait();
                    }
                    UserresultSet.close(); // Close ResultSet
                    userStatement.close(); // Close Statement

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        searchUserHBox.setStyle("-fx-background-color: #fff;");

        return searchUserHBox;
    }

    public VBox SearchBookingUser() {

        VBox displayBookingVBox = new VBox();
        displayBookingVBox.setSpacing(10);
        displayBookingVBox.setPadding( new Insets(2, 10, 10, 10) );

        VBox tableVbox = new VBox();
        tableVbox.setPadding( new Insets( 3, 40, 3, 40 ) );
        tableVbox.getChildren().add(table);


        displayBookingVBox.getChildren().addAll(tableVbox);

        // creating the table
        table.setEditable(true);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the room ID ,ect...) from each Room object.
        TableColumn<Booking, String> bookingIdCol = new TableColumn("Booking ID");
        TableColumn<Booking, String> roomIdCol = new TableColumn("Room ID");
        TableColumn<Booking, String> roomNumberCol = new TableColumn("Room Number");
        TableColumn<Booking, String> checkInDateCol = new TableColumn("Check In Date");
        TableColumn<Booking, String> checkOutDateCol = new TableColumn("Check Out Date");
        TableColumn<Booking, String> idTypeCol = new TableColumn("ID Type");
        TableColumn<Booking, String> idVerifiedCol = new TableColumn("ID Verified");

        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        bookingIdCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("bookingId")
        );
        roomIdCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("roomId")
        );

        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("roomNumber")
        );
        checkInDateCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("CheckInDate")
        );
        checkOutDateCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("CheckOutDate")
        );
        idTypeCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("idType")
        );
        idVerifiedCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("idVerified")
        );

        bookingIdCol.setMinWidth(100);
        roomIdCol.setMinWidth(100);
        roomNumberCol.setMinWidth(100);
        checkInDateCol.setMinWidth(100);
        checkOutDateCol.setMinWidth(100);
        idTypeCol.setMinWidth(100);
        idVerifiedCol.setMinWidth(100);

        bookingIdCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        roomIdCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        roomNumberCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        checkInDateCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        checkOutDateCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        idTypeCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        idVerifiedCol.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");



        table.getColumns().addAll(bookingIdCol, roomIdCol, roomNumberCol, checkInDateCol, checkOutDateCol, idTypeCol, idVerifiedCol);     //adds all the columns into the table
        table.setItems(BookingList);       //set the items inside the table as the observablelist Roomlist, which contains Room objects


        //This will contain label and textfields which will display information about the booking that the user has selected
        GridPane bookingSelectedGrid = new GridPane();
        bookingSelectedGrid.setVgap(3);  // Vertical spacing between rows
        bookingSelectedGrid.setHgap(10);  // Horizontal spacing between columns
        bookingSelectedGrid.setPadding(new Insets(10, 10, 10, 10));

        Label bookingSelectedLabel = new Label("Booking Selected");
        bookingSelectedLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");

        Label BookingIdLabel = new Label("Booking ID:");
        TextField BookingIdTextField = new TextField();
        Label RoomIdLabel = new Label("Room ID:");
        TextField RoomIdTextField = new TextField();
        Label RoomNumberLabel = new Label("Room Number:");
        TextField RoomNumberTextField = new TextField();
        Label CheckInDateLabel = new Label("Check In Date:");
        TextField CheckInDateTextField = new TextField();
        Label CheckOutDateLabel = new Label("Check Out Date:");
        TextField CheckOutDateTextField = new TextField();
        Label IDTypeLabel = new Label("ID Type:");
        TextField IDTypeTextField = new TextField();
        Label IDVerifyLabel = new Label("ID Verify:");
        TextField IDVerifyTextField = new TextField();

        bookingSelectedGrid.add(BookingIdLabel, 0, 0);
        bookingSelectedGrid.add(BookingIdTextField, 1, 0);
        bookingSelectedGrid.add(RoomIdLabel, 2, 0);
        bookingSelectedGrid.add(RoomIdTextField, 3, 0);
        bookingSelectedGrid.add(RoomNumberLabel, 4, 0);
        bookingSelectedGrid.add(RoomNumberTextField, 5, 0);
        bookingSelectedGrid.add(CheckInDateLabel, 0, 2);
        bookingSelectedGrid.add(CheckInDateTextField, 1, 2);
        bookingSelectedGrid.add(CheckOutDateLabel, 2, 2);
        bookingSelectedGrid.add(CheckOutDateTextField, 3, 2);
        bookingSelectedGrid.add(IDTypeLabel, 0, 3);
        bookingSelectedGrid.add(IDTypeTextField, 1, 3);
        bookingSelectedGrid.add(IDVerifyLabel, 2, 3);
        bookingSelectedGrid.add(IDVerifyTextField, 3, 3);

        displayBookingVBox.getChildren().addAll(bookingSelectedLabel, bookingSelectedGrid);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //table.getSelectionModel().selectedItemProperty()  returns a property representing the currently selected item in the table
            //.addListener((observable, oldValue, newValue) -> {: adds a listener to the selecteditemproperty, the listener will be triggered whenever the selected item in the table changes
            //addlistener takes 3 parameters: observable(this is the property being observed in this case selecteditemproperty), oldValue(this is the old value(room or row) before the selection change happened, null if no selection made), newValue(the newly selected item)
            if (newValue != null) {
                // Get the selected room object
                Booking BookingSelected = (Booking) newValue;        //(Room) newValue uses casting to treat newValue which is an object as room object. This allows us to access room specific methods

                // Update the labels with the selected room's details and store the values in a variable
                BookingIdTextField.setText(BookingSelected.getBookingId());
                bookingID = BookingSelected.getBookingId();
                RoomIdTextField.setText(BookingSelected.getRoomId());
                roomID = BookingSelected.getRoomId();
                RoomNumberTextField.setText(BookingSelected.getRoomNumber());
                CheckInDateTextField.setText(BookingSelected.getCheckInDate());
                CheckOutDateTextField.setText(BookingSelected.getCheckOutDate());
                IDTypeTextField.setText(BookingSelected.getIdType());
                IDVerifyTextField.setText(BookingSelected.getIdVerified());

            }
        });

        Label idVerifiedLabel = new Label("Id Verified: ");
        idVerifiedLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;");
        ObservableList<String> idVerifiedList= FXCollections.observableArrayList(
                "Yes",
                "No"
        );

        final ComboBox idVerifiedcomboBox = new ComboBox(idVerifiedList);
        idVerifiedcomboBox.setPromptText("Id Verified");

        displayBookingVBox.getChildren().addAll(idVerifiedLabel, idVerifiedcomboBox);

        Button checkInButton = new Button("Check In");
        displayBookingVBox.getChildren().add(checkInButton);
        checkInButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        checkInButton.setOnMouseEntered(event -> checkInButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        checkInButton.setOnMouseExited(e -> checkInButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        checkInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String idVerifiedText = idVerifiedcomboBox.getValue().toString();
                    Database connection = new Database();
                    if (idVerifiedText.equals("Yes")) {
                        String VerifyIDQuery = "UPDATE `hotel_management`.`bookings` SET `Id Verified` = 1 WHERE `idbookings` = '" + bookingID + "'";       //once the user has verified the id, it updates the booking
                        Statement VerifyIDStatement = connection.connection.createStatement();
                        int rowaffected = VerifyIDStatement.executeUpdate(VerifyIDQuery);

                        if (rowaffected > 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText("Booking successfully verified");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Could not find booking" + bookingID);
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("ID must be presented and verified");
                        alert.showAndWait();
                    }

                    String RoomStatusQuery = "UPDATE `hotel_management`.`rooms` SET `Status` = 'Occupied' WHERE `idrooms` = '" + roomID + "'";      //updates the room status to show that it is occupied
                    Statement roomStatusStatement = connection.connection.createStatement();
                    int rowaffected = roomStatusStatement.executeUpdate(RoomStatusQuery);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        displayBookingVBox.setStyle("-fx-background-color: #fff;");

        return displayBookingVBox;

    }

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        GridPane gridUserSearch = new GridPane();
        gridUserSearch.setAlignment(Pos.CENTER);
        gridUserSearch.setHgap(10);
        gridUserSearch.setVgap(10);
        gridUserSearch.setPadding(new Insets(10, 10, 10, 10));



        BorderPane borderPane = new BorderPane();
        borderPane.setTop(SearchUser());
        borderPane.setCenter(SearchBookingUser());


        Scene scene = new Scene(borderPane, 800, 800);
        primaryStage.setTitle("Check In");
        primaryStage.setScene(scene);

        primaryStage.setX(1075);
        primaryStage.setY(230);

        primaryStage.show();
    }
}
