package ru.mavr;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputManager extends InputAdapter {
    public MavrGame game;

    InputManager() {

    }

    /*@Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.BACK){
            System.out.println("Back press");
            game.setScreen(game.menuScreen);
        }
        return false;
    }*/
}