package test.GUI;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopolinho.obxetos.Taboeiro;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        Taboeiro t=new Taboeiro();
        TaboeiroUI tUI=new TaboeiroUI(t);


        Scene scene = new Scene(tUI, 1080, 900);
        primaryStage.setTitle("Monopolinho");
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
