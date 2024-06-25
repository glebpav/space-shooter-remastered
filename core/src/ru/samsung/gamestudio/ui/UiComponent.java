package ru.samsung.gamestudio.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class UiComponent {

    public Table root;

    UiComponent() {
        root = new Table();
        root.setFillParent(true);
    }

}
