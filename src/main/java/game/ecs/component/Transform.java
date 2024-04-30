package game.ecs.component;

import game.util.MathData;

public class Transform {

    public MathData.Mat4 m_Transform;

    public Transform() {
        m_Transform = new MathData.Mat4();
    }

    public Transform(MathData.Vec3 position) {
        m_Transform = new MathData.Mat4();
        m_Transform.translate(position);
    }

    public Transform(MathData.Vec3 position, MathData.Vec3 rotation) {
        m_Transform = new MathData.Mat4();
        m_Transform.translate(position);
        m_Transform.rotate(rotation);
    }

    public Transform(MathData.Vec3 position, MathData.Vec3 rotation, MathData.Vec3 scale) {
        m_Transform = new MathData.Mat4();
        m_Transform.translate(position);
        m_Transform.rotate(rotation);
        m_Transform.scale(scale);
    }

    public Transform(MathData.Mat4 transform) {
        m_Transform = transform;
    }

    public void setTransform(MathData.Mat4 transform) {
        m_Transform = transform;
    }

    public MathData.Mat4 getTransform() {
        return m_Transform;
    }

    public float[] getTransformArray() {
        return m_Transform.getArray();
    }
}
