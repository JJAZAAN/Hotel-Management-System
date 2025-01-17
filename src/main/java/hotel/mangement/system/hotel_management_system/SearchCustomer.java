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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class SearchCustomer extends Application{

    private ObservableList<User> UserList = FXCollections.observableArrayList();

    public class User {
        private String userId;
        private String name;
        private String country;
        private String phoneNumber;
        private String address;
        private String email;

        public User(String userId, String name, String country, String phoneNumber, String address, String email) {
            this.userId = userId;
            this.name = name;
            this.country = country;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }
        public String getName() {
            return name;
        }
        public String getCountry() {
            return country;
        }
        public String getPhoneNumber() {
            return phoneNumber;
        }
        public String getAddress() {
            return address;
        }
        public String getEmail() {
            return email;
        }
    }


    public VBox Filter() {

        VBox filtersVBox = new VBox();
        filtersVBox.setPadding( new Insets(10, 20, 10, 20) );
        filtersVBox.setSpacing(10);
        filtersVBox.setAlignment(Pos.CENTER);

        Text title = new Text("Search Customer");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");
        filtersVBox.getChildren().add(title);

        HBox filtersHBox = new HBox();
        filtersHBox.setSpacing(10);
        filtersHBox.setAlignment(Pos.CENTER);

        VBox namevBox = new VBox();
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        namevBox.getChildren().addAll(nameLabel, nameField);
        filtersHBox.getChildren().add(namevBox);

        VBox emailvBox = new VBox();
        Label Email = new Label("Email:");
        TextField emailField = new TextField();
        emailvBox.getChildren().addAll(Email, emailField);
        filtersHBox.getChildren().add(emailvBox);

        VBox addressvBox = new VBox();
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressvBox.getChildren().addAll(addressLabel, addressField);
        filtersHBox.getChildren().add(addressvBox);

        VBox searchVBox = new VBox();
        searchVBox.setAlignment(Pos.CENTER);
        Button searchButton = new Button("Search");
        searchVBox.getChildren().add(searchButton);
        filtersHBox.getChildren().add(searchVBox);
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
                UserList.clear();

                //Searches for a user based on specific conditions, the WHERE 1=1 allows additional conditions to be appended. it the user selects any one of the filters it appends the sql statement for that condition
                StringBuilder queryBuilder = new StringBuilder(("SELECT * FROM `hotel_management`.`users`WHERE 1=1"));

                String nameText = nameField.getText();
                if (!nameText.isEmpty()) {
                    queryBuilder.append(" AND `Name` = '" + nameText + "'");
                }

                String emailText = emailField.getText();
                if (!emailText.isEmpty()) {
                    queryBuilder.append(" AND `Email` = '" + emailText + "'");
                }

                String addressText = addressField.getText();
                if (!addressText.isEmpty()) {
                    queryBuilder.append(" AND `Address` = '" + addressText + "'");
                }

                String query = queryBuilder.toString();

                try {
                    Database connection = new Database();
                    ResultSet resultSet = connection.statement.executeQuery(query);
                    while (resultSet.next()) {
                        String userId = resultSet.getString("idusers");
                        String name = resultSet.getString("Name");
                        String country = resultSet.getString("Country");
                        String phoneNumber = resultSet.getString("Phone Number");
                        String address = resultSet.getString("Address");
                        String email = resultSet.getString("Email");

                        UserList.add(new User(userId, name, country, phoneNumber, address, email));
                    }

                } catch (Exception e) {
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
        MaintableVBox.setPadding(new Insets(10, 29, 20, 29));


        try {
            //by deffault shows all users
            Database connection = new Database();
            String query = "SELECT * FROM users";
            ResultSet resultSet = connection.statement.executeQuery(query);
            while (resultSet.next()) {
                String userId = resultSet.getString("idusers");
                String name = resultSet.getString("Name");
                String country = resultSet.getString("Country");
                String phoneNumber = resultSet.getString("Phone Number");
                String address = resultSet.getString("Address");
                String email = resultSet.getString("Email");

                UserList.add(new User(userId, name, country, phoneNumber, address, email));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        TableView<User> table = new TableView<>();
        table.setEditable(true);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the user ID ,ect...) from each User object.
        TableColumn<User, String> userIdcol = new TableColumn("User ID");
        TableColumn<User, String> nameCol = new TableColumn("Name");
        TableColumn<User, String> countryCol = new TableColumn("Country");
        TableColumn<User, String> phoneNumberCol = new TableColumn("Phone Number");
        TableColumn<User, String> addresseCol = new TableColumn("Address");
        TableColumn<User, String> emailCol = new TableColumn("Email");
        TableColumn<User, String> passwordCol = new TableColumn("Password");
        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        userIdcol.setCellValueFactory(
                new PropertyValueFactory<User, String>("userId")
        );

        nameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("name")
        );
        countryCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("country")
        );
        phoneNumberCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("phoneNumber")
        );
        addresseCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("address")
        );
        emailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );

        userIdcol.setMinWidth(80);
        nameCol.setMinWidth(100);
        countryCol.setMinWidth(100);
        phoneNumberCol.setMinWidth(100);
        addresseCol.setMinWidth(120);
        emailCol.setMinWidth(140);

        table.getColumns().addAll(userIdcol, nameCol, countryCol, phoneNumberCol, addresseCol, emailCol);     //adds all the columns into the table
        table.setItems(UserList);       //set the items inside the table as the observablelist User, which contains Room objects
        MaintableVBox.getChildren().addAll(table);

        MaintableVBox.setStyle("-fx-background-color: #fff;");

        return MaintableVBox;
    }

    @Override
    public void start(Stage primaryStage) {


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Filter());
        borderPane.setCenter(MainTable());


        Scene scene = new Scene(borderPane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Customer");
        primaryStage.show();

        primaryStage.setX(1120);
        primaryStage.setY(350);


    }

}
