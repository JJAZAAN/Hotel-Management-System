package hotel.mangement.system.hotel_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.Statement;

public class CheckOut extends AddEmployee{

    String userfoundId = "";        //needed to be able to enter the userid into the bookings table
    String userfoundName = "";      //doesn't have to be here, can be in main
    String userfoundEmail = "";     //
    String userfoundAddress = "";

    String bookingId = "";
    String roomId = "";
    String userId = "";
    String userName = "";
    String roomNumber = "";
    String checkInDate = "";
    String checkOutDate = "";
    String idType = "";
    String idVerified = "";

    private TableView<Booking> table = new TableView<>();       //needed to be able to enter the userid into the bookings table
    private ObservableList<Booking> BookingList = FXCollections.observableArrayList();      //doesn't have to be here, can be in main

    public class Booking {
        private String bookingId = "";
        private String userId = "";
        private String userName = "";
        private String roomId = "";
        private String roomNumber = "";
        private String checkInDate = "";
        private String checkOutDate = "";
        private String idType = "";
        private String idVerified = "";

        public Booking(String bookingId, String userId, String userName,String roomId, String roomNumber, String checkInDate, String checkOutDate, String idType, String idVerified) {
            this.bookingId = bookingId;
            this.userId = userId;
            this.userName = userName;
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
        public String getUserId() {
            return userId;
        }
        public String getUserName() {
            return userName;
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

                    Statement userStatement = connection.connection.createStatement();
                    //searches for the user
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

                        //retrieves booking details, user information, and room details for verified bookings (Id Verified = 1) where the user's name matches the name that the user added (specified NameText)
                        StringBuilder query = new StringBuilder(
                                "SELECT `bookings`.`idbookings`, `bookings`.`idusers`, `bookings`.`idrooms`, " +
                                        "`bookings`.`Check In Date`, `bookings`.`Check Out Date`, " +
                                        "`bookings`.`Id Type`, `bookings`.`Id Verified`, `users`.`Name`, `rooms`.`Room Number` " +
                                        "FROM `hotel_management`.`bookings` " +
                                        "JOIN `hotel_management`.`users` ON `bookings`.`idusers` = `users`.`idusers` " +
                                        "JOIN `hotel_management`.`rooms` ON `bookings`.`idrooms` = `rooms`.`idrooms` " +
                                        "WHERE `users`.`Name` = '"+ NameText +"' AND `bookings`.`Id Verified` = '" + 1 + "' AND `rooms`.`Status` = '" + "Occupied" + "' ");

                        String tablequery = query.toString();


                        Statement searchStatement = connection.connection.createStatement();
                        ResultSet SearchesultSet = searchStatement.executeQuery(tablequery);

                        while (SearchesultSet.next()) {
                            bookingId = SearchesultSet.getString("idbookings");
                            userId = SearchesultSet.getString("idusers");
                            roomId = SearchesultSet.getString("idrooms");
                            userName = SearchesultSet.getString("Name");
                            roomNumber = SearchesultSet.getString("Room Number");
                            checkInDate = SearchesultSet.getString("check In Date");
                            checkOutDate = SearchesultSet.getString("check Out Date");
                            idType = SearchesultSet.getString("Id Type");
                            idVerified = SearchesultSet.getString("Id Verified");


                            BookingList.add(new Booking(bookingId, userId, userName, roomId, roomNumber, checkInDate, checkOutDate, idType, idVerified));   //adds the data recieved about the booking into the observable list, which will automatically update the table

                        }
                        SearchesultSet.close(); // Close ResultSet
                        searchStatement.close(); // Close Statement

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
        tableVbox.setPadding( new Insets( 3, 30, 3, 30 ) );
        tableVbox.getChildren().add(table);


        displayBookingVBox.getChildren().addAll(tableVbox);

        table.setEditable(true);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the room ID ,ect...) from each Room object.
        TableColumn<Booking, String> bookingIdCol = new TableColumn("Booking ID");
        TableColumn<Booking, String> userIdCol = new TableColumn("User ID");
        TableColumn<Booking, String> userNameCol = new TableColumn("User Name");
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
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("userId")
        );
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<Booking, String>("userName")
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

        table.getColumns().addAll(bookingIdCol, userIdCol, userNameCol, roomIdCol, roomNumberCol, checkInDateCol, checkOutDateCol, idTypeCol, idVerifiedCol);     //adds all the columns into the table
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

