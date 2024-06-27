package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.ui.components.LiveView;
import ru.samsung.gamestudio.ui.components.MovingBackground;

public class GameUi extends UiComponent{

    public Group alertLayer;
    public Group hudLayer;
    public Group bottomLayer;
    public Group gameLayer;

    public PauseUi pauseUi;

    public LiveView liveView;
    public Label scoreLabel;
    public Image pauseButton;
    public MovingBackground movingBackground;

    public GameUi(Skin skin) {
        alertLayer = new Group();
        bottomLayer = new Group();
        hudLayer = new Group();
        gameLayer = new Group();

        alertLayer.setWidth(GameSettings.SCREEN_WIDTH);
        alertLayer.setHeight(GameSettings.SCREEN_HEIGHT);

        pauseUi = new PauseUi(skin);

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

        movingBackground = new MovingBackground("textures/background.png");
        // backgroundImage.setPosition(0, 0);
        bottomLayer.addActor(movingBackground);

        // alertLayer.addActor(pauseUi.root);

        root.addActor(bottomLayer);
        root.addActor(gameLayer);
        root.addActor(hudLayer);
        // root.addActor(alertLayer);

    }

}
