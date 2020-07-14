package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends ScreenAdapter {

	private Camera cam;
	private SpriteBatch spriteBatch;
	private Vector3 touchPos;


	public MenuScreen() {

	}

//	@Override
//	public void resize(int width, int height) {
//		if (width > height) {
//			cam.viewportHeight = GameScreen.MINIMUM_VIEWPORT_SIZE;
//			cam.viewportWidth = cam.viewportHeight * (float) width / (float) height;
//		} else {
//			cam.viewportWidth = GameScreen.MINIMUM_VIEWPORT_SIZE;
//			cam.viewportHeight = cam.viewportWidth * (float) height / (float) width;
//		}
//		cam.update();
//	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		touchPos = new Vector3();
		cam = new OrthographicCamera();

		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchUp(int x, int y, int pointer, int button) {
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos); // calibrates the input to your camera's dimentions
				return false;
			}
		});
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
//		atlas.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		spriteBatch.end();
	}
}
