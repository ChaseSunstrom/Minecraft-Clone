#version 330 core
out vec4 FragColor;

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoords;

struct Material {
    sampler2D diffuse;
    sampler2D specular;
    sampler2D ambient;
    sampler2D texture;
    float shininess;
    vec4 color;
};

uniform Material material;

void main() {
    vec4 material_color = material.color;
    vec4 text = texture(material.texture, TexCoords);
    vec4 result = text * material_color;
    FragColor = result;
}