package entities;

import lombok.Data;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Camera {

    private static final float SPEED = 0.02f;

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=SPEED;
        }
    }
}
