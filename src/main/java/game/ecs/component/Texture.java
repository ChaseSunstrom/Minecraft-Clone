package game.ecs.component;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {

    private int m_TextureID;
    private BufferedImage m_Image;

    public Texture(String path) {
        try {
            m_Image = ImageIO.read(new File(path));
            loadTexture();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTexture() {
        if (m_Image != null) {
            // Convert BufferedImage to ByteBuffer
            int[] pixels = new int[m_Image.getWidth() * m_Image.getHeight()];
            m_Image.getRGB(0, 0, m_Image.getWidth(), m_Image.getHeight(), pixels, 0, m_Image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(m_Image.getWidth() * m_Image.getHeight() * 4); // 4 bytes for RGBA

            for (int y = 0; y < m_Image.getHeight(); y++) {
                for (int x = 0; x < m_Image.getWidth(); x++) {
                    int pixel = pixels[y * m_Image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));             // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component
                }
            }
            buffer.flip(); // FOR THE GL

            // Create a new OpenGL texture
            m_TextureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, m_TextureID);

            // Setup wrap mode
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

            // Setup texture scaling filtering
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            // Send texel data to OpenGL
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, m_Image.getWidth(), m_Image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            // Unbind the texture
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
    }

    public void cleanup() {
        GL11.glDeleteTextures(m_TextureID);
    }

    public int getTextureID() {
        return m_TextureID;
    }

    public BufferedImage getImage() {
        return m_Image;
    }

    public int getWidth() {
        return m_Image.getWidth();
    }

    public int getHeight() {
        return m_Image.getHeight();
    }
}
