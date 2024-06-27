package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class UiComponent {

    public Table root;

    public UiComponent() {
        root = new Table();
        root.setFillParent(true);
    }

    public void hide() {
        root.setVisible(false);
    }

    public void release() {
        root.setVisible(true);
    }

}
