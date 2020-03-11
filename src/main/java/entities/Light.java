package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
@AllArgsConstructor
public class Light {
    private Vector3f position;
    private Vector3f color;
}
