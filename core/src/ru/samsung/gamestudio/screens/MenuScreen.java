package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.ui.MenuUI;

public class MenuScreen extends BaseScreen{

    MenuUI ui;

    public MenuScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        ui = new MenuUI(myGdxGame.skin);
        stage.addActor(ui.root);

        ui.exitButton.addListener(exitButtonClickedListener);
        ui.startButton.addListener(startButtonClickedListener);
        ui.settingsButton.addListener(settingsButtonClickedListener);

    }

    ClickListener exitButtonClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    };

    ClickListener settingsButtonClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.settingsScreen);
        }
    };

    ClickListener startButtonClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    };

}
