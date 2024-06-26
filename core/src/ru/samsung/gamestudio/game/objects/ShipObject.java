package ru.samsung.gamestudio.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;

public class ShipObject extends GameObject{

    long lastShotTime;
    int livesLeft;

    public ShipObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, GameSettings.SHIP_BIT, world);
        body.setLinearDamping(10);
        livesLeft = 3;
    }

    public int getLiveLeft() {
        return livesLeft;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        putInFrame();
        super.draw(batch, parentAlpha);
    }

    public void move(Vector3 vector3) {
        body.applyForceToCenter(new Vector2(
                        (vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                        (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO),
                true
        );
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - getHeight() / 2f)) {
            setY((int) (GameSettings.SCREEN_HEIGHT / 2f - getHeight() / 2f));
        }
        if (getY() <= (getHeight() / 2f)) {
            setY(getHeight() / 2);
        }
        if (getX() < (-getHeight() / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + getHeight() / 2f)) {
            setX(0);
        }
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }


}
