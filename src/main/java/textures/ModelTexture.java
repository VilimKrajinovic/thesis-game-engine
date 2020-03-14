package textures;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelTexture {
    private int textureId;
    private float shineDamper = 1;
    private float reflectivity = 0;
    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int textureId) {
        this.textureId = textureId;
    }

}
