package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import java.util.Random;

import java.sql.ResultSet;

public class AddEmployee extends Application {

    public boolean ValidateEmail(String email) {

        if (email.contains("@")) {
            //splits the email at @
            String[] emailParts = email.split("@");

            // Check if there are exactly two parts (before and after @)
            if (emailParts.length == 2) {
                String firstpartemail = email.split("@")[0];
                String lastpartemail = email.split("@")[1];

                /*
                if email is bob@gmail.com

                firstpartemail = bob
                lastpartemail = gmail.com
                 */

                //makes sure the lastpartemail is not empty
                if (!lastpartemail.isEmpty()) {
                    if (lastpartemail.contains(".")) {
                        String[] domainparts = lastpartemail.split("\\.");
                        //Makes sure that domainparts has 2 parts (gmail and com)
                        if (domainparts.length >= 2 && !domainparts[0].isEmpty() && !domainparts[1].isEmpty()) {
                            return true;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Invalid Email");
                            alert.showAndWait();
                            return false;
                        }

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid Email");
                        alert.showAndWait();
                        return false;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid Email");
                    alert.showAndWait();
                    return false;
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Email");
                alert.showAndWait();
                return false;
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }

    }


    public static String generateRandomPassword() {
        Random random = new Random();

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        String pword = "";
        for (int i =0; i < 8; i++) {
            int index = random.nextInt(AlphaNumericString.length());
            pword += AlphaNumericString.charAt(index);
        }
        return pword;
    }

    public VBox MainAddEmployee(Stage primaryStage) {

        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(25, 25, 25, 25));

        HBox titleHBox = new HBox();
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setPadding(new Insets(0, 0, 25, 0));

        Text title = new Text("Add Employee");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;");
        titleHBox.getChildren().addAll(title);
        mainVBox.getChildren().addAll(titleHBox);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label firstname = new Label("First Name: ");
        firstname.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(firstname, 0, 0);

        TextField firstnameTextField = new TextField();
        firstnameTextField.setPromptText("First Name");
        grid.add(firstnameTextField, 1, 0);

        Label lastname = new Label("Last Name: ");
        lastname.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(lastname, 0, 1);

        TextField lastnameTextField = new TextField();
        lastnameTextField.setPromptText("Last Name");
        grid.add(lastnameTextField, 1, 1);

        Label phonenumber = new Label("Phone Number: ");
        phonenumber.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(phonenumber, 0, 2);

        TextField phonenumberTextField = new TextField();
        phonenumberTextField.setPromptText("Phone Number");
        grid.add(phonenumberTextField, 1, 2);

        Label email = new Label("Email: ");
        email.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(email, 0, 3);

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        grid.add(emailTextField, 1, 3);


        Label position = new Label("Position: ");
        position.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        grid.add(position, 0, 4);

        ObservableList<String> positionComboBox = FXCollections.observableArrayList(
                "Manager",
                "Receptionist",
                "Housekeeper",
                "Chef",
                "Waiter",
                "Cleaner"
        );

        final ComboBox comboBox = new ComboBox(positionComboBox);
        comboBox.setPromptText("Position");
        grid.add(comboBox, 1, 4);

        Button add_employeeBtn = new Button("Add Employee");
        add_employeeBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        add_employeeBtn.setStyle("-fx-background-color: #a09fe5;");
        add_employeeBtn.setOnMouseEntered(event -> add_employeeBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        add_employeeBtn.setOnMouseExited(e -> add_employeeBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        add_employeeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (ValidateEmail(emailTextField.getText())) {
                    try {
                        Database connection = new Database();
                        //retrieving the text inside all the data fields
                        String firstnameText = firstnameTextField.getText();
                        String lastnameText = lastnameTextField.getText();
                        String phonenumberText = phonenumberTextField.getText();
                        String emailText = emailTextField.getText();
                        String positionText = comboBox.getValue().toString();

                        //insert the new employee details into employee table
                        String query = "INSERT INTO employees (`First Name`, `Last Name`, `Phone Number`, `Email`, `Position`)" +
                                "VALUES ('" + firstnameText + "', '" + lastnameText + "', '" + phonenumberText + "', '" + emailText + "', '" + positionText + "')";     //inserting the data retrieved from user into the table
                        int rowsaffected = connection.statement.executeUpdate(query);       //To check if data is added successfully, you check how many rows have been affected

                        if (rowsaffected > 0 && !firstnameText.isEmpty() && !lastnameText.isEmpty() && !phonenumberText.isEmpty() && !emailText.isEmpty() && !positionText.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Employee Added Successfully");
                            alert.showAndWait();

                            //making login information for new user
                            String username = firstnameText.substring(0, 1).toLowerCase() + lastnameText.toLowerCase();
                            String password = generateRandomPassword();
                            int admin = 0;
                            if ((positionText == "Manager") || (positionText == "Boss")) {
                                admin = 1;
                            }

                            //retrieving idemployees to be able to add into the login table as it is a foreign key
                            String query1 = "SELECT idemployees from employees WHERE Email = '" + emailText + "'";
                            ResultSet resultSet = connection.statement.executeQuery(query1);

                            int idemployees = -1;
                            if (resultSet.next()) {
                                idemployees = resultSet.getInt("idemployees");
                            }


                            String query2 = "INSERT INTO login (`idemployees`, `Username`, `Password`, `Admin`) VALUES ('" + idemployees + "' ,'" + username + "', '" + password + "', '" + admin + "')";
                            connection.statement.executeUpdate(query2);

                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("Enter all the fields");

                            alert.showAndWait();
                        }


                    } catch (Exception E) {
                        E.printStackTrace();
                    }
                }
            }
        });


        Button backBtn = new Button("Back");
        backBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 14));
        backBtn.setStyle("-fx-background-color: #a09fe5;");
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Admin Admin = new Admin();
                    Admin.start(primaryStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        HBox buttonbox = new HBox();
        buttonbox.setAlignment(Pos.CENTER);
        buttonbox.setSpacing(10);
        buttonbox.setPadding( new Insets(10, 0, 0, 0));

        buttonbox.getChildren().addAll(add_employeeBtn, backBtn);
        grid.setColumnSpan(buttonbox, 2);
        grid.setHalignment(buttonbox, HPos.CENTER); // By default, the button will span to the left of the cell when you span across multiple columns so you need this to make it be in the center
        grid.add(buttonbox, 0, 5);

        mainVBox.getChildren().add(grid);
        return mainVBox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(MainAddEmployee(primaryStage));

        Scene scene = new Scene(borderpane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Employee");
        primaryStage.show();

        primaryStage.setX(1100);
        primaryStage.setY(400);

    }
}
