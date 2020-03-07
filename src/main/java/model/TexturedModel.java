package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import textures.ModelTexture;

@Data
@AllArgsConstructor
public class TexturedModel {
    private Model model;
    private ModelTexture texture;
}
