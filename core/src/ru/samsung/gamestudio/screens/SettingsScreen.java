package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.ui.SettingsUi;

public class SettingsScreen extends BaseScreen{

    SettingsUi ui;

    public SettingsScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        ui = new SettingsUi(myGdxGame.skin);
        stage.addActor(ui.root);

        ui.returnButton.addListener(onButtonReturnClickedListener);

    }

    ClickListener onButtonReturnClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

}
