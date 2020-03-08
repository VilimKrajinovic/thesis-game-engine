package engineTester;

import entities.Entity;
import model.Model;
import model.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Main {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

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

        Entity entity = new Entity(texturedModel,new Vector3f(0.5f,0,-1), 0,0,45,0.5f);

        while (!Display.isCloseRequested()){
            entity.increaseRotation(0.1f,0.1f,0.1f);
            renderer.prepare();
            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}
