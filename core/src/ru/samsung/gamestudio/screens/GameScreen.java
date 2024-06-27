package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.game.objects.BulletObject;
import ru.samsung.gamestudio.game.objects.GameObject;
import ru.samsung.gamestudio.game.objects.ShipObject;
import ru.samsung.gamestudio.game.objects.TrashObject;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.ui.game.GameUi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

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

        ui.pauseButton.addListener(onButtonPauseClickedListener);
        ui.pauseUi.homeButton.addListener(onButtonHomeClickedListener);
        ui.pauseUi.resumeButton.addListener(onButtonResumeClickedListener);
        ui.endGameUi.homeButton.addListener(onButtonHome2ClickedListener);
        ui.gameLayer.addActor(shipObject);
    }

    @Override
    public void show() {
        super.show();
        restartGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

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
                ui.movingBackground.disable();
                ui.alertLayer.addActor(ui.endGameUi.root);
                ui.movingBackground.disable();
                ui.endGameUi.setItems(MemoryManager.loadRecordsTable());
                stage.addActor(ui.alertLayer);
            }

            updateTrash();
            updateBullets();
            session.updateScore();
            ui.scoreLabel.setText("Score: " + session.getScore());
            ui.liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
        }
    }

    private void updateTrash() {
        trashArray = trashArray.stream().filter(trashObject -> {
            if (!trashObject.isAlive()) {
                session.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.2f);
            }
            return deletePhysicObjectWrapper(trashObject);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private void updateBullets() {
        bulletArray = bulletArray
                .stream()
                .filter(this::deletePhysicObjectWrapper)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean deletePhysicObjectWrapper(GameObject gameObject) {
        if (gameObject.hasToBeDestroyed()) {
            myGdxGame.world.destroyBody(gameObject.body);
            ui.gameLayer.removeActor(gameObject);
            gameObject.remove();
            return false;
        }
        return true;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(touch);
        }
    }

    private void restartGame() {

        trashArray.forEach(it -> {
            myGdxGame.world.destroyBody(it.body);
            it.remove();
        });
        trashArray.clear();

        bulletArray.forEach(it -> {
            myGdxGame.world.destroyBody(it.body);
            it.remove();
        });
        bulletArray.clear();

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(
                GameResources.SHIP_IMG_PATH,
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                myGdxGame.world
        );

        session.startGame();
    }

    ClickListener onButtonPauseClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            stage.addActor(ui.alertLayer);
            ui.alertLayer.addActor(ui.pauseUi.root);
            ui.movingBackground.disable();
            session.pauseGame();
        }
    };

    ClickListener onButtonHomeClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ui.alertLayer.removeActor(ui.pauseUi.root);
            ui.alertLayer.remove();
            ui.movingBackground.activate();
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    ClickListener onButtonResumeClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ui.alertLayer.removeActor(ui.pauseUi.root);
            ui.alertLayer.remove();
            ui.movingBackground.activate();
            session.resumeGame();
        }
    };

    ClickListener onButtonHome2ClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ui.alertLayer.removeActor(ui.endGameUi.root);
            ui.alertLayer.remove();
            ui.movingBackground.activate();
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };
}
