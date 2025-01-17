package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.sql.ResultSet;


public class UserLogin extends Application {


    public VBox SidePanel() {

        VBox main = new VBox();
        main.setAlignment(Pos.TOP_CENTER);
        main.setSpacing(2);
        main.setMinWidth(270);

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.TOP_CENTER);
        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imagevBox.getChildren().add(imageView);

        main.getChildren().add(imagevBox);

        Label label = new Label("Premium Getaways");
        label.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #eaa842;");
        main.getChildren().addAll(label);

        main.setStyle("-fx-background-color: #a09fe5");

        return main;
    }

    public VBox MainPanel(Stage primaryStage) {

        VBox main = new VBox();
        main.setPadding(new Insets(50, 10, 10, 50));
        main.setAlignment(Pos.TOP_CENTER);



        GridPane gridpane = new GridPane();     //creates the gridpane and names it grid
        gridpane.setAlignment(Pos.CENTER_LEFT);      //positions the grid to the left of the screen
        gridpane.setHgap(10);       //gap manages the spacing between the rows and the columns
        gridpane.setVgap(22);
        gridpane.setPadding(new Insets(55, 10, 10, 10));        //the padding property manages the space around the edges of the grid pane. The insets are in the order of top, right, bottom, and left.

        Text scenetitle = new Text("Login");      //A text object cannot be edited, a label can.
        scenetitle.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 28));
        gridpane.add(scenetitle, 0, 0);

        Label Email = new Label("Email:");
        gridpane.add(Email, 0, 1);
        Email.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));

        TextField EmailTextField = new TextField();
        EmailTextField.setPromptText("Enter Email");
        EmailTextField.setMinHeight(35);
        EmailTextField.setPrefWidth(200);
        gridpane.add(EmailTextField, 1, 1);

        Label password = new Label("Password:");
        gridpane.add(password, 0, 2);
        password.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));

        PasswordField passwordBox = new PasswordField();
        passwordBox.setPromptText("Enter Password");
        passwordBox.setMinHeight(35);
        passwordBox.setPrefWidth(200);
        gridpane.add(passwordBox, 1, 2);

        Button loginBtn = new Button("Log in");
        loginBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;" +
                "-fx-background-color: #a09fe5;");
        loginBtn.setMinHeight(15);
        loginBtn.setMinWidth(100);
        GridPane.setColumnSpan(loginBtn, 2);
        GridPane.setHalignment(loginBtn, HPos.CENTER); // By default, the button will span to the left of the cell when you span across multiple columns so you need this to make it be in the center
        gridpane.add(loginBtn, 0, 3);
        loginBtn.setOnMouseEntered(event ->loginBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-color: #c0bff8;"));

        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
                "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-color: #a09fe5;"));

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Database connection = new Database();
                    String EmailText = EmailTextField.getText();
                    String passwordText = passwordBox.getText();

                    //Try and retrieve the user login details from user
                    String query = "SELECT * FROM users WHERE Email = '" + EmailText + "' AND password = '" + passwordText + "'";
                    ResultSet resultSet = connection.statement.executeQuery(query);

                    //if the user account is found then is sets userSession login to true. It then sets the userid, which will be used for the bookingRooms
                    if (resultSet.next()) {
                        UserSession.login();
                        int userid = resultSet.getInt("idusers");
                        UserSession.setUserid(userid);
                        if (UserSession.isBookingContext()) {       //isbookingcontext returns bookingcontext. if its true then that means the room details have been set.  so it displays the BookingRoom page and retrieves the room details that the user selected
                            BookingRoom BookingRoom = new BookingRoom(
                                    UserSession.getRoomType(),
                                    UserSession.getBedType(),
                                    UserSession.getFee(),
                                    UserSession.getUserid(),
                                    UserSession.getCheckInDate(),
                                    UserSession.getCheckOutDate()
                            );
                            BookingRoom.start(primaryStage);
                        } else {
                            // Redirect to the user dashboard
                            RoomDisplay RoomDisplay = new RoomDisplay();
                            RoomDisplay.start(primaryStage);
                        }

                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Incorrect Username or Password");

                        alert.showAndWait();
                    }
                    //connection.statement.executeQuery(query);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });


        main.getChildren().add(gridpane);

        //This is to display the signin button and label
        HBox signinhBox = new HBox();

        signinhBox.setAlignment(Pos.BOTTOM_LEFT);
        signinhBox.setSpacing(5);
        Label signInTextLabel = new Label("Not a User?");
        signInTextLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; " +
                "-fx-font-size: 14;");
        Label SignInLabel = new Label("Sign In");
        SignInLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; " +
                "-fx-font-size: 14;" +
                "-fx-text-fill: blue;");
        SignInLabel.setOnMouseEntered(event -> SignInLabel.setStyle(
                "-fx-text-fill: #1515b8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold'; " +
                        "-fx-font-size: 14;"
        ));
        SignInLabel.setOnMouseExited(event -> SignInLabel.setStyle(
                "-fx-text-fill: blue;" +
                        "-fx-font-family: 'Arial Rounded MT Bold'; " +
                        "-fx-font-size: 14;"
        ));
        SignInLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    SignupUser signupUser = new SignupUser();
                    signupUser.start(primaryStage);
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
        signinhBox.getChildren().addAll(signInTextLabel, SignInLabel);


        VBox.setVgrow(signinhBox, Priority.ALWAYS);     //the vbox main adds space between its child nodes. So signinbox was not full on the bottom, it had some space. This forces the hbox signinbox to stretch and occupy all space inside main vbox
        main.getChildren().add(signinhBox);

        return main;

    }

    @Override
    public void start(Stage primaryStage) {




        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(SidePanel());        //Displays the image and a label on the left side of the borderpane
        borderPane.setCenter(MainPanel(primaryStage));      //displays the login relevant data

        Scene scene = new Scene(borderPane, 700, 400);        //the grid is set as the root node, numbers are the size of the window
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();


    }
}
