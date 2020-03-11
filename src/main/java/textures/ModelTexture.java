package textures;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelTexture {
    private int textureId;

    public ModelTexture(int textureId) {
        this.textureId = textureId;
    }

    private float shineDamper = 1;
    private float reflectivity = 0;
}
