package org.hl.engine.graphics;

import org.hl.engine.utils.TextureLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.openvr.Texture;

import java.awt.image.BufferedImage;

public class Material {

    private Texture texture;

    private String path;

    private BufferedImage image;

    private int width, height;
    private int textureID;

    public Material(String path) {

        this.path = path;

    }
    public void create() {

        this.image = TextureLoader.loadImage(path); //The path is inside the jar file
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.textureID = TextureLoader.loadTexture(image);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }

    public void destroy() {
        GL11.glDeleteTextures(textureID);
    }
}
