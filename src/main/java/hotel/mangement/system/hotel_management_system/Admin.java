package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Admin extends Application {

    public VBox Menu(Stage primaryStage) {          //need to pass primarystage, as it is used when clicking the addRooms or addEmployee button. Needed to call the relevant page
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(15);


        Button add_employeeBtn = new Button("Add Employee");
        add_employeeBtn.setPrefWidth(225);
        add_employeeBtn.setStyle("-fx-background-color: white;");
        add_employeeBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        add_employeeBtn.setOnMouseEntered(event ->add_employeeBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        add_employeeBtn.setOnMouseExited(e -> add_employeeBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));

        vbox.getChildren().addAll(add_employeeBtn);       //adding the add employee image and the button together into the vbox

        add_employeeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddEmployee AddEmployee = new AddEmployee();
                    Stage AddEmployeeStage = new Stage();
                    AddEmployee.start(AddEmployeeStage);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button add_roomBtn = new Button("Add Rooms");
        add_roomBtn.setPrefWidth(225);
        add_roomBtn.setStyle("-fx-background-color: white;");
        add_roomBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        add_roomBtn.setOnMouseEntered(event ->add_roomBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        add_roomBtn.setOnMouseExited(e -> add_roomBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));

        vbox.getChildren().addAll(add_roomBtn);        //adding add room image and the button together into the vbox

        add_roomBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddRoom AddRoom = new AddRoom();
                    Stage AddRoomStage = new Stage();
                    AddRoom.start(AddRoomStage);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button change_employee_detailsBtn = new Button("Change Employee Details");
        change_employee_detailsBtn.setPrefWidth(225);
        change_employee_detailsBtn.setStyle("-fx-background-color: white;");
        change_employee_detailsBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        change_employee_detailsBtn.setOnMouseEntered(event ->change_employee_detailsBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        change_employee_detailsBtn.setOnMouseExited(e -> change_employee_detailsBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));

        vbox.getChildren().addAll(change_employee_detailsBtn);        //adding add room image and the button together into the vbox

        change_employee_detailsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ChangeEmployeeDetails ChangeEmployeeDetails = new ChangeEmployeeDetails();
                    Stage ChangeEmployeeDetailsStage = new Stage();
                    ChangeEmployeeDetails.start(ChangeEmployeeDetailsStage);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button change_room_detailsBtn = new Button("Change Room Details");
        change_room_detailsBtn.setPrefWidth(225);
        change_room_detailsBtn.setStyle("-fx-background-color: white;");
        change_room_detailsBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        change_room_detailsBtn.setOnMouseEntered(event ->change_room_detailsBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        change_room_detailsBtn.setOnMouseExited(e -> change_room_detailsBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));

        vbox.getChildren().addAll(change_room_detailsBtn);        //adding add room image and the button together into the vbox

        change_room_detailsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ChangeRoomDetails ChangeRoomDetails = new ChangeRoomDetails();
                    Stage ChangeRoomDetailsStage = new Stage();
                    ChangeRoomDetails.start(ChangeRoomDetailsStage);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button backbtn = new Button("Back");
        backbtn.setPrefWidth(225);
        backbtn.setStyle("-fx-background-color: white;");
        backbtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        backbtn.setOnMouseEntered(event -> backbtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        backbtn.setOnMouseExited(e -> backbtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));

        vbox.getChildren().addAll(backbtn);        //adding add room image and the button together into the vbox

        backbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Dashboard Dashboard = new Dashboard();
                    Dashboard.start(primaryStage);

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });


        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);
        VBox.setMargin(imageView, new Insets(420, 0, 0, 50));        //add space between the buttons and the image
        vbox.getChildren().add(imageView);

        vbox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 3, 0, 0))));    //add a line in the middle to make it look seperate

        return vbox;

    }

    public HBox Logo() {        //this is used to store the logo on the admin page
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);

        Image image = new Image(getClass().getResource("/logo3.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setFitHeight(300);
        hbox.getChildren().addAll(imageView);

        return hbox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderpane = new BorderPane();       //used to create the main layout of the page
        borderpane.setLeft(Menu(primaryStage));
        borderpane.setCenter(Logo());


        Scene scene = new Scene(borderpane, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();


        primaryStage.setTitle("Admin");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        BackgroundFill background_fill = new BackgroundFill(Color.web("#a09fe5"), CornerRadii.EMPTY, Insets.EMPTY);     //create a background fill
        Background background = new Background(background_fill);        // create Background

        borderpane.setBackground(background);   //fill the hbox with the background colour
    }
}
