package engineTester;

import model.Model;
import model.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Main {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoordinates = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        Model model = loader.loadToVertexArrayObject(vertices, textureCoordinates, indices);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("texture")));

        while (!Display.isCloseRequested()){
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}
