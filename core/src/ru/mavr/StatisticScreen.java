package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class StatisticScreen {

    private final int width;
    private final int height;
    private Stage stage;
    private Dialog dialog;
    public MavrGame game;
    private Camera cam;
    private SpriteBatch spriteBatch;
    private Vector3 touchPos;
    private RenderText selectBox;

    public StatisticScreen(MavrGame game) {
        this.game = game;
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();

        this.spriteBatch = new SpriteBatch();
        this.touchPos = new Vector3();
        this.cam = new OrthographicCamera();

        BitmapFont font = new BitmapFont();

        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.getFont("font").getData().setScale(1.5f);

        dialog = new Dialog("Loading", skin);

        dialog.setSize(500, 500);
        dialog.setPosition(400, 1000);

//        final SelectBox<String> selectBox = new SelectBox<String>(skin);
//        selectBox.setItems("2", "3", "4");
//        int item = this.game.prefs.getInteger("playersCount");
//        selectBox.setSelected(String.valueOf(item));
//        selectBox.getStyle().listStyle.font.getData().scale(2);


//        dialog.getContentTable().defaults().pad(20);
//        dialog.getContentTable().add(selectBox).width(100);
        ;
        stage.addActor(dialog);

        stage.addListener(new ClickListener() {

//            @Override
//            public boolean keyUp(InputEvent event, int keycode) {

//                 TODO: alert screen
//                if (keycode == Input.Keys.BACK) {
            // Save settings
//                    int selected = Integer.parseInt(selectBox.getSelected());
//                    setMenuScreen(selected);
//                }
//                return false;
//            }

//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println(selectBox.getSelection());
//                System.out.println(event.getRelatedActor());

//            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {
        this.cam.viewportWidth = width;
        this.cam.viewportHeight = height;
        this.cam.update();
    }

    public void dispose() {
        spriteBatch.dispose();
//		atlas.dispose();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        stage.act();
        stage.draw();
        spriteBatch.end();
    }
}