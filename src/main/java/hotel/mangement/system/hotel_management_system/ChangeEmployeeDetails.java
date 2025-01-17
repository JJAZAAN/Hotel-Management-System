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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.Statement;

public class ChangeEmployeeDetails extends Application {

    private ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();
    private TableView<Employee> table = new TableView<>();

    public String employeeid = "";

    public class Employee {
        private String employeeId;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String position;

        public Employee(String employeeId, String firstName, String lastName, String phoneNumber, String email, String position) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.position = position;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public String getPosition() {
            return position;
        }
    }

    private void refreshTableData() {
        try {
            table.getItems().clear();
            Database connection = new Database();
            //select all employees
            String query = "SELECT * FROM employees";
            ResultSet resultSet = connection.statement.executeQuery(query);

            while (resultSet.next()) {
                String employeeId = resultSet.getString("idemployees");
                String firstName = resultSet.getString("First Name");
                String lastName = resultSet.getString("Last Name");
                String phoneNumber = resultSet.getString("Phone Number");
                String email = resultSet.getString("Email");
                String position = resultSet.getString("Position");

                EmployeeList.add(new Employee(employeeId, firstName, lastName, phoneNumber, email, position));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public VBox Filters() {

        VBox filtersVBox = new VBox();
        filtersVBox.setPadding(new Insets(10, 20, 10, 20));
        filtersVBox.setSpacing(10);

        Text title = new Text("Search Employees");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16;");
        filtersVBox.getChildren().add(title);

        HBox filtersHBox = new HBox();
        filtersHBox.setSpacing(20);

        ObservableList<String> positionList = FXCollections.observableArrayList(
                "Select Position",
                "Manager",
                "Receptionist",
                "Housekeeper",
                "Chef",
                "Waiter",
                "Cleaner"
        );

        final ComboBox positionComboBox = new ComboBox(positionList);
        positionComboBox.setValue("Select Position");
        filtersHBox.getChildren().add(positionComboBox);

        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("Enter First Name");
        filtersHBox.getChildren().add(firstNameTextField);

        Button searchButton = new Button("Search");
        filtersHBox.getChildren().add(searchButton);
        searchButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        searchButton.setOnMouseEntered(event -> searchButton.setStyle(
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
                EmployeeList.clear();

                //Searches for a employee based on specific conditions, the WHERE 1=1 allows additional conditions to be appended. it the user selects any one of the filters it appends the sql statement for that condition
                StringBuilder queryBuilder = new StringBuilder("SELECT idemployees, `First Name`, `Last Name`, `Phone Number`, `Email`, `Position` FROM hotel_management.employees WHERE 1=1");

                String position = positionComboBox.getValue().toString();
                if (!position.equals("Select Position")) {
                    queryBuilder.append(" AND `Position` = '" + position + "'");
                }

                String firstName = firstNameTextField.getText();
                if (firstName != null && !firstName.isEmpty()) {
                    queryBuilder.append(" AND `First Name` LIKE '%" + firstName + "%'");
                }

                String query = queryBuilder.toString();

                try {
                    Database connection = new Database();
                    ResultSet resultSet = connection.statement.executeQuery(query);

                    while (resultSet.next()) {
                        String employeeId = resultSet.getString("idemployees");
                        String firstNameResult = resultSet.getString("First Name");
                        String lastName = resultSet.getString("Last Name");
                        String phoneNumber = resultSet.getString("Phone Number");
                        String email = resultSet.getString("Email");
                        String positionResult = resultSet.getString("Position");

                        EmployeeList.add(new Employee(employeeId, firstNameResult, lastName, phoneNumber, email, positionResult));
                        //creates a new employee object and adds it into the observablelist employeeList. Items added need to be of type Room as the observablelist expects Room
                    }

                } catch (Exception e) {
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
        table.refresh();

        try {

            Database connection = new Database();
            //selects all employees and displays when page is displayed
            String query = "SELECT * FROM employees";
            ResultSet resultSet = connection.statement.executeQuery(query);
            while (resultSet.next()) {
                String employeeId = resultSet.getString("idemployees");
                String firstName = resultSet.getString("First Name");
                String lastName = resultSet.getString("Last Name");
                String phoneNumber = resultSet.getString("Phone Number");
                String email = resultSet.getString("Email");
                String position = resultSet.getString("Position");

                EmployeeList.add(new Employee(employeeId, firstName, lastName, phoneNumber, email, position));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        table.setEditable(true);

        //JavaFX's TableView and TableColumn are designed to be generic so they can handle various data types and objects.
        //Adding the columns to the table
        // TableColumn<Room, String> means that this column will display String data (the user ID ,ect...) from each User object.
        TableColumn<Employee, String> employeeIdcol = new TableColumn("Employee ID");
        TableColumn<Employee, String> firstNamecol = new TableColumn("First Name");
        TableColumn<Employee, String> lastNamecol = new TableColumn("Last Name");
        TableColumn<Employee, String> phoneNumberCol = new TableColumn("Phone Number");
        TableColumn<Employee, String> emailcol = new TableColumn("Email");
        TableColumn<Employee, String> positioncol = new TableColumn("Position");
        //When the table is populated with room objects, each row corresponds to a room object. The roomidcol will display the value of roomid property from each room object
        //the propertyvaluefactory uses reflection to access the getter method of the roomid property to display the value in the roomid column
        //If you remove setCellValueFactory, the roomIdCol column would not know how to retrieve or display the data for the room ID in the TableView.
        //Is used to define how the data will be displayed in each cell from room objects
        employeeIdcol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("employeeId")
        );

        firstNamecol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("firstName")
        );
        lastNamecol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("lastName")
        );
        phoneNumberCol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("phoneNumber")
        );
        emailcol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("email")
        );
        positioncol.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("position")
        );

        employeeIdcol.setMinWidth(50);
        firstNamecol.setMinWidth(80);
        lastNamecol.setMinWidth(80);
        phoneNumberCol.setMinWidth(100);
        emailcol.setMinWidth(110);
        positioncol.setMinWidth(90);


        table.getColumns().addAll(employeeIdcol, firstNamecol, lastNamecol, phoneNumberCol, emailcol, positioncol);    //adds all the columns into the table
        table.setItems(EmployeeList);       //set the items inside the table as the observablelist User, which contains Room objects
        maintableVBox.getChildren().addAll(table);


        return maintableVBox;

    }


    //changes the detail of the employee
    public VBox ChangeDetails(TableView<Employee> table) {

        VBox changedetailsVBox = new VBox();
        changedetailsVBox.setPadding(new Insets(10));
        changedetailsVBox.setAlignment(Pos.CENTER);

        GridPane selectedEmployeeGrid = new GridPane();
        selectedEmployeeGrid.setVgap(3);  // Vertical spacing between rows
        selectedEmployeeGrid.setHgap(10);  // Horizontal spacing between columns
        selectedEmployeeGrid.setPadding(new Insets(10, 10, 10, 10));


        Label employeeIdLabel = new Label("Employee ID");
        TextField employeeIdTextField = new TextField();
        Label firstNameLabel = new Label("First Name");
        TextField firstNameTextField = new TextField();
        Label lastNameLabel = new Label("Last Name");
        TextField lastNameTextField = new TextField();
        Label phoneNumberLabel = new Label("Phone Number");
        TextField phoneNumberTextField = new TextField();
        Label emailLabel = new Label("Email");
        TextField emailTextField = new TextField();
//        Label positionLabel = new Label("Position");
//        TextField positionTextField = new TextField();
        Label positionLabel = new Label("Position");
        ObservableList<String> positionList = FXCollections.observableArrayList(
                "Select Position",
                "Manager",
                "Receptionist",
                "Housekeeper",
                "Chef",
                "Waiter",
                "Cleaner"
        );

        final ComboBox positionComboBox = new ComboBox(positionList);
        positionComboBox.setValue("Select Position");


        selectedEmployeeGrid.add(employeeIdLabel, 0, 0);
        selectedEmployeeGrid.add(employeeIdTextField, 1, 0);
        selectedEmployeeGrid.add(firstNameLabel, 2, 0);
        selectedEmployeeGrid.add(firstNameTextField, 3, 0);
        selectedEmployeeGrid.add(lastNameLabel, 0, 2);
        selectedEmployeeGrid.add(lastNameTextField, 1, 2);
        selectedEmployeeGrid.add(phoneNumberLabel, 2, 2);
        selectedEmployeeGrid.add(phoneNumberTextField, 3, 2);
        selectedEmployeeGrid.add(emailLabel, 0, 3);
        selectedEmployeeGrid.add(emailTextField, 1, 3);
        selectedEmployeeGrid.add(positionLabel, 2, 3);
        selectedEmployeeGrid.add(positionComboBox, 3, 3);

        changedetailsVBox.getChildren().addAll(selectedEmployeeGrid);


        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //table.getSelectionModel().selectedItemProperty()  returns a property representing the currently selected item in the table
            //.addListener((observable, oldValue, newValue) -> {: adds a listener to the selecteditemproperty, the listener will be triggered whenever the selected item in the table changes
            //addlistener takes 3 parameters: observable(this is the property being observed in this case selecteditemproperty), oldValue(this is the old value(room or row) before the selection change happened, null if no selection made), newValue(the newly selected item)
            if (newValue != null) {
                // Get the selected room object
                Employee EmployeeSelected = (Employee) newValue;        //(Room) newValue uses casting to treat newValue which is an object as room object. This allows us to access room specific methods

                // Update the labels with the selected room's details and store the values in a variable
                employeeIdTextField.setText(EmployeeSelected.getEmployeeId());
                employeeid = EmployeeSelected.getEmployeeId();
                firstNameTextField.setText(EmployeeSelected.getFirstName());
                lastNameTextField.setText(EmployeeSelected.getLastName());
                phoneNumberTextField.setText(EmployeeSelected.getPhoneNumber());
                emailTextField.setText(EmployeeSelected.getEmail());
                positionComboBox.setValue(EmployeeSelected.getPosition());
            }
        });

        Button changedetailsBtn = new Button("Change Details");
        changedetailsBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        changedetailsBtn.setStyle("-fx-background-color: #a09fe5;");
        changedetailsVBox.getChildren().addAll(changedetailsBtn);

        changedetailsBtn.setOnMouseEntered(event -> changedetailsBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        changedetailsBtn.setOnMouseExited(e -> changedetailsBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        changedetailsBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {

                try {
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String phoneNumber = phoneNumberTextField.getText();
                    String email = emailTextField.getText();
                    String position = positionComboBox.getValue().toString();


                    Database connection = new Database();
                    String UpdateEmployeeDetailsquery = "UPDATE `hotel_management`.`employees` " +
                            "SET `First Name` = '" + firstName + "', " +
                            "`Last Name` = '" + lastName + "', " +
                            "`Phone Number` = '" + phoneNumber + "', " +
                            "`Email` = '" + email + "', " +
                            "`Position` = '" + position + "' " +
                            "WHERE `idemployees` = '" + employeeid + "'";

                    Statement UpdateDetails = connection.connection.createStatement();
                    int rowaffected = UpdateDetails.executeUpdate(UpdateEmployeeDetailsquery);
                    if (rowaffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee Details Updated");
                        alert.showAndWait();


                        if (position == "Manager" || position == "Boss") {
                            String updateAdminquery = "UPDATE `hotel_management`.`login` SET `Admin` = 1 WHERE `idemployees` = '" + employeeid + "'";
                            Statement UpdateAdmin = connection.connection.createStatement();
                            UpdateAdmin.executeUpdate(updateAdminquery);
                        } else {
                            String updateAdminquery = "UPDATE `hotel_management`.`login` SET `Admin` = 0 WHERE `idemployees` = '" + employeeid + "'";
                            Statement UpdateAdmin = connection.connection.createStatement();
                            UpdateAdmin.executeUpdate(updateAdminquery);
                        }


                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Employee Details Not Updated");
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
        primaryStage.setTitle("Change Employee Details");
        primaryStage.show();

        primaryStage.setX(1100);
        primaryStage.setY(400);

    }
}
