package hotel.mangement.system.hotel_management_system;

//import com.mysql.cj.xdevapi.FilterableStatement;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class ChangeRoomDetails extends Application {

    private ObservableList<Room> RoomList = FXCollections.observableArrayList();
    private TableView<Room> table = new TableView<>();

    public String roomid = "";

    public class Room {
        private String roomId;
        private String roomNumber;
        private String bedType;
        private String roomType;
        private String price;
        private String status;

        public Room(String roomId, String roomNumber, String bedType, String roomType, String price, String status) {
            this.roomId = roomId;
            this.roomNumber = roomNumber;
            this.bedType = bedType;
            this.roomType = roomType;
            this.price = price;
            this.status = status;
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

        public String getStatus() {
            return status;
        }
    }

    private void refreshTableData() {
        try {
            table.getItems().clear();
            Database connection = new Database();
            String query = "SELECT `rooms`.`idrooms`, `rooms`.`Room Number`, `rooms`.`Bed Type`, `rooms`.`Room Type`, `rooms`.`Price`, `rooms`.`Status` FROM `hotel_management`.`rooms`";
            ResultSet resultSet = connection.statement.executeQuery(query);

            while (resultSet.next()) {
                String roomId = resultSet.getString("idrooms");
                String roomNumber = resultSet.getString("Room Number");
                String bedType = resultSet.getString("Bed Type");
                String roomType = resultSet.getString("Room Type");
                String price = resultSet.getString("Price");
                String status = resultSet.getString("Status");

                RoomList.add(new Room(roomId, roomNumber, bedType, roomType, price, status));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox Filters() {

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
                StringBuilder querybuilder = new StringBuilder("SELECT `idrooms`, `Room Number`, `Bed Type`, `Room Type`, `Price`, `Status` FROM `hotel_management`.`rooms`WHERE 1=1");
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
                        String status = resultSet.getString("Status");

                        RoomList.add(new Room(roomId, roomNumber, bedType, roomTypeResult, price, status));

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        filtersVBox.getChildren().add(filtersHBox);

        return filtersVBox;

    }

    public VBox MainTable() {

        VBox maintableVBox = new VBox();
        maintableVBox.setPadding(new Insets(10, 25, 0, 25));

        try {

            Database connection = new Database();
            //Selects rooms from rooms table
            String query = "SELECT `rooms`.`idrooms`, `rooms`.`Room Number`, `rooms`.`Bed Type`, `rooms`.`Room Type`, `rooms`.`Price`, `rooms`.`Status` FROM `hotel_management`.`rooms`";
            ResultSet resultSet = connection.statement.executeQuery(query);
            while (resultSet.next()) {
                String roomId = resultSet.getString("idrooms");
                String roomNumber = resultSet.getString("Room Number");
                String bedType = resultSet.getString("Bed Type");
                String roomType = resultSet.getString("Room Type");
                String price = resultSet.getString("Price");
                String status = resultSet.getString("Status");

                RoomList.add(new Room(roomId, roomNumber, bedType, roomType, price, status));
                //creates a new room object and adds it into the observablelist RoomList. Items added need to be of type Room as the observablelist expects Room
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        table.setEditable(true);

        // Adding the columns to the table
        TableColumn<Room, String> roomIdCol = new TableColumn("Room ID");
        TableColumn<Room, String> roomNumberCol = new TableColumn("Room Number");
        TableColumn<Room, String> bedTypeCol = new TableColumn("Bed Type");
        TableColumn<Room, String> roomTypeCol = new TableColumn("Room Type");
        TableColumn<Room, String> priceCol = new TableColumn("Price");
        TableColumn<Room, String> statusCol = new TableColumn("Status");

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
                new PropertyValueFactory<Room, String>("price")
        );
        statusCol.setCellValueFactory(
                new PropertyValueFactory<Room, String>("status")
        );

        roomIdCol.setMinWidth(50);
        roomNumberCol.setMinWidth(80);
        bedTypeCol.setMinWidth(80);
        roomTypeCol.setMinWidth(100);
        priceCol.setMinWidth(80);
        statusCol.setMinWidth(80);

        table.getColumns().addAll(roomIdCol, roomNumberCol, bedTypeCol, roomTypeCol, priceCol, statusCol);
        table.setItems(RoomList);
        maintableVBox.getChildren().addAll(table);

        return maintableVBox;

    }

    public VBox ChangeDetails(TableView<Room> table) {

        VBox changedetailsVBox = new VBox();
        changedetailsVBox.setPadding(new Insets(10));
        changedetailsVBox.setAlignment(Pos.CENTER);

        GridPane selectedRoomGrid = new GridPane();
        selectedRoomGrid.setVgap(3);  // Vertical spacing between rows
        selectedRoomGrid.setHgap(10);  // Horizontal spacing between columns
        selectedRoomGrid.setPadding(new Insets(10, 10, 10, 10));

        Label roomNumberLabel = new Label("Room Number");
        TextField roomNumberTextField = new TextField();
        Label bedTypeLabel = new Label("Bed Type");
        TextField bedTypeTextField = new TextField();
        Label roomTypeLabel = new Label("Room Type");
        TextField roomTypeTextField = new TextField();
        Label priceLabel = new Label("Price");
        TextField priceTextField = new TextField();
        Label statusLabel = new Label("Status");
        TextField statusTextField = new TextField();

        selectedRoomGrid.add(roomNumberLabel, 0, 0);
        selectedRoomGrid.add(roomNumberTextField, 1, 0);
        selectedRoomGrid.add(bedTypeLabel, 2, 0);
        selectedRoomGrid.add(bedTypeTextField, 3, 0);
        selectedRoomGrid.add(roomTypeLabel, 0, 2);
        selectedRoomGrid.add(roomTypeTextField, 1, 2);
        selectedRoomGrid.add(priceLabel, 2, 2);
        selectedRoomGrid.add(priceTextField, 3, 2);
        selectedRoomGrid.add(statusLabel, 0, 3);
        selectedRoomGrid.add(statusTextField, 1, 3);

        changedetailsVBox.getChildren().addAll(selectedRoomGrid);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Room roomSelected = (Room) newValue;

                roomNumberTextField.setText(roomSelected.getRoomNumber());
                roomid = roomSelected.getRoomId();      //roomid variable is updated each time a new room is updated. This will be used to change room details in where clause
                bedTypeTextField.setText(roomSelected.getBedType());
                roomTypeTextField.setText(roomSelected.getRoomType());
                priceTextField.setText(roomSelected.getPrice());
                statusTextField.setText(roomSelected.getStatus());
            }
        });

        Button changedetailsBtn = new Button("Change Details");
        changedetailsBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        changedetailsBtn.setStyle("-fx-background-color: #a09fe5;");
        changedetailsVBox.getChildren().addAll(changedetailsBtn);

        changedetailsBtn.setOnMouseEntered(event -> changedetailsBtn.setStyle(
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        changedetailsBtn.setOnMouseExited(e -> changedetailsBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        //changes room details for the room that is selected
        changedetailsBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {

                try {
                    String roomNumber = roomNumberTextField.getText();
                    String bedType = bedTypeTextField.getText();
                    String roomType = roomTypeTextField.getText();
                    String price = priceTextField.getText();
                    String status = statusTextField.getText();

                    Database connection = new Database();
                    String UpdateRoomDetailsquery = "UPDATE `hotel_management`.`rooms` " +
                            "SET `Room Number` = '" + roomNumber + "', " +
                            "`Bed Type` = '" + bedType + "', " +
                            "`Room Type` = '" + roomType + "', " +
                            "`Price` = '" + price + "', " +
                            "`Status` = '" + status + "' " +
                            "WHERE `idrooms` = '" + roomid + "'";

                    Statement UpdateDetails = connection.connection.createStatement();
                    int rowaffected = UpdateDetails.executeUpdate(UpdateRoomDetailsquery);
                    if (rowaffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Room Details Updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Room Details Not Updated");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshTableData();

            }
        });

        return changedetailsVBox;
    }

    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Filters());
        borderPane.setCenter(MainTable());
        borderPane.setBottom(ChangeDetails(table));

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Room Details");
        primaryStage.show();

        primaryStage.setX(1100);
        primaryStage.setY(400);

    }
}
