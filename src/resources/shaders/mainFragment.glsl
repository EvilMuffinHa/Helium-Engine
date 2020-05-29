#version 410 core

in vec3 passColor;
in vec2 passTextureCoord;
uniform int type;

out vec4 outColor;

uniform sampler2D tex;

void main() {
	if (type == 1) {
		outColor = texture(tex, passTextureCoord);
	} else {
		outColor = vec4(passColor, 1.0);
	}
}
