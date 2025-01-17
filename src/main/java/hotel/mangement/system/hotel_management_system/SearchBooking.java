package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class SearchBooking extends Application {

    private String bookingId = "";
    private String userId = "";
    private String userName = "";
    private String roomId = "";
    private String roomNumber = "";
    private String checkInDate = "";
    private String checkOutDate = "";
    private String idType = "";
    private String idVerified = "";

    private ObservableList<Booking> BookingList = FXCollections.observableArrayList();

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

    public VBox Filter() {

        VBox filtersVBox = new VBox();
        filtersVBox.setPadding( new Insets(10, 20, 0, 20) );
        filtersVBox.setAlignment(Pos.CENTER);
        filtersVBox.setSpacing(15);


        Text title = new Text("Search Bookings");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");
        filtersVBox.getChildren().add(title);

        HBox filtersHBox = new HBox();
        filtersHBox.setSpacing(20);
        filtersHBox.setAlignment(Pos.CENTER);

        VBox namevBox = new VBox();
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        namevBox.getChildren().addAll(nameLabel, nameTextField);
        filtersHBox.getChildren().add(namevBox);

        VBox roomvBox = new VBox();
        Label roomNumberLabel = new Label("Room Number");
        TextField roomNumberTextField = new TextField();
        roomvBox.getChildren().addAll(roomNumberLabel, roomNumberTextField);
        filtersHBox.getChildren().addAll(roomvBox);

        VBox checkInDateVBox = new VBox();
        Label checkInLabel = new Label("Check-In Date:");
        DatePicker checkInDatePicker = new DatePicker();
        checkInDateVBox.getChildren().addAll(checkInLabel, checkInDatePicker);
        filtersHBox.getChildren().addAll(checkInDateVBox);

        VBox checkOutDateVBox = new VBox();
        Label checkOutLabel = new Label("Check-Out Date:");
        DatePicker checkOutDatePicker = new DatePicker();
        checkOutDateVBox.getChildren().addAll(checkOutLabel, checkOutDatePicker);
        filtersHBox.getChildren().addAll(checkOutDateVBox);

        VBox activeVBox = new VBox();
        activeVBox.setAlignment(Pos.CENTER);
        Label ActiveLabel = new Label("Active:");
        CheckBox activecb = new CheckBox("");
        activecb.setIndeterminate(false);
        filtersHBox.getChildren().add(activecb);
        activeVBox.getChildren().addAll(ActiveLabel, activecb);
        filtersHBox.getChildren().addAll(activeVBox);

        VBox searchbtnVBox = new VBox();
        searchbtnVBox.setAlignment(Pos.CENTER);
        Button searchButton = new Button("Search");
        searchbtnVBox.getChildren().add(searchButton);
        filtersHBox.getChildren().add(searchbtnVBox);
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

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BookingList.clear();
                try {
                    String nameText = nameTextField.getText();
                    String roomNumberText = roomNumberTextField.getText();

                    //Could have used where 1=1 here too
                    // a base query that selects booking details and joins related user and room data. The query changes depending on the filters
                    // A condition flag tracks whether a WHERE clause has been added to ensure proper syntax when combining multiple filters (switching between WHERE and AND as needed)
                    StringBuilder queryBuilder = new StringBuilder(
                            "SELECT `bookings`.`idbookings`, `bookings`.`idusers`, `bookings`.`idrooms`, " +
                                    "`bookings`.`Check In Date`, `bookings`.`Check Out Date`, " +
                                    "`bookings`.`Id Type`, `bookings`.`Id Verified`, `users`.`Name`, `rooms`.`Room Number` " +
                                    "FROM `hotel_management`.`bookings` " +
                                    "JOIN `hotel_management`.`users` ON `bookings`.`idusers` = `users`.`idusers` " +
                                    "JOIN `hotel_management`.`rooms` ON `bookings`.`idrooms` = `rooms`.`idrooms`"

                    );

                    boolean condition = false;

                    if (!nameText.isEmpty()) {
                        queryBuilder.append(" WHERE `users`.`Name` = '" + nameText + "'");
                        condition = true;
                    }
                    //doesn't need an if-else statement because it is the first condition being checked. There are no prior conditions that affect how it should behave

                    if (!roomNumberText.isEmpty()) {
                        if (condition) {
                            queryBuilder.append(" AND `rooms`.`Room Number` = '" + roomNumberText + "'");
                        } else {
                            queryBuilder.append(" WHERE `rooms`.`Room Number` = '" + roomNumberText + "'");
                            condition = true;
                        }

                    }

                    if (activecb.isSelected()) {
                        if (condition) {
                            queryBuilder.append(" AND `rooms`.`Status` = 'Occupied'");
                        } else {
                            queryBuilder.append(" WHERE `rooms`.`Status` = 'Occupied'");
                            condition = true;
                        }
                    }

                    if (checkInDatePicker.getValue() != null) {
                        checkInDate = checkInDatePicker.getValue().toString();
                        if (condition) {
                            queryBuilder.append(" AND `bookings`.`Check In Date` = '" + checkInDate + "'");
                        } else {
                            queryBuilder.append(" WHERE `bookings`.`Check In Date` = '" + checkInDate + "'");
                            condition = true;
                        }
                    }

                    if (checkOutDatePicker.getValue() != null) {
                        checkOutDate = checkOutDatePicker.getValue().toString();
                        if (condition) {
                            queryBuilder.append(" AND `bookings`.`Check In Date` = '" + checkOutDate + "'");
                        } else {
                            queryBuilder.append(" WHERE `bookings`.`Check In Date` = '" + checkOutDate + "'");
                            condition = true;
                        }
                    }


                    Database connection = new Database();
                    String query = queryBuilder.toString();
                    Statement searchStatement = connection.connection.createStatement();
                    ResultSet SearchesultSet = searchStatement.executeQuery(query);

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


                        BookingList.add(new Booking(bookingId, userId, userName, roomId, roomNumber, checkInDate, checkOutDate, idType, idVerified));
                        // Resets the DatePickers after search
                        checkInDatePicker.setValue(null);  // Reset check-in date
                        checkOutDatePicker.setValue(null); // Reset check-out date
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        filtersVBox.getChildren().add(filtersHBox);

        filtersVBox.setStyle("-fx-background-color: #fff");

        return filtersVBox;
    }

    public VBox MainTable() {

        VBox mainTableVBox = new VBox();
        mainTableVBox.setPadding(new Insets(0, 38, 10, 38));
        mainTableVBox.setAlignment(Pos.CENTER);

        try {

            // Retrieves today's date dynamically
            LocalDate today = LocalDate.now();
            String todayDate = today.toString(); // Format: YYYY-MM-DD

            Database connection = new Database();
            String Bookingquery = "SELECT * FROM bookings";
            Statement bookingStatement = connection.connection.createStatement();
            ResultSet BookingresultSet = bookingStatement.executeQuery(Bookingquery);


            while (BookingresultSet.next()) {
                bookingId = BookingresultSet.getString("idbookings");
                userId = BookingresultSet.getString("idusers");
                roomId = BookingresultSet.getString("idrooms");
                checkInDate = BookingresultSet.getString("check In Date");
                checkOutDate = BookingresultSet.getString("check Out Date");
                idType = BookingresultSet.getString("Id Type");
                idVerified = BookingresultSet.getString("Id Verified");

                String userNamequery = "SELECT Name FROM users WHERE idusers='" + userId + "'";
                Statement userNameStatement = connection.connection.createStatement();
                ResultSet UserNameresultSet = userNameStatement.executeQuery(userNamequery);
                if (UserNameresultSet.next()) {
                    userName = UserNameresultSet.getString("Name");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Could not find Name");
                }

                String RetrieveRoomNumberQuery = "SELECT `Room Number` FROM rooms" +
                        " WHERE idrooms = '" + roomId + "'";
                Statement roomStatement = connection.connection.createStatement();
                ResultSet RetrieveRoomNumberResultSet = roomStatement.executeQuery(RetrieveRoomNumberQuery);
                if (RetrieveRoomNumberResultSet.next()) {
                    roomNumber = RetrieveRoomNumberResultSet.getString("Room Number");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Could not find Room Number");
                }

                BookingList.add(new Booking(bookingId, userId, userName, roomId, roomNumber, checkInDate, checkOutDate, idType, idVerified));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        TableView table = new TableView();      //This initialises the table control
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

        bookingIdCol.setMinWidth(95);
        userIdCol.setMinWidth(90);
        userNameCol.setMinWidth(100);
        roomIdCol.setMinWidth(90);
        roomNumberCol.setMinWidth(95);
        checkInDateCol.setMinWidth(100);
        checkOutDateCol.setMinWidth(100);
        idTypeCol.setMinWidth(100);
        idVerifiedCol.setMinWidth(100);

        table.getColumns().addAll(bookingIdCol, userIdCol, userNameCol, roomIdCol, roomNumberCol, checkInDateCol, checkOutDateCol, idTypeCol, idVerifiedCol);     //adds all the columns into the table
        table.setItems(BookingList);       //set the items inside the table as the observablelist Roomlist, which contains Room objects
        mainTableVBox.getChildren().add(table);

        mainTableVBox.setStyle("-fx-background-color: #fff;");

        return mainTableVBox;
    }

    @Override
    public void start(Stage primaryStage) {


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Filter());
        borderPane.setCenter(MainTable());

        Scene scene = new Scene(borderPane, 950, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Bookings");
        primaryStage.show();

        primaryStage.setX(1000);
        primaryStage.setY(380);


    }

}
