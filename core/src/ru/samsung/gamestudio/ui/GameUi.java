package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.samsung.gamestudio.ui.components.LiveView;

public class GameUi extends UiComponent{

    public Group alertLayer;
    public Group hudLayer;
    public Group bottomLayer;
    public Group gameLayer;

    public LiveView liveView;
    public Label scoreLabel;
    public Image pauseButton;

    public GameUi(Skin skin) {
        alertLayer = new Group();
        bottomLayer = new Group();
        hudLayer = new Group();
        gameLayer = new Group();

        Image hudBackgroundImage = new Image(new Texture("textures/blackout_top.png"));
        liveView = new LiveView();
        pauseButton = new Image(new Texture("textures/pause_icon.png"));
        scoreLabel = new Label("Score: ", skin);
        liveView.setLeftLives(3);

        hudLayer.addActor(hudBackgroundImage);
        hudLayer.addActor(pauseButton);
        hudLayer.addActor(scoreLabel);
        hudLayer.addActor(liveView);

        hudLayer.setPosition(0, 1180);
        pauseButton.setPosition(606, 23);
        pauseButton.setSize(46, 54);
        scoreLabel.setPosition(50, 35);
        liveView.setPosition(305, 35);
        hudBackgroundImage.setPosition(0, 0);

        Image backgroundImage = new Image(new Texture("textures/background.png"));
        backgroundImage.setPosition(0, 0);
        bottomLayer.addActor(backgroundImage);

        root.addActor(bottomLayer);
        root.addActor(gameLayer);
        root.addActor(hudLayer);
        root.addActor(alertLayer);
    }

}
