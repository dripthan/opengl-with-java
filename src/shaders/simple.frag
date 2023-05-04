#version 450 core

in vec3 fColor;

out vec4 pixelColor;

void main()
{
  pixelColor = vec4(fColor, 1.f);
}