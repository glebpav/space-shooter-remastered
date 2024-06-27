package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LiveView extends Actor {

    static final int LIVE_PADDING = 6;

    Image liveImage;
    private int leftLives;

    int baseX;

    public LiveView() {
        liveImage = new Image(new Texture("textures/life.png"));
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void setPosition(float x, float y) {;
        baseX = (int) x;
        setY(y);
        this.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (leftLives > 0) {
            liveImage.setPosition(baseX + (liveImage.getImageWidth() + LIVE_PADDING), getY());
            liveImage.draw(batch, parentAlpha);
        }
        if (leftLives > 1) {
            liveImage.setPosition(baseX, getY());
            liveImage.draw(batch, parentAlpha);
        }
        if (leftLives > 2) {
            liveImage.setPosition(baseX + 2 * (liveImage.getWidth() + LIVE_PADDING), getY());
            liveImage.draw(batch, parentAlpha);
        }
    }

}
