package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;

import java.io.StringReader;


public class Reception extends Application {

    //function to set the style for buttons
    public static void setButtonMouseEvents(Button button) {
        button.setOnMouseEntered(event ->button.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: black;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" + "-fx-text-fill: white;"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;"));
    }

    public VBox NavigationMenu(Stage primaryStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50));
        vbox.setSpacing(25);

        Button createaccountbtn = new Button("Create Account");
        createaccountbtn.setStyle("-fx-background-color: white;");
        createaccountbtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        createaccountbtn.setPrefWidth(250);
        setButtonMouseEvents(createaccountbtn);

        createaccountbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SignupUser SignupUser = new SignupUser();
                    Stage SignupUserStage = new Stage();
                    SignupUser.start(SignupUserStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button bookroomBtn = new Button("Book Room");
        bookroomBtn.setStyle("-fx-background-color: white;");
        bookroomBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        bookroomBtn.setPrefWidth(250);
        setButtonMouseEvents(bookroomBtn);

        bookroomBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    BookingRoomsReception BookingRoomsReception = new BookingRoomsReception();
                    Stage BookingRoomsReceptionStage = new Stage();
                    BookingRoomsReception.start(BookingRoomsReceptionStage);
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button checkinBtn = new Button("Check In");
        checkinBtn.setStyle("-fx-background-color: white;");
        checkinBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        checkinBtn.setPrefWidth(250);
        setButtonMouseEvents(checkinBtn);

        checkinBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    CheckIn CheckIn = new CheckIn();
                    Stage CheckInStage = new Stage();
                    CheckIn.start(CheckInStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button check_outBtn = new Button("Check Out");
        check_outBtn.setStyle("-fx-background-color: white;");
        check_outBtn.setPrefWidth(250);
        check_outBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        setButtonMouseEvents(check_outBtn);

        check_outBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    CheckOut CheckOut = new CheckOut();
                    Stage CheckOutStage = new Stage();
                    CheckOut.start(CheckOutStage);
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Button searchroomBtn = new Button("Search Room");
        searchroomBtn.setStyle("-fx-background-color: white;");
        searchroomBtn.setPrefWidth(250);
        searchroomBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        setButtonMouseEvents(searchroomBtn);

        searchroomBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SearchRoom SearchRoom = new SearchRoom();
                Stage SearchRoomStage = new Stage();
                SearchRoom.start(SearchRoomStage);
            }
        });

        Button search_bookingBtn = new Button("Search Bookings");
        search_bookingBtn.setStyle("-fx-background-color: white;");
        search_bookingBtn.setPrefWidth(250);
        search_bookingBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        setButtonMouseEvents(search_bookingBtn);

        search_bookingBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SearchBooking SearchBooking = new SearchBooking();
                Stage SearchBookingStage = new Stage();
                SearchBooking.start(SearchBookingStage);
            }
        });

        Button search_customerBtn = new Button("Search Customer");
        search_customerBtn.setStyle("-fx-background-color: white;");
        search_customerBtn.setPrefWidth(250);
        search_customerBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        setButtonMouseEvents(search_customerBtn);

        search_customerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SearchCustomer SearchCustomer = new SearchCustomer();
                Stage SearchCustomerStage = new Stage();
                SearchCustomer.start(SearchCustomerStage);
            }
        });



        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: white;");
        backBtn.setPrefWidth(250);
        backBtn.setFont(javafx.scene.text.Font.font("Arial Rounded MT Bold", FontWeight.BOLD , 16));
        setButtonMouseEvents(backBtn);

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Dashboard Dashboard = new Dashboard();
                    Dashboard.start(primaryStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });



        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(260);
        VBox.setMargin(imageView, new Insets(185, 0, 0, 0));        //add space between the buttons and the image

        vbox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 3, 0, 0))));    //add a line in the middle to make it look seperate


        vbox.getChildren().addAll(createaccountbtn, bookroomBtn, checkinBtn, check_outBtn, searchroomBtn, search_bookingBtn, search_customerBtn, backBtn, imageView);
        return vbox;
    }

    public VBox ReceptionMain() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color: a09fe5;");

        Image image = new Image(getClass().getResource("/logo3.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(520);

        vbox.setAlignment(Pos.TOP_CENTER);

        vbox.getChildren().addAll(imageView);

        return vbox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception  {

        BorderPane borderpane = new BorderPane();       //used to create the main layout of the page
        borderpane.setLeft(NavigationMenu(primaryStage));
        borderpane.setCenter(ReceptionMain());


        Scene scene = new Scene(borderpane, 1500, 1000);        //the grid is set as the root node, numbers are the size of the window
        primaryStage.setScene(scene);

        //background colour
        BackgroundFill background_fill = new BackgroundFill(Color.web("#a09fe5"), CornerRadii.EMPTY, Insets.EMPTY);     //create a background fill
        Background background = new Background(background_fill);        // create Background
        borderpane.setBackground(background);   //fill the borderpane with the background colour

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}


