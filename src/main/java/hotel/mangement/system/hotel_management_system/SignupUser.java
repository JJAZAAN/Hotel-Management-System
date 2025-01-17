package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class SignupUser extends Application {

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

    public boolean NotEmptyFields(String name, String country, String phonenumber, String Address, String email, String password) {

        if (!name.isEmpty() && !country.isEmpty() && !phonenumber.isEmpty() && !Address.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter all fields");
            alert.showAndWait();
            return false;
        }

    }

    public VBox Logo() {

        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(125);

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.CENTER);
        imagevBox.getChildren().add(imageView);
        imagevBox.setStyle("-fx-background-color: #a09fe5");

        return imagevBox;
    }

    public GridPane MainPane(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label Name = new Label("Name:");
        Name.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(Name, 0, 0);

        TextField NameTextField = new TextField();
        NameTextField.setPromptText("Name");
        grid.add(NameTextField, 1, 0);

        Label Country = new Label("Home Country:");
        Country.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(Country, 0, 1);

        TextField CountryTextField = new TextField();
        CountryTextField.setPromptText("Country");
        grid.add(CountryTextField, 1, 1);

        Label PhoneNumber = new Label("Phone Number:");
        PhoneNumber.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(PhoneNumber, 0, 2);

        TextField PhoneNumberTextField = new TextField();
        PhoneNumberTextField.setPromptText("Country");
        grid.add(PhoneNumberTextField, 1, 2);

        Label Address = new Label("Address:");
        Address.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(Address, 0, 3);

        TextArea AddressTextArea = new TextArea();
        AddressTextArea.setPromptText("Country");
        grid.add(AddressTextArea, 1, 3);

        Label Email = new Label("Email:");
        Email.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(Email, 0, 4);

        TextField EmailTextField = new TextField();
        EmailTextField.setPromptText("Email");
        grid.add(EmailTextField, 1, 4);

        Label Password = new Label("Password:");
        Password.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        grid.add(Password, 0, 5);

        TextField PasswordTextField = new TextField();
        PasswordTextField.setPromptText("Password");
        grid.add(PasswordTextField, 1, 5);

        HBox buttonshBox = new HBox();
        buttonshBox.setSpacing(10);
        buttonshBox.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(buttonshBox, 1, 6);

        Button SignUpButton = new Button("Sign Up");
        SignUpButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        SignUpButton.setStyle("-fx-background-color: #a09fe5;");

        SignUpButton.setOnMouseEntered(event ->SignUpButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-size: 16;"));

        SignUpButton.setOnMouseExited(e -> SignUpButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-size: 16;"));

        SignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //Makes sure that the fields are not empty
                if (NotEmptyFields(NameTextField.getText(), CountryTextField.getText(), PhoneNumberTextField.getText(), AddressTextArea.getText(), EmailTextField.getText(), PasswordTextField.getText())) {
                    //Makes sure the email is actaully an email
                    if (ValidateEmail(EmailTextField.getText())) {

                        try {
                            Database connection = new Database();

                            String NameText = NameTextField.getText();
                            String CountryText = CountryTextField.getText();
                            String PhoneNumberText = PhoneNumberTextField.getText();
                            String AddressText = AddressTextArea.getText();
                            String EmailText = EmailTextField.getText();
                            String PasswordText = PasswordTextField.getText();

                            String query = "INSERT INTO users (`Name`, `Country`, `Phone Number`, `Address`, `Email`, `Password`)" +
                                    "VALUES ('" + NameText + "', '" + CountryText + "', '" + PhoneNumberText + "', '" + AddressText + "', '" + EmailText + "', '" + PasswordText + "')";
                            int rowsaffected = connection.statement.executeUpdate(query);       //To check if data is added successfully, you check how many rows have been affected

                            if (rowsaffected > 0 && !NameText.isEmpty() && !CountryText.isEmpty() && !PhoneNumberText.isEmpty() && !AddressText.isEmpty() && !EmailText.isEmpty() && !PasswordText.isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("Account Created");
                                alert.showAndWait();
                                UserLogin login = new UserLogin();
                                login.start(primaryStage);

                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Enter all the fields");

                            }

                        } catch (Exception E) {
                            E.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonshBox.getChildren().add(SignUpButton);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #a09fe5;");
        backBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        backBtn.setOnMouseEntered(event ->backBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-size: 16;"));

        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-size: 16;"));


        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    UserLogin userLogin = new UserLogin();
                    userLogin.start(primaryStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
        buttonshBox.getChildren().add(backBtn);

        return grid;

    }

    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Logo());
        borderPane.setCenter(MainPane(primaryStage));

        Scene scene = new Scene(borderPane, 700, 600);
        primaryStage.setScene(scene);

        primaryStage.setTitle("SignUp");
        primaryStage.setX(1120);        //sets the position of the page in the screen
        primaryStage.setY(200);

        primaryStage.show();
    }
}
