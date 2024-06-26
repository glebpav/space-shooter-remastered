package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.game.objects.ShipObject;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.ui.GameUi;

public class GameScreen extends BaseScreen {

    GameUi ui;
    GameSession session;

    ShipObject shipObject;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        session = new GameSession();

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

        if (session.state == GameState.PLAYING) {
            if (session.shouldSpawnTrash()) {
                /*TrashObject trashObject = new TrashObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.TRASH_IMG_PATH,
                        myGdxGame.world
                );
                trashArray.add(trashObject);*/
            }

            /*if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }*/

            if (!shipObject.isAlive()) {
                session.endGame();
                // recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            // updateTrash();
            // updateBullets();
            // backgroundView.move();
            session.updateScore();
            ui.scoreLabel.setText("Score: " + session.getScore());
            ui.liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
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
