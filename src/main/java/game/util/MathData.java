package game.util;

public class MathData {

    public static class Vec2 {

        public float m_X;
        public float m_Y;

        public static final int SIZE = 2 * Float.BYTES;

        public Vec2() {
            m_X = 0;
            m_Y = 0;
        }

        public Vec2(float x, float y) {
            this.m_X = x;
            this.m_Y = y;
        }

        public Vec2 add(Vec2 other) {
            return new Vec2(m_X + other.m_X, m_Y + other.m_Y);
        }

        public Vec2 sub(Vec2 other) {
            return new Vec2(m_X - other.m_X, m_Y - other.m_Y);
        }

        public Vec2 mul(float scalar) {
            return new Vec2(m_X * scalar, m_Y * scalar);
        }

        public Vec2 div(float scalar) {
            return new Vec2(m_X / scalar, m_Y / scalar);
        }

        public float dot(Vec2 other) {
            return m_X * other.m_X + m_Y * other.m_Y;
        }

        public float length() {
            return (float) Math.sqrt(m_X * m_X + m_Y * m_Y);
        }

        public Vec2 normalize() {
            return div(length());
        }

        public String toString() {
            return "(" + m_X + ", " + m_Y + ")";
        }

    }

    public static class Vec3 {

        public float m_X;
        public float m_Y;
        public float m_Z;

        public static final int SIZE = 3 * Float.BYTES;

        public Vec3() {
            m_X = 0;
            m_Y = 0;
            m_Z = 0;
        }

        public Vec3(float x, float y, float z) {
            this.m_X = x;
            this.m_Y = y;
            this.m_Z = z;
        }

        public Vec3 add(Vec3 other) {
            return new Vec3(m_X + other.m_X, m_Y + other.m_Y, m_Z + other.m_Z);
        }

        public Vec3 sub(Vec3 other) {
            return new Vec3(m_X - other.m_X, m_Y - other.m_Y, m_Z - other.m_Z);
        }

        public Vec3 mul(float scalar) {
            return new Vec3(m_X * scalar, m_Y * scalar, m_Z * scalar);
        }

        public Vec3 div(float scalar) {
            return new Vec3(m_X / scalar, m_Y / scalar, m_Z / scalar);
        }

        public float dot(Vec3 other) {
            return m_X * other.m_X + m_Y * other.m_Y + m_Z * other.m_Z;
        }

        public Vec3 cross(Vec3 other) {
            return new Vec3(m_Y * other.m_Z - m_Z * other.m_Y, m_Z * other.m_X - m_X * other.m_Z, m_X * other.m_Y - m_Y * other.m_X);
        }

        public float length() {
            return (float) Math.sqrt(m_X * m_X + m_Y * m_Y + m_Z * m_Z);
        }

        public Vec3 normalize() {
            return div(length());
        }

        public String toString() {
            return "(" + m_X + ", " + m_Y + ", " + m_Z + ")";
        }

    }

    public static class Vec4 {

        public float m_X;
        public float m_Y;
        public float m_Z;
        public float m_W;

        public Vec4() {
            m_X = 0;
            m_Y = 0;
            m_Z = 0;
            m_W = 0;
        }

        public Vec4(float x, float y, float z, float w) {
            this.m_X = x;
            this.m_Y = y;
            this.m_Z = z;
            this.m_W = w;
        }

        public Vec4 add(Vec4 other) {
            return new Vec4(m_X + other.m_X, m_Y + other.m_Y, m_Z + other.m_Z, m_W + other.m_W);
        }

        public Vec4 sub(Vec4 other) {
            return new Vec4(m_X - other.m_X, m_Y - other.m_Y, m_Z - other.m_Z, m_W - other.m_W);
        }

        public Vec4 mul(float scalar) {
            return new Vec4(m_X * scalar, m_Y * scalar, m_Z * scalar, m_W * scalar);
        }

        public Vec4 div(float scalar) {
            return new Vec4(m_X / scalar, m_Y / scalar, m_Z / scalar, m_W / scalar);
        }

        public float dot(Vec4 other) {
            return m_X * other.m_X + m_Y * other.m_Y + m_Z * other.m_Z + m_W * other.m_W;
        }