        Label bookingIdLabel = new Label("Booking ID");
        TextField bookingIdTextField = new TextField();
        Label userIdLabel = new Label("User ID");
        TextField userIdTextField = new TextField();
        Label userNameLabel = new Label("User Name");
        TextField userNameTextField = new TextField();
        Label roomIdLabel = new Label("Room ID");
        TextField roomIdTextField = new TextField();
        Label roomNumberLabel = new Label("Room Number");
        TextField roomNumberTextField = new TextField();
        Label checkInDateLabel = new Label("Check In Date");
        TextField checkInDateTextField = new TextField();
        Label checkOutDateLabel = new Label("Check Out Date");
        TextField checkOutDateTextField = new TextField();
        Label idTypeLabel = new Label("ID Type");
        TextField idTypeTextField = new TextField();
        Label idVerifiedLabel = new Label("ID Verified");
        TextField idVerifiedTextField = new TextField();

        bookingSelectedGrid.add(bookingIdLabel, 0, 0);
        bookingSelectedGrid.add(bookingIdTextField, 1, 0);
        bookingSelectedGrid.add(userIdLabel, 2, 0);
        bookingSelectedGrid.add(userIdTextField, 3, 0);
        bookingSelectedGrid.add(userNameLabel, 4, 0);
        bookingSelectedGrid.add(userNameTextField, 5, 0);
        bookingSelectedGrid.add(roomIdLabel, 0, 1);
        bookingSelectedGrid.add(roomIdTextField, 1, 1);
        bookingSelectedGrid.add(roomNumberLabel, 2, 1);
        bookingSelectedGrid.add(roomNumberTextField, 3, 1);
        bookingSelectedGrid.add(checkInDateLabel, 0, 2);
        bookingSelectedGrid.add(checkInDateTextField, 1, 2);
        bookingSelectedGrid.add(checkOutDateLabel, 2, 2);
        bookingSelectedGrid.add(checkOutDateTextField, 3, 2);
        bookingSelectedGrid.add(idTypeLabel, 0, 3);
        bookingSelectedGrid.add(idTypeTextField, 1, 3);
        bookingSelectedGrid.add(idVerifiedLabel, 2, 3);
        bookingSelectedGrid.add(idVerifiedTextField, 3, 3);

        displayBookingVBox.getChildren().addAll(bookingSelectedLabel, bookingSelectedGrid);



        //When an item is selected in the table, the labels will display details of the item selected
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //table.getSelectionModel().selectedItemProperty()  returns a property representing the currently selected item in the table
            //.addListener((observable, oldValue, newValue) -> {: adds a listener to the selecteditemproperty, the listener will be triggered whenever the selected item in the table changes
            //addlistener takes 3 parameters: observable(this is the property being observed in this case selecteditemproperty), oldValue(this is the old value(room or row) before the selection change happened, null if no selection made), newValue(the newly selected item)
            if (newValue != null) {
                // Get the selected room object
                Booking BookingSelected = (Booking) newValue;        //(Room) newValue uses casting to treat newValue which is an object as room object. This allows us to access room specific methods

                // Update the labels with the selected room's details and store the values in a variable
                bookingIdTextField.setText(BookingSelected.getBookingId());
                userIdTextField.setText(BookingSelected.getUserId());
                userNameTextField.setText(BookingSelected.getUserName());
                roomIdTextField.setText(BookingSelected.getRoomId());
                roomId = BookingSelected.getRoomId();
                roomNumberTextField.setText(BookingSelected.getRoomNumber());
                checkInDateTextField.setText(BookingSelected.getCheckInDate());
                checkOutDateTextField.setText(BookingSelected.getCheckOutDate());
                idTypeTextField.setText(BookingSelected.getIdType());
                idVerifiedTextField.setText(BookingSelected.getIdVerified());
            }
        });

        Button checkOutButton = new Button("Check Out");
        displayBookingVBox.getChildren().add(checkOutButton);
        checkOutButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        checkOutButton.setOnMouseEntered(event -> checkOutButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        checkOutButton.setOnMouseExited(e -> checkOutButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        checkOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    String selectedRoomId = roomId;

                    Database connection = new Database();
                    //will update the rooms status to Available and the user has now been checked out
                    String query = "UPDATE `hotel_management`.`rooms` " +
                            "SET `Status` = 'Available' " +
                            "WHERE `idrooms` = '" + selectedRoomId + "'";
                    Statement updateStatusStatement = connection.connection.createStatement();
                    int rowaffected = updateStatusStatement.executeUpdate(query);

                    if (rowaffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Check Out Successfull");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Check Out Failed");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                table.refresh();
            }
        });

        displayBookingVBox.setStyle("-fx-background-color: #fff;");

        return displayBookingVBox;

    }


    @Override
    public void start(Stage primaryStage) {



        BorderPane borderPane = new BorderPane();
        borderPane.setTop(SearchUser());        //the tops contains the part that is used to search for the user
        borderPane.setCenter(SearchBookingUser());      //this contains the table and the checkout part


        Scene scene = new Scene(borderPane, 800, 800);
        primaryStage.setTitle("Check Out");
        primaryStage.setScene(scene);

        primaryStage.setX(1075);
        primaryStage.setY(230);

        primaryStage.show();
    }
}
