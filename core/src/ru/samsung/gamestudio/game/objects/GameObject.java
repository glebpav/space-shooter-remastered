package ru.samsung.gamestudio.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static ru.samsung.gamestudio.GameSettings.SCALE;

public abstract class GameObject extends Actor {

    public Body body;
    Image image;

    GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world) {
        setBounds(x, y, width, height);

        image = new Image(new Texture(texturePath));
        body = createBody(x, y, cBits, world);
    }

    @Override
    public float getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    @Override
    public float getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    private Body createBody(float x, float y, short cBits, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(getWidth(), getHeight()) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = cBits;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circleShape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        image.draw(batch, parentAlpha);
    }

    public abstract void hit();
}
