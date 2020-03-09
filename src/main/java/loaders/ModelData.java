package loaders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModelData {
    private float[] vertices;
    private float[] textureCoordinates;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;
}
