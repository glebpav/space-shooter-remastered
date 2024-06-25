package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class SettingsUi extends UiComponent {

    public Label musicLabel;
    public Label soundLabel;
    public Label recordsLabel;
    public Button returnButton;

    public SettingsUi(Skin skin) {

        Label titleLabel = new Label("Settings", skin);
        titleLabel.setFontScale(2.5f);
        Image backgroundImage = new Image(new Texture("textures/background.png"));
        Image blackoutImage = new Image(new Texture("textures/blackout_middle.png"));
        Table table = new Table();

        musicLabel = new Label("music: ", skin);
        soundLabel = new Label("sound: ", skin);
        recordsLabel = new Label("clear records", skin);
        returnButton = new TextButton("return", skin);

        musicLabel.setAlignment(Align.left);
        soundLabel.setAlignment(Align.left);
        recordsLabel.setAlignment(Align.left);

        table.setBackground(blackoutImage.getDrawable());
        table.add(musicLabel).width(300);
        table.row();
        table.add(soundLabel).width(300);
        table.row();
        table.add(recordsLabel).width(300);
        table.row();
        table.row();
        table.add(returnButton).width(150).height(50).padTop(50);

        root.setBackground(backgroundImage.getDrawable());
        root.add(titleLabel).height(150);
        root.row();
        root.add(table);

    }

}
