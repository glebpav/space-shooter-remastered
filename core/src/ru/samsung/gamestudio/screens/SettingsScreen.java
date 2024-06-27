package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.ui.settings.SettingsUi;

import java.util.ArrayList;

public class SettingsScreen extends BaseScreen{

    SettingsUi ui;

    public SettingsScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        ui = new SettingsUi(myGdxGame.skin);
        stage.addActor(ui.root);

        ui.soundLabel.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
        ui.musicLabel.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));


        ui.returnButton.addListener(onButtonReturnClickedListener);
        ui.soundLabel.addListener(onSoundLabelClickedListener);
        ui.musicLabel.addListener(onMusicLabelClickedListener);
        ui.recordsLabel.addListener(onRecordsLabelClickedListener);

    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
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
            MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
            ui.soundLabel.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
            myGdxGame.audioManager.updateSoundFlag();
        }
    };

    ClickListener onMusicLabelClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
            ui.musicLabel.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
            myGdxGame.audioManager.updateMusicFlag();
        }
    };

    ClickListener onRecordsLabelClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            MemoryManager.saveTableOfRecords(new ArrayList<>());
            ui.recordsLabel.setText("clear records (cleared)");
        }
    };

}
