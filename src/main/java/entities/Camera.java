package entities;

import lombok.Data;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Camera {

    private static final float SPEED = 0.2f;

    private Vector3f position = new Vector3f(0, 1, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public void move() {

    }
}
