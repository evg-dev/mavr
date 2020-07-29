package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsScreen extends MenuScreen {

    private Stage stage;
    private Dialog dialog;
    public MavrGame game;
    private Camera cam;
    private SpriteBatch spriteBatch;
    private Vector3 touchPos;
    private RenderText selectBox;

    public SettingsScreen(MavrGame game) {
        super(game);
        this.game = game;
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();

        this.spriteBatch = new SpriteBatch();
        this.touchPos = new Vector3();
        this.cam = new OrthographicCamera();

        font = new BitmapFont();

        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.getFont("font").getData().setScale(1.5f);

        dialog = new Dialog("Setting", skin);
        dialog.setSize(200, 200);
        dialog.setPosition(0, 0);

        final SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("2", "3", "4");
        selectBox.setSelected("4");
//        selectBox.getSelected()

        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(selectBox);

        stage.addActor(dialog);
//        stage.getActor()
        stage.addListener(new ClickListener() {

            @Override
            public boolean keyUp(InputEvent event, int keycode) {

                if (keycode == Input.Keys.BACK) {
                    set();
                }
                return false;
            }
        });
    }

    void set() {
        game.setScreen(game.menuScreen);
        System.out.println("Click");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {
        this.cam.viewportWidth = width;
        this.cam.viewportHeight = height;
        this.cam.update();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
//		atlas.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        stage.act();
        stage.draw();
        spriteBatch.end();
    }

}
