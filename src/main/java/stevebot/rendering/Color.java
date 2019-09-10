package stevebot.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3f;

import java.util.Random;

public class Color extends Vector3f {


	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);

	public static final Color CYAN = new Color(0, 1, 1);
	public static final Color MAGENTA = new Color(1, 0, 1);
	public static final Color YELLOW = new Color(1, 1, 0);


	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	public static final Color BLACK = new Color(0, 0, 0);


	private static final Random random = new Random();




	public static Color random() {
		return random(System.nanoTime());
	}




	public static Color random(long seed) {
		random.setSeed(seed);
		final float hue = random.nextFloat();
		final float saturation = (random.nextFloat() / 2) + 0.5f;
		final float luminance = 0.9f;
		return fromHSV(hue, saturation, luminance);
	}




	public static Color fromHSV(float hue, float saturation, float luminance) {
		final java.awt.Color awtColor = java.awt.Color.getHSBColor(hue, saturation, luminance);
		return new Color((float) awtColor.getRed() / 255f, (float) awtColor.getGreen() / 255f, (float) awtColor.getBlue() / 255f);
	}




	public static Color mix(Color colorA, Color colorB, float value) {
		final float r = (colorA.x * (1f - value)) + (colorB.x * value);
		final float g = (colorA.y * (1f - value)) + (colorB.y * value);
		final float b = (colorA.z * (1f - value)) + (colorB.z * value);
		return new Color(r, g, b);
	}




	public Color(float r, float g, float b) {
		super(r, g, b);
	}


}
