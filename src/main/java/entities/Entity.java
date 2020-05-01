package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

@Data
@AllArgsConstructor
public class Entity {
    private TexturedModel texturedModel;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private boolean shouldRender = true;

    public Entity(TexturedModel texturedModel, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.texturedModel = texturedModel;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }
}
