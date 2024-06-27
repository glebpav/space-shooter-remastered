package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.w3c.dom.ls.LSOutput;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.game.objects.BulletObject;
import ru.samsung.gamestudio.game.objects.ShipObject;
import ru.samsung.gamestudio.game.objects.TrashObject;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.ui.GameUi;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {

    GameUi ui;
    GameSession session;
    ContactManager contactManager;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;

    ShipObject shipObject;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        session = new GameSession();
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        contactManager = new ContactManager(myGdxGame.world);

        shipObject = new ShipObject(
                GameResources.SHIP_IMG_PATH,
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                myGdxGame.world
        );

        ui = new GameUi(myGdxGame.skin);
        stage.addActor(ui.root);

        ui.gameLayer.addActor(shipObject);
    }

    @Override
    public void show() {
        super.show();
        session.startGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // handleInput();
        System.out.println(trashArray.size());

        if (session.state == GameState.PLAYING) {
            if (session.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.TRASH_IMG_PATH,
                        myGdxGame.world
                );
                trashArray.add(trashObject);
                ui.gameLayer.addActor(trashObject);
            }

            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        (int) shipObject.getX(), (int) (shipObject.getY() + shipObject.getHeight() / 2),
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                ui.gameLayer.addActor(laserBullet);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }

            if (!shipObject.isAlive()) {
                session.endGame();
                // recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            updateTrash();
            updateBullets();
            // backgroundView.move();
            session.updateScore();
            ui.scoreLabel.setText("Score: " + session.getScore());
            ui.liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
        }
    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {

            System.out.println("trashArray(" + i + "): " + trashArray.get(i).getY() + ", " + trashArray.get(i).getX());

            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive()) {
                session.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                ui.gameLayer.removeActor(trashArray.get(i));
                trashArray.remove(i--);
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                ui.gameLayer.removeActor(bulletArray.get(i));
                bulletArray.remove(i--);
            }
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(touch);
        }
    }
}
