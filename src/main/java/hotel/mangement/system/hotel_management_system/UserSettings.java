package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserSettings extends Application {

    String password = "";
    private boolean ispasswordvisible = false;

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




    public VBox Logo() {
        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(200);

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.CENTER);
        imagevBox.getChildren().add(imageView);
        imagevBox.setStyle("-fx-background-color: #a09fe5");

        return imagevBox;
    }


    public VBox MainSetting() {

        VBox MainSettingVBox = new VBox();
        MainSettingVBox.setAlignment(Pos.CENTER);
        MainSettingVBox.setPadding(new Insets(10));
        MainSettingVBox.setSpacing(15);

        Text title = new Text("Settings");
        title.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;");
        MainSettingVBox.getChildren().add(title);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);

        //.setVisible(false) means it won't be showed

        //Name
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentNameLabel = new Label("Current Name");
        Button changeNameButton = new Button("Change");
        TextField newNameTextField = new TextField();
        Button saveNameButton = new Button("Save");

        newNameTextField.setVisible(false);
        saveNameButton.setVisible(false);

        changeNameButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changeNameButton.setOnMouseEntered(event -> changeNameButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeNameButton.setOnMouseExited(e -> changeNameButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        saveNameButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        saveNameButton.setOnMouseEntered(event -> saveNameButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        saveNameButton.setOnMouseExited(e -> saveNameButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeNameButton.setOnAction(event -> {
            newNameTextField.setVisible(true);
            saveNameButton.setVisible(true);
            changeNameButton.setVisible(false);
        });


        saveNameButton.setOnAction(event -> {
            newNameTextField.setVisible(false);
            changeNameButton.setVisible(true);
            saveNameButton.setVisible(false);


            if (!newNameTextField.getText().isEmpty()) {
                try {


                    Database connection = new Database();
                    String UpdateNamequery = "UPDATE users SET name = '" + newNameTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                    Statement updateNameStatement = connection.connection.createStatement();
                    int rowaffected = updateNameStatement.executeUpdate(UpdateNamequery);

                    if (rowaffected > 0) {
                        currentNameLabel.setText(newNameTextField.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Name has been updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Name cannot be empty");
                alert.showAndWait();
            }

        });

        grid.add(nameLabel, 0, 0);
        grid.add(currentNameLabel, 1, 0);
        grid.add(changeNameButton, 2, 0);
        grid.add(newNameTextField, 3, 0);
        grid.add(saveNameButton, 4, 0);



        //Country
        Label countryLabel = new Label("Country:");
        countryLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentCountryLabel = new Label("Current Country");
        Button changeCountryButton = new Button("Change");
        TextField newCountryTextField = new TextField();
        Button saveCountryButton = new Button("Save");

        newCountryTextField.setVisible(false);
        saveCountryButton.setVisible(false);

        changeCountryButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changeCountryButton.setOnMouseEntered(event -> changeCountryButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeCountryButton.setOnMouseExited(e -> changeCountryButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        saveCountryButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        saveCountryButton.setOnMouseEntered(event -> saveCountryButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        saveCountryButton.setOnMouseExited(e -> saveCountryButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeCountryButton.setOnAction(event -> {
            newCountryTextField.setVisible(true);
            saveCountryButton.setVisible(true);
            changeCountryButton.setVisible(false);
        });


        saveCountryButton.setOnAction(event -> {
            newCountryTextField.setVisible(false);
            changeCountryButton.setVisible(true);
            saveCountryButton.setVisible(false);

            if (!newCountryTextField.getText().isEmpty()) {
                try {
                    Database connection = new Database();
                    String UpdateCountryquery = "UPDATE users SET Country = '" + newCountryTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                    Statement updateCountryStatement = connection.connection.createStatement();
                    int rowaffected = updateCountryStatement.executeUpdate(UpdateCountryquery);

                    if (rowaffected > 0) {
                        currentCountryLabel.setText(newCountryTextField.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Country has been updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Country cannot be empty");
                alert.showAndWait();
        }

        });


        grid.add(countryLabel, 0, 1);
        grid.add(currentCountryLabel, 1, 1);
        grid.add(changeCountryButton, 2, 1);
        grid.add(newCountryTextField, 3, 1);
        grid.add(saveCountryButton, 4, 1);



        //Phone Number
        Label phonenumberLabel = new Label("Phone Number:");
        phonenumberLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentPhoneNumberLabel = new Label("Current PhoneNumber");
        Button changePhoneNumberButton = new Button("Change");
        TextField newPhoneNumberTextField = new TextField();
        Button savePhoneNumberButton = new Button("Save");

        newPhoneNumberTextField.setVisible(false);
        savePhoneNumberButton.setVisible(false);

        changePhoneNumberButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changePhoneNumberButton.setOnMouseEntered(event -> changePhoneNumberButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changePhoneNumberButton.setOnMouseExited(e -> changePhoneNumberButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        savePhoneNumberButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        savePhoneNumberButton.setOnMouseEntered(event -> savePhoneNumberButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        savePhoneNumberButton.setOnMouseExited(e -> savePhoneNumberButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changePhoneNumberButton.setOnAction(event -> {
            newPhoneNumberTextField.setVisible(true);
            savePhoneNumberButton.setVisible(true);
            changePhoneNumberButton.setVisible(false);
        });


        savePhoneNumberButton.setOnAction(event -> {
            newPhoneNumberTextField.setVisible(false);
            changePhoneNumberButton.setVisible(true);
            savePhoneNumberButton.setVisible(false);

            if (!newPhoneNumberTextField.getText().isEmpty()) {
                try {
                    Database connection = new Database();
                    String UpdatePhoneNumberquery = "UPDATE users SET `Phone Number` = '" + newPhoneNumberTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                    Statement updatePhoneNumberStatement = connection.connection.createStatement();
                    int rowaffected = updatePhoneNumberStatement.executeUpdate(UpdatePhoneNumberquery);

                    if (rowaffected > 0) {
                        currentPhoneNumberLabel.setText(newPhoneNumberTextField.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Phone Number has been updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Phone Number cannot be empty");
                alert.showAndWait();
            }

        });


        grid.add(phonenumberLabel, 0, 2);
        grid.add(currentPhoneNumberLabel, 1, 2);
        grid.add(changePhoneNumberButton, 2, 2);
        grid.add(newPhoneNumberTextField, 3, 2);
        grid.add(savePhoneNumberButton, 4, 2);



        //Address
        Label addressLabel = new Label("Address:");
        addressLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentAddressLabel = new Label("Current Address");
        Button changeAddressButton = new Button("Change");
        TextField newAddressTextField = new TextField();
        Button saveAddressButton = new Button("Save");

        newAddressTextField.setVisible(false);
        saveAddressButton.setVisible(false);

        changeAddressButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changeAddressButton.setOnMouseEntered(event -> changeAddressButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeAddressButton.setOnMouseExited(e -> changeAddressButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        saveAddressButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        saveAddressButton.setOnMouseEntered(event -> saveAddressButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        saveAddressButton.setOnMouseExited(e -> saveAddressButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeAddressButton.setOnAction(event -> {
            newAddressTextField.setVisible(true);
            saveAddressButton.setVisible(true);
            changeAddressButton.setVisible(false);
        });


        saveAddressButton.setOnAction(event -> {
            newAddressTextField.setVisible(false);
            changeAddressButton.setVisible(true);
            saveAddressButton.setVisible(false);

            if (!newAddressTextField.getText().isEmpty()) {
                try {
                    Database connection = new Database();
                    String UpdateAddressquery = "UPDATE users SET Address = '" + newAddressTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                    Statement updateAddressStatement = connection.connection.createStatement();
                    int rowaffected = updateAddressStatement.executeUpdate(UpdateAddressquery);

                    if (rowaffected > 0) {
                        currentAddressLabel.setText(newAddressTextField.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Address has been updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Address cannot be empty");
                alert.showAndWait();
            }

        });

        grid.add(addressLabel, 0, 3);
        grid.add(currentAddressLabel, 1, 3);
        grid.add(changeAddressButton, 2, 3);
        grid.add(newAddressTextField, 3, 3);
        grid.add(saveAddressButton, 4, 3);



        //Email
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentEmailLabel = new Label("Current Email");
        Button changeEmailButton = new Button("Change");
        TextField newEmailTextField = new TextField();
        Button saveEmailButton = new Button("Save");

        newEmailTextField.setVisible(false);
        saveEmailButton.setVisible(false);

        changeEmailButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changeEmailButton.setOnMouseEntered(event -> changeEmailButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeEmailButton.setOnMouseExited(e -> changeEmailButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        saveEmailButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        saveEmailButton.setOnMouseEntered(event -> saveEmailButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        saveEmailButton.setOnMouseExited(e -> saveEmailButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changeEmailButton.setOnAction(event -> {
            newEmailTextField.setVisible(true);
            saveEmailButton.setVisible(true);
            changeEmailButton.setVisible(false);
        });


        saveEmailButton.setOnAction(event -> {
            newEmailTextField.setVisible(false);
            changeEmailButton.setVisible(true);
            saveEmailButton.setVisible(false);

            if (!newEmailTextField.getText().isEmpty()) {
                if (ValidateEmail(newEmailTextField.getText())) {

                    try {

                        Database connection = new Database();
                        String UpdateEmailquery = "UPDATE users SET Email = '" + newEmailTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                        Statement updateEmailStatement = connection.connection.createStatement();
                        int rowaffected = updateEmailStatement.executeUpdate(UpdateEmailquery);

                        if (rowaffected > 0) {
                            currentEmailLabel.setText(newEmailTextField.getText());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Email has been updated");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Something went wrong");
                            alert.showAndWait();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email cannot be empty");
                alert.showAndWait();
            }

        });

        grid.add(emailLabel, 0, 4);
        grid.add(currentEmailLabel, 1, 4);
        grid.add(changeEmailButton, 2, 4);
        grid.add(newEmailTextField, 3, 4);
        grid.add(saveEmailButton, 4, 4);



        //Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 16;");
        Label currentPasswordLabel = new Label("Current Password");
        Button changePasswordButton = new Button("Change");
        TextField newPasswordTextField = new TextField();
        Button savePasswordButton = new Button("Save");

        newPasswordTextField.setVisible(false);
        savePasswordButton.setVisible(false);

        changePasswordButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        changePasswordButton.setOnMouseEntered(event -> changePasswordButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changePasswordButton.setOnMouseExited(e -> changePasswordButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));


        savePasswordButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        savePasswordButton.setOnMouseEntered(event -> savePasswordButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        savePasswordButton.setOnMouseExited(e -> savePasswordButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;"));

        changePasswordButton.setOnAction(event -> {
            newPasswordTextField.setVisible(true);
            savePasswordButton.setVisible(true);
            changePasswordButton.setVisible(false);
        });


        savePasswordButton.setOnAction(event -> {
            newPasswordTextField.setVisible(false);
            changePasswordButton.setVisible(true);
            savePasswordButton.setVisible(false);

            if (!newPasswordTextField.getText().isEmpty()) {
                try {
                    Database connection = new Database();
                    String UpdateEmailquery = "UPDATE users SET Password = '" + newPasswordTextField.getText() + "' WHERE idusers = '" + UserSession.getUserid() + "'";
                    Statement updateEmailStatement = connection.connection.createStatement();
                    int rowaffected = updateEmailStatement.executeUpdate(UpdateEmailquery);

                    if (rowaffected > 0) {
                        currentPasswordLabel.setText(newPasswordTextField.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Password has been updated");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password cannot be empty");
                alert.showAndWait();
            }

        });

        grid.add(passwordLabel, 0, 5);
        grid.add(currentPasswordLabel, 1, 5);
        grid.add(changePasswordButton, 2, 5);
        grid.add(newPasswordTextField, 3, 5);
        grid.add(savePasswordButton, 4, 5);


        MainSettingVBox.getChildren().addAll(grid);

        try {

            Database connection = new Database();
            String query = "SELECT * FROM users WHERE idusers = '" + UserSession.getUserid() + "'";
            Statement getUserStatement = connection.connection.createStatement();
            ResultSet getUserResultSet = getUserStatement.executeQuery(query);

            if (getUserResultSet.next()) {
                currentNameLabel.setText(getUserResultSet.getString("Name"));
                currentCountryLabel.setText(getUserResultSet.getString("Country"));
                currentPhoneNumberLabel.setText(getUserResultSet.getString("Phone Number"));
                currentAddressLabel.setText(getUserResultSet.getString("Address"));
                currentEmailLabel.setText(getUserResultSet.getString("Email"));
                currentPasswordLabel.setText(getUserResultSet.getString("Password"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something went wrong");
                alert.showAndWait();
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        return MainSettingVBox;
    }

    public HBox BackButton(Stage primaryStage) {

        HBox BackButtonHBox = new HBox();
        BackButtonHBox.setAlignment(Pos.CENTER_LEFT);
        BackButtonHBox.setPadding(new Insets(10, 10, 10, 10));

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12;" +
                "-fx-background-color: #a09fe5;");
        backBtn.setMinHeight(15);
        backBtn.setMinWidth(100);
        backBtn.setOnMouseEntered(event ->backBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;" +
                        "-fx-background-color: #c0bff8;"));

        backBtn.setOnMouseExited(event -> backBtn.setStyle(
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;" +
                        "-fx-background-color: #a09fe5;"));

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    RoomDisplay roomDisplay = new RoomDisplay();
                    roomDisplay.start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        BackButtonHBox.getChildren().add(backBtn);
        return BackButtonHBox;

    }

    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Logo());
        borderPane.setCenter(MainSetting());
        borderPane.setBottom(BackButton(primaryStage));

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Settings");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}
