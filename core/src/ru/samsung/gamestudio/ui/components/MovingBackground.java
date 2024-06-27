package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ru.samsung.gamestudio.GameSettings;

public class MovingBackground extends Actor {

    static final float BACKGROUND_SPEED = -200;
    Image image;
    Boolean isActive;
    float baseY;
    float baseY2;

    public MovingBackground(String pathToImage) {
        image = new Image(new Texture(pathToImage));
        image.setSize(image.getWidth() * 1.01f, image.getHeight() * 1.01f);
        baseY = 0;
        baseY2 = GameSettings.SCREEN_HEIGHT;
        isActive = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.setY(baseY);
        image.draw(batch, parentAlpha);
        image.setY(baseY2);
        image.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        if (isActive) {
            baseY = computeY(baseY, delta);
            baseY2 = computeY(baseY2, delta);
        }
    }

    public void disable() {
        isActive = false;
    }

    public void activate() {
        isActive = true;
    }

    private float computeY(float y, float delta) {
        return y >= -GameSettings.SCREEN_HEIGHT ? y + BACKGROUND_SPEED * delta : GameSettings.SCREEN_HEIGHT;
    }

}
