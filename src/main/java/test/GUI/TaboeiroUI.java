package test.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Taboeiro;
import monopolinho.tipos.Zona;


public class TaboeiroUI extends GridPane {
    public TaboeiroUI(Taboeiro t){
        ColumnConstraints column = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        column.setPercentWidth(100/11.0f);
        row.setPercentHeight(100/11.0f);
        Image image = new Image("Laranjo.png");
        super.add(new ImageView(image),1,5);

        for(int i=0;i<11;i++)super.getColumnConstraints().add(column);
        for(int i=0;i<11;i++)super.getRowConstraints().add(row);
        for(int i=0;i<11;i++){
            Casilla c=t.getCasillas(Zona.NORTE).get(i);
            super.add(new CasillaUI(c),i,0);
        }
        for(int i=0;i<9;i++){
            Casilla cE=t.getCasillas(Zona.ESTE).get(i);
            Casilla cO=t.getCasillas(Zona.OESTE).get(8-i);
            super.add(new CasillaUI(cE),10,i+1);
            super.add(new CasillaUI(cO),0,i+1);
        }
        for(int i=0;i<11;i++){
            Casilla c=t.getCasillas(Zona.SUR).get(10-i);
            super.add(new CasillaUI(c),i,10);
        }
    }
}
