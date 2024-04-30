package game.window;

import game.util.MathData;

public class Camera {

    private float m_FOV;
    private float m_AspectRatio;
    private float m_Near;
    private float m_Far;
    private MathData.Vec3 m_Position;
    private MathData.Vec3 m_Rotation;

    public Camera(float fov, float aspectRatio, float near, float far) {
        m_FOV = fov;
        m_AspectRatio = aspectRatio;
        m_Near = near;
        m_Far = far;
        m_Position = new MathData.Vec3(0.0f, 0.0f, 0.0f);
        m_Rotation = new MathData.Vec3(0.0f, 0.0f, 0.0f);
    }

    public float[] getViewMatrix() {
        MathData.Mat4 view = new MathData.Mat4();
        view.rotate(m_Rotation);
        view.translate(new MathData.Vec3(-m_Position.m_X, -m_Position.m_Y, -m_Position.m_Z));
        return view.getArray();
    }

    public float[] getProjectionMatrix() {
        return MathData.Mat4.perspective(m_FOV, m_AspectRatio, m_Near, m_Far).getArray();
    }

    public float getFOV() {
        return m_FOV;
    }

    public float getAspectRatio() {
        return m_AspectRatio;
    }

    public float getNear() {
        return m_Near;
    }

    public float getFar() {
        return m_Far;
    }

    public MathData.Vec3 getPosition() {
        return m_Position;
    }

    public MathData.Vec3 getRotation() {
        return m_Rotation;
    }

    public void setFOV(float fov) {
        m_FOV = fov;
    }

    public void setAspectRatio(float aspectRatio) {
        m_AspectRatio = aspectRatio;
    }

    public void setNear(float near) {
        m_Near = near;
    }

    public void setFar(float far) {
        m_Far = far;
    }

    public void setPosition(MathData.Vec3 position) {
        m_Position = position;
    }

    public void setRotation(MathData.Vec3 rotation) {
        m_Rotation = rotation;
    }
}
