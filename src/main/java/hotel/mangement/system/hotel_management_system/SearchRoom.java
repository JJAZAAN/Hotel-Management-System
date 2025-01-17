package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;

import java.sql.ResultSet;

public class SearchRoom extends Application {

    private ObservableList<Room> RoomList = FXCollections.observableArrayList();

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

    public VBox Filter() {

        VBox filtersVBox = new VBox();
        filtersVBox.setPadding( new Insets(10, 20, 10, 20) );
        filtersVBox.setSpacing(10);

        Text title = new Text("Search Rooms");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");
        filtersVBox.getChildren().add(title);

        HBox filtersHBox = new HBox();
        filtersHBox.setSpacing(20);

        HBox bookedHBox = new HBox();
        bookedHBox.setAlignment(Pos.CENTER);
        CheckBox Bookedcb = new CheckBox("Booked");
        Bookedcb.setIndeterminate(false);
        bookedHBox.getChildren().add(Bookedcb);
        filtersHBox.getChildren().add(bookedHBox);

        HBox notbookedHBox = new HBox();
        notbookedHBox.setAlignment(Pos.CENTER);
        CheckBox NotBookedcb = new CheckBox("Not Booked");
        NotBookedcb.setIndeterminate(false);
        notbookedHBox.getChildren().add(NotBookedcb);
        filtersHBox.getChildren().add(notbookedHBox);

        ObservableList<String> Room_TypeList= FXCollections.observableArrayList(
                "Select Room Type",
                "Standard Room",
                "Deluxe Room",
                "Suite"
        );

        final ComboBox Room_TypecomboBox = new ComboBox(Room_TypeList);
        Room_TypecomboBox.setValue("Select Room Type");
        filtersHBox.getChildren().add(Room_TypecomboBox);

        ObservableList<String> Bed_TypeList= FXCollections.observableArrayList(
                "Select Bed Type",
                "Single",
                "Double",
                "King"
        );

        final ComboBox Bed_TypecomboBox = new ComboBox(Bed_TypeList);
        Bed_TypecomboBox.setValue("Select Bed Type");
        filtersHBox.getChildren().add(Bed_TypecomboBox);

        Button searchButton = new Button("Search");
        filtersHBox.getChildren().add(searchButton);
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
            public void handle(ActionEvent actionEvent) {
                RoomList.clear();
                //Searches for a room based on specific conditions, the WHERE 1=1 allows additional conditions to be appended. it the user selects any one of the filters it appends the sql statement for that condition
                StringBuilder querybuilder = new StringBuilder("SELECT `idrooms`, `Room Number`, `Bed Type`, `Room Type`, `Price` FROM `hotel_management`.`rooms`WHERE 1=1");
                String Room_Type = "";
                String Bed_Type = "";

                Room_Type = Room_TypecomboBox.getValue().toString();
                if (!Room_Type.equals("Select Room Type")) {
                    querybuilder.append(" AND `Room Type` = '" + Room_Type + "'");
                }

                Bed_Type = Bed_TypecomboBox.getValue().toString();
                if (!Bed_Type.equals("Select Bed Type")) {
                    querybuilder.append(" AND `Bed Type` = '" + Bed_Type + "'");
                }



                if (Bookedcb.isSelected() && NotBookedcb.isSelected()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Cannot Choose Booked and Not Booked");
                    alert.showAndWait();
                } else if (Bookedcb.isSelected()) {
                    querybuilder.append(" AND `idrooms` IN (SELECT `idrooms` FROM `hotel_management`.`bookings` WHERE `idrooms` IS NOT NULL)");
                } else if (NotBookedcb.isSelected()) {
                    querybuilder.append(" AND `idrooms` NOT IN (SELECT `idrooms` FROM `hotel_management`.`bookings` WHERE `idrooms` IS NOT NULL)");
                }

                String query = querybuilder.toString();


                try {

                    Database connection = new Database();
                    ResultSet resultSet = connection.statement.executeQuery(query);

                    while (resultSet.next()) {
                        String roomId = resultSet.getString("idrooms");
                        String roomNumber = resultSet.getString("Room Number");
                        String bedType = resultSet.getString("Bed Type");
                        String roomTypeResult = resultSet.getString("Room Type");
                        String price = resultSet.getString("Price");

                        RoomList.add(new Room(roomId, roomNumber, bedType, roomTypeResult, price));

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        filtersVBox.getChildren().add(filtersHBox);

        filtersVBox.setStyle("-fx-background-color: #fff;");

        return filtersVBox;
    }

    public VBox MainTable() {

        VBox MaintableVBox = new VBox();
        MaintableVBox.setPadding(new Insets(10, 29, 0, 29));

        try {

            Database connection = new Database();
            //by default shows all rooms
            //Selects rooms from rooms table and makes sure that the rooms are not already booked by using idrooms and checking if the room is free on the user required dates
            String query = "SELECT `idrooms`, `Room Number`, `Bed Type`, `Room Type`, `Price`" +
                    " FROM `hotel_management`.`rooms`";
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


        TableView table = new TableView();      //This initialises the table control
        table.setEditable(true);

        VBox tableVbox = new VBox();
        tableVbox.getChildren().add(table);

        MaintableVBox.getChildren().add(tableVbox);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the room ID ,ect...) from each Room object.
        TableColumn<BookingRoomsReception.Room, String> roomIdCol = new TableColumn("Room ID");
        TableColumn<BookingRoomsReception.Room, String> roomNumberCol = new TableColumn("Room Number");
        TableColumn<BookingRoomsReception.Room, String> bedTypeCol = new TableColumn("Bed Type");
        TableColumn<BookingRoomsReception.Room, String> roomTypeCol = new TableColumn("Room Type");
        TableColumn<BookingRoomsReception.Room, String> priceCol = new TableColumn("Price");

        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        roomIdCol.setCellValueFactory(
                new PropertyValueFactory<BookingRoomsReception.Room, String>("roomId")
        );

        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<BookingRoomsReception.Room, String>("roomNumber")
        );
        bedTypeCol.setCellValueFactory(
                new PropertyValueFactory<BookingRoomsReception.Room, String>("bedType")
        );
        roomTypeCol.setCellValueFactory(
                new PropertyValueFactory<BookingRoomsReception.Room, String>("roomType")
        );
        priceCol.setCellValueFactory(
                new PropertyValueFactory<BookingRoomsReception.Room, String>("Price")
        );

        roomIdCol.setMinWidth(108);
        roomNumberCol.setMinWidth(108);
        bedTypeCol.setMinWidth(108);
        roomTypeCol.setMinWidth(108);
        priceCol.setMinWidth(108);


        table.getColumns().addAll(roomIdCol, roomNumberCol, bedTypeCol, roomTypeCol, priceCol);     //adds all the columns into the table
        table.setItems(RoomList);       //set the items inside the table as the observablelist Roomlist, which contains Room objects
        MaintableVBox.getChildren().add(table);

        MaintableVBox.setStyle("-fx-background-color: #fff");

        return MaintableVBox;

    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Filter());
        borderPane.setCenter(MainTable());

        Scene scene = new Scene(borderPane, 600, 515);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Rooms");
        primaryStage.show();

        primaryStage.setX(1160);
        primaryStage.setY(330);

    }
}
