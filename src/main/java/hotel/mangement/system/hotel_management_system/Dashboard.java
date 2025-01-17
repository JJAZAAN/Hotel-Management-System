package hotel.mangement.system.hotel_management_system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


public class Dashboard extends Application {

    public VBox MainDashboard(Stage primaryStage) {

        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);      //positions the grid to the left of the screen
        grid.setHgap(100);       //gap manages the spacing between the rows and the columns
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Image image1 = new Image(getClass().getResource("/reception.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(100);
        imageView1.setFitHeight(100);
        grid.add(imageView1, 0, 0);

        Button receptionBtn = new Button("     Reception    ");
        receptionBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-background-color: #a09fe5;");
        grid.add(receptionBtn, 0, 1);
        receptionBtn.setOnMouseEntered(event ->receptionBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';"));

        receptionBtn.setOnMouseExited(e -> receptionBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';"));

        receptionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Reception Reception = new Reception();
                    Reception.start(primaryStage);
                }catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        Image image2 = new Image(getClass().getResource("/boss.png").toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(100);
        grid.add(imageView2, 1, 0);

        Button adminBtn = new Button("       Admin        ");
        adminBtn.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-background-color: #a09fe5;");
        grid.add(adminBtn, 1, 1);
        adminBtn.setOnMouseEntered(event ->adminBtn.setStyle(       //event ->, means what should have when the mouse enters the area. The MouseEvent is an object that contains information about the mouse event
                "-fx-background-color: #c0bff8;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';"));

        adminBtn.setOnMouseExited(e -> adminBtn.setStyle(
                "-fx-background-color: #a09fe5;" +
                        "-fx-font-family: 'Arial Rounded MT Bold';"));

        adminBtn.setOnAction(new EventHandler<ActionEvent>() {
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

        main.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        main.getChildren().add(grid);

        return main;

    }

    @Override
    public void start(Stage primaryStage) throws Exception  {



        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(MainDashboard(primaryStage));

        Scene scene = new Scene(borderPane, 600, 400);        //the grid is set as the root node, numbers are the size of the window
        primaryStage.setTitle("Dashboard");
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);

        //background colour

        primaryStage.show();
    }

}
