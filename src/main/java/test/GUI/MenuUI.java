package test.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import monopolinho.obxetos.Taboeiro;

public class MenuUI extends SplitPane {
    public MenuUI(){
        super();
        Taboeiro t=new Taboeiro();
        TaboeiroUI tUI=new TaboeiroUI(t);

        tUI.resize(900,900);
        super.setPrefSize(1080,900);
        super.getChildren().add(tUI);


    }
}