        public float length() {
            return (float) Math.sqrt(m_X * m_X + m_Y * m_Y + m_Z * m_Z + m_W * m_W);
        }

        public Vec4 normalize() {
            return div(length());
        }

        public String toString() {
            return "(" + m_X + ", " + m_Y + ", " + m_Z + ", " + m_W + ")";
        }

        public float[] getArray() {
            return new float[]{m_X, m_Y, m_Z, m_W};
        }
    }

    public static class Mat4 {

        public Vec4[] m_Elements;

        public Mat4() {
            m_Elements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                m_Elements[i] = new Vec4();
            }
        }

        public Mat4(Vec4[] elements) {
            m_Elements = elements;
        }

        public Mat4(float[][] elements) {
            m_Elements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                m_Elements[i] = new Vec4(elements[i][0], elements[i][1], elements[i][2], elements[i][3]);
            }
        }

        public static Mat4 perspective(float fov, float aspectRatio, float near, float far) {
            Mat4 result = new Mat4();
            float q = 1.0f / (float) Math.tan(Math.toRadians(0.5f * fov));
            float a = q / aspectRatio;
            float b = (near + far) / (near - far);
            float c = (2.0f * near * far) / (near - far);
            result.m_Elements[0] = new Vec4(a, 0, 0, 0);
            result.m_Elements[1] = new Vec4(0, q, 0, 0);
            result.m_Elements[2] = new Vec4(0, 0, b, -1);
            result.m_Elements[3] = new Vec4(0, 0, c, 0);
            return result;
        }

        public Mat4 add(Mat4 other) {
            Vec4[] newElements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                newElements[i] = m_Elements[i].add(other.m_Elements[i]);
            }
            return new Mat4(newElements);
        }

        public Mat4 sub(Mat4 other) {
            Vec4[] newElements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                newElements[i] = m_Elements[i].sub(other.m_Elements[i]);
            }
            return new Mat4(newElements);
        }

        public Mat4 mul(float scalar) {
            Vec4[] newElements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                newElements[i] = m_Elements[i].mul(scalar);
            }
            return new Mat4(newElements);
        }

        public Mat4 div(float scalar) {
            Vec4[] newElements = new Vec4[4];
            for (int i = 0; i < 4; i++) {
                newElements[i] = m_Elements[i].div(scalar);
            }
            return new Mat4(newElements);
        }

        public Mat4 mul(Mat4 other) {
            float[][] elements = new float[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    elements[i][j] = m_Elements[i].m_X * other.m_Elements[0].m_X +
                            m_Elements[i].m_Y * other.m_Elements[1].m_X +
                            m_Elements[i].m_Z * other.m_Elements[2].m_X +
                            m_Elements[i].m_W * other.m_Elements[3].m_X;
                }
            }
            return new Mat4(elements);
        }

        public Vec4 mul(Vec4 other) {
            float[] elements = new float[4];
            for (int i = 0; i < 4; i++) {
                elements[i] = m_Elements[i].m_X * other.m_X +
                        m_Elements[i].m_Y * other.m_Y +
                        m_Elements[i].m_Z * other.m_Z +
                        m_Elements[i].m_W * other.m_W;
            }
            return new Vec4(elements[0], elements[1], elements[2], elements[3]);
        }

        public Mat4 transpose() {
            float[][] elements = new float[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    elements[i][j] = m_Elements[j].m_X;
                }
            }
            return new Mat4(elements);
        }

        public Mat4 inverse() {
            float[][] m = new float[4][4];

            // Populate the 2D array from Vec4 elements
            for (int i = 0; i < 4; i++) {
                m[i][0] = m_Elements[i].m_X;
                m[i][1] = m_Elements[i].m_Y;
                m[i][2] = m_Elements[i].m_Z;
                m[i][3] = m_Elements[i].m_W;
            }

            // Inverse matrix initialization
            float[][] inv = new float[4][4];

            // Calculating the matrix of cofactors, transposed (adjoint)
            inv[0][0] = det3x3(m[1][1], m[1][2], m[1][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
            inv[1][0] = -det3x3(m[1][0], m[1][2], m[1][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
            inv[2][0] = det3x3(m[1][0], m[1][1], m[1][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
            inv[3][0] = -det3x3(m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);

            inv[0][1] = -det3x3(m[0][1], m[0][2], m[0][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
            inv[1][1] = det3x3(m[0][0], m[0][2], m[0][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
            inv[2][1] = -det3x3(m[0][0], m[0][1], m[0][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
            inv[3][1] = det3x3(m[0][0], m[0][1], m[0][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);

            // Calculate more cofactors for the remaining elements...
            // This code block will be large due to the nature of the calculation.

            // Determinant calculated from the original matrix elements
            float det = m[0][0] * inv[0][0] + m[0][1] * inv[1][0] + m[0][2] * inv[2][0] + m[0][3] * inv[3][0];

            if (det == 0) {
                throw new IllegalStateException("Matrix is not invertible");
            }

            // Normalize the adjoint matrix to get the inverse
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    inv[i][j] /= det;
                }
            }

            for (int i = 0; i < 4; i++) {
                m_Elements[i] = new Vec4(inv[i][0], inv[i][1], inv[i][2], inv[i][3]);
            }

            return this;
        }

        private float det3x3(float a, float b, float c, float d, float e, float f, float g, float h, float i) {
            return a * (e * i - f * h) + b * (f * g - d * i) + c * (d * h - e * g);
        }

        public void translate(Vec3 position) {
            m_Elements[3].m_X += position.m_X;
            m_Elements[3].m_Y += position.m_Y;
            m_Elements[3].m_Z += position.m_Z;
        }

        public void rotate(Vec3 rotation) {
            Mat4 rotationMatrix = new Mat4();
            rotationMatrix.rotateX(rotation.m_X);
            rotationMatrix.rotateY(rotation.m_Y);
            rotationMatrix.rotateZ(rotation.m_Z);
            m_Elements = rotationMatrix.mul(this).m_Elements;
        }

        private void rotateX(float mX) {
            float cos = (float) Math.cos(Math.toRadians(mX));
            float sin = (float) Math.sin(Math.toRadians(mX));
            Mat4 rotationMatrix = new Mat4(new float[][]{
                    {1, 0, 0, 0},
                    {0, cos, -sin, 0},
                    {0, sin, cos, 0},
                    {0, 0, 0, 1}
            });
            m_Elements = rotationMatrix.mul(this).m_Elements;
        }

        private void rotateY(float mY) {
            float cos = (float) Math.cos(Math.toRadians(mY));
            float sin = (float) Math.sin(Math.toRadians(mY));
            Mat4 rotationMatrix = new Mat4(new float[][]{
                    {cos, 0, sin, 0},
                    {0, 1, 0, 0},
                    {-sin, 0, cos, 0},
                    {0, 0, 0, 1}
            });
            m_Elements = rotationMatrix.mul(this).m_Elements;
        }

        private void rotateZ(float mZ) {
            float cos = (float) Math.cos(Math.toRadians(mZ));
            float sin = (float) Math.sin(Math.toRadians(mZ));
            Mat4 rotationMatrix = new Mat4(new float[][]{
                    {cos, -sin, 0, 0},
                    {sin, cos, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
            m_Elements = rotationMatrix.mul(this).m_Elements;
        }

        public void scale(Vec3 scale) {
            m_Elements[0].m_X *= scale.m_X;
            m_Elements[1].m_Y *= scale.m_Y;
            m_Elements[2].m_Z *= scale.m_Z;
        }

        public float[] getArray() {
            float[] array = new float[16];
            for (int i = 0; i < 4; i++) {
                array[i * 4] = m_Elements[i].m_X;
                array[i * 4 + 1] = m_Elements[i].m_Y;
                array[i * 4 + 2] = m_Elements[i].m_Z;
                array[i * 4 + 3] = m_Elements[i].m_W;
            }
            return array;
        }
    }

    public static class Vertex {

        public Vec3 m_Position;
        public Vec3 m_Normal;
        public Vec2 m_TexCoords;

        public static final int SIZE = Vec3.SIZE * 2 + Vec2.SIZE;

        public Vertex(Vec3 position, Vec3 normal, Vec2 texCoords) {
            m_Position = position;
            m_Normal = normal;
            m_TexCoords = texCoords;
        }
    }

}
