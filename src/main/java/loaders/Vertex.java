package loaders;

import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Vertex {
    private static final int NO_INDEX = -1;

    private Vector3f position;
    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private Vertex duplicateVertex = null;
    private int index;
    private float length;

    public Vertex(int index, Vector3f position) {
        this.index = index;
        this.position = position;
        this.length = position.length();
    }

    public boolean isSet() {
        return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
    }

    public boolean hasSameTextureAndNormal(int otherTextureIndex, int otherNormalIndex) {
        return otherTextureIndex == textureIndex && otherNormalIndex == normalIndex;
    }
}
