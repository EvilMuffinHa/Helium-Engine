#version 460 core

in vec3 passColor;
in vec3 passTextureCoord;

out vec4 outColor;

uniform sampler2D tex;

void main() {
    outColor = texture(tex, passTextureCoord);
}
