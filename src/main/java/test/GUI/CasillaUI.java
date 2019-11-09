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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import monopolinho.obxetos.Casilla;

public class CasillaUI extends StackPane {

    public CasillaUI(Casilla c) {
        Color k=Color.AZURE;
        switch (c.getColorCasilla()){
            case ANSI_BLUE_BACKGROUND:
                k=Color.BLUE;
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
                k=Color.PINK;
                break;
            case ANSI_CYAN_BACKGROUND:
                k=Color.CYAN;
                break;
        }
        Text ka=new Text(c.getNome());
        ka.setTextAlignment(TextAlignment.CENTER);
        super.getChildren().add(ka);
        StackPane.setAlignment(ka, Pos.CENTER);
        if(c.podeseComprar()){
            ka.setText(ka.getText()+"\n"+String.valueOf(c.getValor()));
        }
        this.setBackground(new Background(new BackgroundFill(k, CornerRadii.EMPTY, Insets.EMPTY)));
        switch (c.getTipoCasilla()){
            case CARCEL:
                ka.setRotate(45);
                break;
            case SALIDA:
                ka.setRotate(-45);
                break;
            case IRCARCEL:
                ka.setRotate(45);
                break;
            case PARKING:
                ka.setRotate(-45);
                break;
        }
    }

}
