package ru.samsung.gamestudio.screens;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.ui.GameUi;

public class GameScreen extends BaseScreen {

    GameUi ui;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        ui = new GameUi(myGdxGame.skin);
        stage.addActor(ui.root);
    }

}
