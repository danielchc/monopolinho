package test.GUI;

import java.awt.*;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import monopolinho.obxetos.Casilla;
import javafx.scene.text.Text;

public class CasillaUI extends StackPane {

    public CasillaUI(Casilla c) {
        Color k=Color.AZURE;
        switch (c.getColorCasilla()){
            case ANSI_BLUE_BACKGROUND:
                k=Color.rgb(0, 131, 245);
                break;
            case ANSI_PURPLE_BACKGROUND:
                k=Color.PURPLE;
                break;
            case ANSI_HIGH_YELLOW_BACKGROUND:
                k=Color.YELLOW;
                break;
            case ANSI_RED_BACKGROUND:
                k=Color.RED;
                break;
                case ANSI_GREEN_BACKGROUND:
                k=Color.GREEN;
                break;
            case ANSI_HIGH_WHITE_BACKGROUND:
                k=Color.NAVAJOWHITE;
                break;
            case ANSI_WHITE_BACKGROUND:
                k=Color.GREY;
                break;
            case ANSI_YELLOW_BACKGROUND:
                k=Color.rgb(201, 145, 32);
                break;
            case ANSI_CYAN_BACKGROUND:
                k=Color.CYAN;
                break;
        }
        Text ka=new Text(c.getNome());
        ka.setTextAlignment(TextAlignment.CENTER);
        super.getChildren().add(ka);
        StackPane.setAlignment(ka, Pos.TOP_CENTER);
        if(c.podeseComprar()){
            ka.setText(ka.getText()+"\n"+String.valueOf(c.getValor()));
        }
        ka.setFont(Font.font("Verdana", FontWeight.BOLD,12));

        this.setBackground(new Background(new BackgroundFill(k, CornerRadii.EMPTY, Insets.EMPTY)));

        switch (c.getTipoCasilla()){
            case IMPOSTO:
            case SORTE:
            case COMUNIDADE:
                ka.setFill(Color.rgb(255, 94, 0));
                break;
            case CARCERE:
            case IRCARCERE:
                StackPane.setAlignment(ka, Pos.CENTER);
                ka.setFill(Color.GREEN);
                ka.setRotate(45);
                break;
            case SAIDA:
            case PARKING:
                StackPane.setAlignment(ka, Pos.CENTER);
                ka.setFill(Color.GREEN);
                ka.setRotate(-45);
                break;
            /*case IRCARCERE:
                StackPane.setAlignment(ka, Pos.CENTER);
                ka.setRotate(45);
                break;
            case PARKING:
                StackPane.setAlignment(ka, Pos.CENTER);
                ka.setRotate(-45);
                break;
            */
        }
    }

}
