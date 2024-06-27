package ru.samsung.gamestudio.ui.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ru.samsung.gamestudio.ui.UiComponent;

import java.util.ArrayList;

public class EndGameUi extends UiComponent {

    public TextButton homeButton;
    List<String> listView;
    ScrollPane scrollPane;
    Skin skin;

    public EndGameUi(Skin skin) {
        this.skin = skin;

        homeButton = new TextButton("Home", skin);
        listView = new List<>(skin);
        scrollPane = new ScrollPane(listView, skin);

        Image image = new Image(new Texture("textures/blackout_full.png"));
        Label label = new Label("Your records", skin);
        label.setFontScale(2.5f);

        root.setBackground(image.getDrawable());
        root.add(label);
        root.row();
        root.add(scrollPane).padTop(100).width(300).height(300);
        root.row();
        root.add(homeButton).width(160).height(70).padTop(100);

    }

    public void setItems(ArrayList<Integer> recordsList) {
        String[] labelsList = new String[recordsList.size()];
        for (int i = 0; i < recordsList.size(); i++) {
            labelsList[i] = (i+1) + " - " + recordsList.get(i);
        }
        listView.setItems(labelsList);
        listView.setAlignment(Align.center);
        scrollPane.setActor(listView);
        scrollPane.setSize(300, 300);
    }

}
