package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import loaders.ModelData;
import loaders.OBJLoader;
import model.Model;
import model.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;

public class Main {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

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

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            renderer.processEntity(entity);
            renderer.render(light, camera);
            camera.move();
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}
