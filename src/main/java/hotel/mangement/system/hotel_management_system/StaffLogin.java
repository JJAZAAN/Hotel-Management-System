package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Font;      //specifies the font
import javafx.scene.text.FontWeight;        //specifies the font weight
import javafx.scene.text.Text;      //defines a node that displays text
import javafx.scene.control.TextField;      //allows a user to enter a single line of unformatted text
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;    //load an image in the background
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.ResultSet;


public class StaffLogin extends Application {


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

        Text scenetitle = new Text("Staff Login");      //A text object cannot be edited, a label can.
        scenetitle.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 28));
        gridpane.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        userName.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));
        gridpane.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter Username");
        userTextField.setMinHeight(35);
        userTextField.setPrefWidth(200);
        gridpane.add(userTextField, 1, 1);

        Label password = new Label("Password:");
        gridpane.add(password, 0, 2);
        password.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));

        PasswordField passwordBox = new PasswordField();
        passwordBox.setPromptText("Enter Password");
        passwordBox.setMinHeight(35);
        passwordBox.setPrefWidth(200);
        gridpane.add(passwordBox, 1, 2);

        Button loginBtn = new Button("Sign in");
        loginBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18;" +
                "-fx-background-color: #a09fe5;");
        loginBtn.setMinHeight(15);
        loginBtn.setMinWidth(100);
        GridPane.setColumnSpan(loginBtn, 2);
        GridPane.setHalignment(loginBtn, HPos.CENTER); // Center-align the button within its cell
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
                    String username = userTextField.getText();
                    String password = passwordBox.getText();

                    String query = "SELECT Admin FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                    ResultSet resultSet = connection.statement.executeQuery(query);
                    if (resultSet.next()) {
                        String admin = resultSet.getString("Admin");
                        if (admin.equals("1")) {
                            Dashboard dashboard = new Dashboard();
                            dashboard.start(primaryStage);
                        } else {
                            Reception reception = new Reception();
                            reception.start(primaryStage);
                        }

                    }else {
                        Alert alert = new Alert(AlertType.ERROR);
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

        return main;

    }

    @Override
    public void start(Stage primaryStage) throws Exception  {
        // Create the login/signup page

        primaryStage.setTitle("StaffLogin");
        primaryStage.setResizable(false);






        //grid.setGridLinesVisible(true);


        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(SidePanel());
        borderPane.setCenter(MainPanel(primaryStage));

        Scene scene = new Scene(borderPane, 700, 400);        //the grid is set as the root node, numbers are the size of the window
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


}