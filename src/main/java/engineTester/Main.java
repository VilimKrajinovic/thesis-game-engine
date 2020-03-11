package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import loaders.ModelData;
import loaders.OBJLoader;
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

        Camera camera = new Camera();

        Light light = new Light(new Vector3f(0, 0, 3), new Vector3f(1, 1, 1));

        ModelData modelData = OBJLoader.load("girl");
        Model model = loader.loadToVertexArrayObject(modelData.getVertices(), modelData.getTextureCoordinates(),
                modelData.getNormals(), modelData.getIndices());
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = texturedModel.getTexture();
        texture.setReflectivity(1f);
        texture.setShineDamper(10f);

        Entity entity = new Entity(texturedModel, new Vector3f(0, -1, -7), 0, 0, 0, 1);

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}
