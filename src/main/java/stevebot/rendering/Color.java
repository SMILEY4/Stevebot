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




	/**
	 * @return a random color
	 */
	public static Color random() {
		return random(System.nanoTime());
	}




	/**
	 * @param seed the seed for the rng
	 * @return a random color with the given seed
	 */
	public static Color random(long seed) {
		random.setSeed(seed);
		final float hue = random.nextFloat();
		final float saturation = (random.nextFloat() / 2) + 0.5f;
		final float luminance = 0.9f;
		return fromHSV(hue, saturation, luminance);
	}




	/**
	 * @param n the number of colors
	 * @return an array of n distinct colors
	 */
	public static Color[] generateDistinctColors(int n) {
		Color[] colors = new Color[n];
		for (int i = 0, j = 0; i < 360; i += 360 / n) {
			colors[j++] = fromHSV((float)i/360f, 1f, 0.7f);
		}
		return colors;
	}




	/**
	 * Creates a {@link Color} from the given HSB-components
	 *
	 * @param hue        the hue of the color between 0.0 and 1.0
	 * @param saturation the saturation of the color between 0.0 and 1.0
	 * @param value      the value/brightness of the color between 0.0 and 1.0
	 * @return the created color
	 */
	public static Color fromHSV(float hue, float saturation, float value) {
		final java.awt.Color awtColor = java.awt.Color.getHSBColor(hue, saturation, value);
		return new Color((float) awtColor.getRed() / 255f, (float) awtColor.getGreen() / 255f, (float) awtColor.getBlue() / 255f);
	}




	/**
	 * Creates a new color by mixing the two given color
	 *
	 * @param colorA the first color
	 * @param colorB the seconds color
	 * @param value  0 = result is 100% of the first color, 1 = the 100% of the second color
	 * @return the created color
	 */
	public static Color mix(Color colorA, Color colorB, float value) {
		final float r = (colorA.x * (1f - value)) + (colorB.x * value);
		final float g = (colorA.y * (1f - value)) + (colorB.y * value);
		final float b = (colorA.z * (1f - value)) + (colorB.z * value);
		return new Color(r, g, b);
	}




	/**
	 * @param r the red component between 0.0 and 1.0
	 * @param g the green component between 0.0 and 1.0
	 * @param b the blue component between 0.0 and 1.0
	 */
	public Color(float r, float g, float b) {
		super(r, g, b);
	}


}
