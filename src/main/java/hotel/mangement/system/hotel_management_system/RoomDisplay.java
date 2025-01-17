package hotel.mangement.system.hotel_management_system;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.temporal.ChronoUnit;


public class RoomDisplay extends Application {

    @Override
    public void init() throws Exception {
        // when main is initialised, this will run
        Thread.sleep(2500);     // Simulates 4 seconds of loading
    }

    public VBox Logo(Stage primaryStage) {

        VBox mainVBox = new VBox();

        VBox imagevBox = new VBox();
        imagevBox.setAlignment(Pos.CENTER);
        imagevBox.setStyle("-fx-background-color: #a09fe5");

        Image image = new Image(getClass().getResource("/logo2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(200);
        imagevBox.getChildren().add(imageView);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(10, 0, 10, 0));
        buttonsHBox.setSpacing(10);
        buttonsHBox.setStyle(
                "-fx-border-color: transparent transparent #a09fe5 transparent;" +
                        "-fx-border-width: 0 0 2px 0;"); // Only bottom border with a thickness of 2px


        Button UserSettingBtn = new Button("Settings");
        UserSettingBtn.setStyle("-fx-background-color: #a09fe5; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14;");
        UserSettingBtn.setOnMouseEntered(e -> UserSettingBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        UserSettingBtn.setOnMouseExited(e -> UserSettingBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        UserSettingBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserSettings userSettings = new UserSettings();
                    userSettings.start(primaryStage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button UserBookingsBtn = new Button("Previous Bookings");
        UserBookingsBtn.setStyle("-fx-background-color: #a09fe5; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14;");
        UserBookingsBtn.setOnMouseEntered(e -> UserBookingsBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        UserBookingsBtn.setOnMouseExited(e -> UserBookingsBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        UserBookingsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserPreviousBookings userPreviousBookings = new UserPreviousBookings();
                    userPreviousBookings.start(primaryStage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //if the user is logged in, the page doesn't show login button, instead show seeting and bookings
        if (UserSession.isLoggedIn()) {
            buttonsHBox.getChildren().addAll(UserSettingBtn, UserBookingsBtn);
        }
        mainVBox.getChildren().addAll(imagevBox, buttonsHBox);



        return mainVBox;
    }


    //This shows the checkin and checkout dates. Once they have been selected and the search button is pressed. The user will be shown all the available rooms
    //
    public VBox Main(Stage primaryStage) {
        VBox vBoxMain = new VBox();
        vBoxMain.setAlignment(Pos.TOP_CENTER);
        vBoxMain.setSpacing(10);
        vBoxMain.setPadding(new Insets(30, 0, 0, 0));

        VBox dateSelectionVBoxMain = new VBox();
        dateSelectionVBoxMain.setAlignment(Pos.CENTER); // Center alignment for all children
        dateSelectionVBoxMain.setSpacing(20);

        Label checkInLabel = new Label("Check-In Date:");
        checkInLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        DatePicker checkInDatePicker = new DatePicker();
        VBox checkInvBox = new VBox();
        checkInvBox.setAlignment(Pos.CENTER);
        checkInvBox.setSpacing(5);
        checkInvBox.getChildren().addAll(checkInLabel, checkInDatePicker);

        Label checkOutLabel = new Label("Check-Out Date:");
        checkOutLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        DatePicker checkOutDatePicker = new DatePicker();
        VBox checkOutvBox = new VBox();
        checkOutvBox.setAlignment(Pos.CENTER);
        checkOutvBox.setSpacing(5);
        checkOutvBox.getChildren().addAll(checkOutLabel, checkOutDatePicker);

        HBox dateSelectionhBox = new HBox();
        dateSelectionhBox.setAlignment(Pos.CENTER);
        dateSelectionhBox.setSpacing(20); // Add spacing between Check-In and Check-Out sections
        dateSelectionhBox.getChildren().addAll(checkInvBox, checkOutvBox);

        Button searchButton = new Button("Search Rooms");
        searchButton.setPrefWidth(130);
        searchButton.setPrefHeight(30);
        searchButton.setStyle("-fx-background-color: #a09fe5;");
        searchButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        searchButton.setOnMouseEntered(event ->searchButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold;"));

        searchButton.setOnMouseExited(e -> searchButton.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-weight: bold"));


        dateSelectionVBoxMain.getChildren().addAll(dateSelectionhBox, searchButton);
        // Add a bottom border to the VBox
        dateSelectionVBoxMain.setStyle("-fx-border-color: #a09fe5; -fx-border-width: 0 0 2 0; -fx-border-style: solid;" +  "-fx-padding: 0 0 20 0;");


        vBoxMain.getChildren().add(dateSelectionVBoxMain);

        //Once the submit button is clicked. This will show the title and the vbox roomSection containing the flowPane that contains the rooms.
        VBox roomSection = new VBox();
        roomSection.setAlignment(Pos.TOP_CENTER);
        roomSection.setSpacing(10);

        //The flowpane contains all of the rooms once they are fetched. Flowpane allows the rooms to fill up the line and then show underneath
        FlowPane roomPane = new FlowPane();
        roomPane.setAlignment(Pos.CENTER);
        roomPane.setHgap(15); // Horizontal gap between items
        roomPane.setVgap(15); // Vertical gap between items
        roomPane.setPrefWrapLength(600); // Wrap width
        roomPane.setPadding(new Insets(10, 0, 0, 0));


        Label titleLabel = new Label(" Our Selection");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        try {

            Database connection = new Database();
                    /*
                    There are multiple rooms with the same bed type and room type and price, the min() function returns the smallest idrooms (room ID) for each combination of Bed Type, Room Type, and Price
                    retrieves the minimum idrooms for rooms that are available for booking, excluding those already reserved during the
                    specified date range (checkInDate to checkOutDate). It groups the results by Bed Type, Room Type, and Price, showing the
                    available room types along with bed type
                     */

            String query = "SELECT MIN(rooms.idrooms) AS idrooms, " +
                    "       rooms.`Bed Type`, " +
                    "       rooms.`Room Type`, " +
                    "       rooms.`Price` " +
                    "FROM `hotel_management`.`rooms` AS rooms " +
                    "GROUP BY rooms.`Bed Type`, rooms.`Room Type`, rooms.`Price`;";
            ResultSet resultSet = connection.statement.executeQuery(query);
            while (resultSet.next()) {

                String bedType  = resultSet.getString("Bed Type");
                String roomType = resultSet.getString("Room Type");
                String roomPrice = resultSet.getString("Price");


                // Room display VBox
                VBox roomBox = new VBox();
                roomBox.setAlignment(Pos.CENTER);

                ImageView imageView = new ImageView();

                // Changes the image displayed depending on the roomtype and bedtype
                if (roomType.equals("Standard Room") && bedType.equals("Single")) {
                    Image image = new Image(getClass().getResource("/StandardSingleroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Standard Room") && bedType.equals("Double")) {
                    Image image = new Image(getClass().getResource("/StandardDoubleroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Standard Room") && bedType.equals("King")) {
                    Image image = new Image(getClass().getResource("/StandardKingroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Deluxe Room") && bedType.equals("Single")) {
                    Image image = new Image(getClass().getResource("/DeluxeKingroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Deluxe Room") && bedType.equals("Double")) {
                    Image image = new Image(getClass().getResource("/DeluxeDoubleroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Deluxe Room") && bedType.equals("King")) {
                    Image image = new Image(getClass().getResource("/DeluxeKingroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Suite") && bedType.equals("Single")) {
                    Image image = new Image(getClass().getResource("/SuiteSingleroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Suite") && bedType.equals("Double")) {
                    Image image = new Image(getClass().getResource("/SuiteDoubleroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else if (roomType.equals("Suite") && bedType.equals("King")) {
                    Image image = new Image(getClass().getResource("/SuiteKingroom.jpg").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                } else {
                    Image image = new Image(getClass().getResource("/room.png").toExternalForm());
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(180);
                }

                // Label that is displayed under the image showing the room type and bed type
                Label roomLabel = new Label(roomType + " - " + bedType);
                roomLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-weight: bold; -fx-font-size: 14px;");
                Label feeLabel = new Label(" £" + roomPrice);
                feeLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-weight: bold; -fx-font-size: 14px;");

                //displayed under the label.
                // If the user is not logged in, then the user will be shown the login page and the room details set in UserSession. After the user logs in, the bookingroom page is displayed, where the details of the room are retrieved from userSession
                //if the user is logged in, then the user will be shown bookingroom page, where the details will be retrieved from userSession



                // Add image and label to roomBox
                roomBox.getChildren().addAll(imageView, roomLabel, feeLabel);
                roomBox.setOnMouseEntered(e -> roomBox.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-text-fill: black;" +
                                "-fx-border-color: black;" + // Add black border
                                "-fx-border-width: 1.3px;"    // Specify border width
                ));
                roomBox.setOnMouseExited(e -> roomBox.setStyle(
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-text-fill: black;" +
                                "-fx-border-color: transparent;" + // Remove border
                                "-fx-border-width: 0px;"           // Reset border width
                ));


                // Add roomBox to FlowPane
                roomPane.getChildren().add(roomBox);

            }
            roomSection.getChildren().addAll(titleLabel, roomPane);

        } catch (Exception e) {
            e.printStackTrace();
        }









        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                roomPane.getChildren().clear();
                roomSection.getChildren().clear();

                Label titleLabel = new Label("Available Rooms");
                titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                try {

                    //calculate the number of days between the dates to determine price
                    Date checkindate = Date.valueOf(checkInDatePicker.getValue());
                    Date checkoutdate = Date.valueOf(checkOutDatePicker.getValue());

                    long daysBetween = ChronoUnit.DAYS.between(
                            checkindate.toLocalDate(),
                            checkoutdate.toLocalDate()
                    );


                    //retrieve data values that the user selected
                    java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDatePicker.getValue());
                    java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutDatePicker.getValue());

                    Database connection = new Database();
                    /*
                    There are multiple rooms with the same bed type and room type and price, the min() function returns the smallest idrooms (room ID) for each combination of Bed Type, Room Type, and Price
                    retrieves the minimum idrooms for rooms that are available for booking, excluding those already reserved during the
                    specified date range (checkInDate to checkOutDate). It groups the results by Bed Type, Room Type, and Price, showing the
                    available room types along with bed type
                     */

                    String query = "SELECT MIN(rooms.idrooms) AS idrooms, " +
                            "       rooms.`Bed Type`, " +
                            "       rooms.`Room Type`, " +
                            "       rooms.`Price` " +
                            "FROM `hotel_management`.`rooms` AS rooms " +
                            "WHERE rooms.`idrooms` NOT IN ( " +
                            "      SELECT bookings.idrooms " +
                            "      FROM `hotel_management`.`bookings` AS bookings " +
                            "      WHERE bookings.`Check In Date` < '" + checkOutDate + "' " +
                            "        AND bookings.`Check Out Date` > '" + checkInDate + "' " +
                            "  ) " +
                            "GROUP BY rooms.`Bed Type`, rooms.`Room Type`, rooms.`Price`;";
                    ResultSet resultSet = connection.statement.executeQuery(query);
                    while (resultSet.next()) {

                        String bedType  = resultSet.getString("Bed Type");
                        String roomType = resultSet.getString("Room Type");
                        String roomPrice = resultSet.getString("Price");

                        double price = resultSet.getDouble("Price");
                        double fee = price * daysBetween;
                        String stringfee = String.valueOf(fee);


                        // Room display VBox
                        VBox roomBox = new VBox();
                        roomBox.setAlignment(Pos.CENTER);

                        ImageView imageView = new ImageView();

                        // Changes the image displayed depending on the roomtype and bedtype
                        if (roomType.equals("Standard Room") && bedType.equals("Single")) {
                            Image image = new Image(getClass().getResource("/StandardSingleroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Standard Room") && bedType.equals("Double")) {
                            Image image = new Image(getClass().getResource("/StandardDoubleroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Standard Room") && bedType.equals("King")) {
                            Image image = new Image(getClass().getResource("/StandardKingroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Deluxe Room") && bedType.equals("Single")) {
                            Image image = new Image(getClass().getResource("/DeluxeKingroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Deluxe Room") && bedType.equals("Double")) {
                            Image image = new Image(getClass().getResource("/DeluxeDoubleroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Deluxe Room") && bedType.equals("King")) {
                            Image image = new Image(getClass().getResource("/DeluxeKingroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Suite") && bedType.equals("Single")) {
                            Image image = new Image(getClass().getResource("/SuiteSingleroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Suite") && bedType.equals("Double")) {
                            Image image = new Image(getClass().getResource("/SuiteDoubleroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else if (roomType.equals("Suite") && bedType.equals("King")) {
                            Image image = new Image(getClass().getResource("/SuiteKingroom.jpg").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                        } else {
                            Image image = new Image(getClass().getResource("/room.png").toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(180);
                        }

                        // Label that is displayed under the image showing the room type and bed type
                        Label roomLabel = new Label(roomType + " - " + bedType);
                        roomLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-weight: bold; -fx-font-size: 14px;");
                        Label feeLabel = new Label(" £" + stringfee);
                        feeLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-weight: bold; -fx-font-size: 14px;");

                        //displayed under the label.
                        // If the user is not logged in, then the user will be shown the login page and the room details set in UserSession. After the user logs in, the bookingroom page is displayed, where the details of the room are retrieved from userSession
                        //if the user is logged in, then the user will be shown bookingroom page, where the details will be retrieved from userSession
                        Hyperlink bookLink = new Hyperlink("Book");
                        bookLink.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;");
                        bookLink.setOnAction(event2 -> {
                            String selectedRoomType = roomType;
                            String selectedBedType = bedType;

                            if (!UserSession.isLoggedIn()) {
                                // Redirect to login page
                                UserLogin loginPage = new UserLogin();
                                UserSession.setBookingContext(selectedRoomType, selectedBedType, stringfee, checkInDate, checkOutDate); // Set room details
                                loginPage.start(primaryStage);
                            } else if (UserSession.isLoggedIn()) {
                                // Redirect to booking page
                                BookingRoom BookingRoom = new BookingRoom(selectedRoomType, selectedBedType, stringfee, UserSession.getUserid(), checkInDate, checkOutDate);
                                BookingRoom.start(primaryStage);
                            }
                        });

                        VBox bookLinkvBox = new VBox();
                        bookLinkvBox.getChildren().add(bookLink);
                        bookLinkvBox.setAlignment(Pos.CENTER);
                        BorderPane bookLinkBorderPane = new BorderPane(bookLinkvBox);


                        // Add image and label to roomBox
                        roomBox.getChildren().addAll(imageView, roomLabel, feeLabel, bookLinkBorderPane);
                        roomBox.setOnMouseEntered(e -> roomBox.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                                        "-fx-font-weight: bold;" +
                                                "-fx-font-size: 14px;" +
                                        "-fx-text-fill: black;" +
                                        "-fx-border-color: black;" + // Add black border
                                        "-fx-border-width: 1.3px;"    // Specify border width
                        ));
                        roomBox.setOnMouseExited(e -> roomBox.setStyle(
                                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                                        "-fx-font-weight: bold;" +
                                                "-fx-font-size: 14px;" +
                                        "-fx-text-fill: black;" +
                                        "-fx-border-color: transparent;" + // Remove border
                                        "-fx-border-width: 0px;"           // Reset border width
                        ));


                        // Add roomBox to FlowPane
                        roomPane.getChildren().add(roomBox);

                    }
                    roomSection.getChildren().addAll(titleLabel, roomPane);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        vBoxMain.getChildren().add(roomSection);

        return vBoxMain;

    }

    public HBox SignInButtons(Stage primaryStage) {

        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomHBox.setPadding(new Insets(0, 20, 10, 10)); // Add padding (top, right, bottom, left)
        bottomHBox.setSpacing(10);

        Button staffSignInButton = new Button("Staff Sign In");
        staffSignInButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14;");
        staffSignInButton.setOnMouseEntered(e -> staffSignInButton.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        staffSignInButton.setOnMouseExited(e -> staffSignInButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        staffSignInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    StaffLogin staffLogin = new StaffLogin();
                    staffLogin.start(primaryStage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button LoginUserBtn = new Button("Login User");
        LoginUserBtn.setStyle("-fx-background-color: transparent; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14;");
        LoginUserBtn.setOnMouseEntered(e -> LoginUserBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        LoginUserBtn.setOnMouseExited(e -> LoginUserBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        LoginUserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserLogin userLogin = new UserLogin();
                    userLogin.start(primaryStage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button LogoutUserBtn = new Button("Logout");
        LogoutUserBtn.setStyle("-fx-background-color: transparent; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 14;");
        LogoutUserBtn.setOnMouseEntered(e -> LogoutUserBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        LogoutUserBtn.setOnMouseExited(e -> LogoutUserBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;"));
        //it will forget all of the user's details in UserSession and re-display RoomDisplay
        LogoutUserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to logout?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        UserSession.logout();
                        RoomDisplay roomDisplay = new RoomDisplay();
                        roomDisplay.start(primaryStage);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //if user isn't logged in, the page shows login button
        if (!UserSession.isLoggedIn()) {
            bottomHBox.getChildren().addAll(staffSignInButton, LoginUserBtn);
        }
        //if user is logged in, the page shows the logout button
        if (UserSession.isLoggedIn()) {
            bottomHBox.getChildren().addAll(LogoutUserBtn);
        }

        return bottomHBox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderpane = new BorderPane();       //used to create the main layout of the page
        borderpane.setTop(Logo(primaryStage));      //will show the logo on the top of the page
        borderpane.setCenter(Main(primaryStage));       //will show the main section. Passing primary Stage, as book hyperlink requires it to open bookrooms page
        borderpane.setBottom(SignInButtons(primaryStage));      //section showing sign in staff button and user log in button

        primaryStage.setTitle("Room Display");
        primaryStage.setResizable(false);

        Scene scene = new Scene(borderpane, 1200, 880);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();



    }

    public static void main(String[] args) {
        // Specify the custom preloader
        System.setProperty("javafx.preloader", SplashScreen.class.getName());
        launch(args);
    }

}


