//package ru.mavr;
//
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.math.Vector3;
//
//public class InputManager extends InputAdapter {
//
//    OrthographicCamera camera;
//    static Vector3 temp = new Vector3();
//
//    public InputManager(OrthographicCamera camera) {
//        this.camera = camera;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//
//        temp.set(screenX, screenY, 0);
//        camera.unproject(temp);
//        float touchX = temp.x;
//        float touchY = temp.y;
////        System.out.println("Touch");
////        System.out.println(touchX);
////        System.out.println(touchY);
//        return false;
//
//    }
//
//}