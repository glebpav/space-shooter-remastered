package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseUi extends UiComponent {

    public TextButton homeButton;
    public TextButton resumeButton;

    PauseUi(Skin skin) {
        Image blackout = new Image(new Texture("textures/blackout_full.png"));
        Label label = new Label("Pause", skin);
        homeButton = new TextButton("Home", skin);
        resumeButton = new TextButton("Continue", skin);

        label.setFontScale(2.5f);

        root.setBackground(blackout.getDrawable());
        root.columnDefaults(2);
        root.add(label).colspan(2).height(150);
        root.row();
        root.add(homeButton).width(150).height(80).padRight(20);
        root.add(resumeButton).width(150).height(80).padLeft(20);
    }

}
