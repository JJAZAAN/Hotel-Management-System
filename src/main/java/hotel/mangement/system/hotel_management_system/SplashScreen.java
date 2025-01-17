package hotel.mangement.system.hotel_management_system;


import javafx.application.Preloader;
import javafx.scene.Scene;      //container for everything
import javafx.scene.image.Image;    //load an image in the background
import javafx.scene.image.ImageView;    //resizing the displayed image using image class
import javafx.scene.layout.StackPane;   //lays out its children in a back-to-front stack.
import javafx.stage.Stage;      //top level container
import javafx.stage.StageStyle;     //Defines a Stage style with a solid white background and minimal platform decorations


public class SplashScreen extends Preloader {
    private Stage preloaderStage;       //creates preloaderStage which is of type Stage


    @Override
    public void init() throws Exception {


    }


    @Override
    public void start(Stage primaryStage) throws Exception {        //This is the main entry point for the application, passes primaryStage of type Stage
        preloaderStage = primaryStage;


        // Load the image for the splash screen
        Image image = new Image(getClass().getResource("/splashscreen.jpg").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1024);
        imageView.setFitHeight(800);


        // Set up the scene
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 1024, 800);
        primaryStage.initStyle(StageStyle.UNDECORATED); // Defines solid white background with no decorations
        primaryStage.setScene(scene);       //set scene as root of the application
        primaryStage.centerOnScreen();


        //show the stage
        primaryStage.show();
    }


    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            // Close the splash screen when the main application is ready
            preloaderStage.close();
        }
    }


}
