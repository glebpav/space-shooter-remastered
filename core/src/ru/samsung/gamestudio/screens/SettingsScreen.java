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
        ui.soundLabel.addListener(onSoundLabelClickedListener);
        ui.musicLabel.addListener(onMusicLabelClickedListener);
        ui.recordsLabel.addListener(onRecordsLabelClickedListener);

    }

    ClickListener onButtonReturnClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    ClickListener onSoundLabelClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("on sound clicked");
        }
    };

    ClickListener onMusicLabelClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("on music clicked");
        }
    };

    ClickListener onRecordsLabelClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("on records clicked");
        }
    };

}
