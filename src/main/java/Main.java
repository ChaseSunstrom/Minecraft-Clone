import game.*;
import game.ecs.component.Components;
import game.ecs.component.MaterialManager;
import game.ecs.component.MeshManager;
import game.events.Event;
import game.events.EventSubscription;
import game.events.SubscriptionTopic;
import game.util.*;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) {

        ThreadPool.initialize(10);

        EventSubscription.Subscription.create(SubscriptionTopic.WINDOW_EVENT_TOPIC, Main::onEvent);

        Game game = new Game();

        setupMesh();
        setupMaterial();
        //createEntity();

        game.run();

        // Shut down the thread pool to clean up resources
        ThreadPool.shutdown();
    }

    public static void setupMesh() {
        MeshManager meshManager = Game.m_MeshManager;

        ArrayList<MathData.Vertex> vertices = new ArrayList<>();

        vertices.add(new MathData.Vertex(new MathData.Vec3(-0.5f, -0.5f, 0.0f), new MathData.Vec3(0.0f, 0.0f, 1.0f), new MathData.Vec2(0.0f, 0.0f)));
        vertices.add(new MathData.Vertex(new MathData.Vec3(0.5f, -0.5f, 0.0f), new MathData.Vec3(0.0f, 0.0f, 1.0f), new MathData.Vec2(1.0f, 0.0f)));
        vertices.add(new MathData.Vertex(new MathData.Vec3(0.5f, 0.5f, 0.0f), new MathData.Vec3(0.0f, 0.0f, 1.0f), new MathData.Vec2(1.0f, 1.0f)));
        vertices.add(new MathData.Vertex(new MathData.Vec3(-0.5f, 0.5f, 0.0f), new MathData.Vec3(0.0f, 0.0f, 1.0f), new MathData.Vec2(0.0f, 1.0f)));

        meshManager.createMesh("cube", vertices);
    }

    public static void setupMaterial() {
        MaterialManager materialManager = Game.m_MaterialManager;
        materialManager.createMaterial("default", "shaders/default_vert.vert", "shaders/default_frag.frag");
    }

    public static void createEntity() {
        Game.m_ECS.createEntity(
                new Components.MeshComponent("cube"),
                new Components.MaterialComponent("default"),
                new Components.TransformComponent(new MathData.Vec3(0.0f, 0.0f, 0.0f), new MathData.Vec3(0.0f, 0.0f, 0.0f), new MathData.Vec3(1.0f, 1.0f, 1.0f)));
    }

    public static void onEvent(Event event) {
        System.out.println(event.getType().toString());
    }
}
