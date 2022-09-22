package stevebot.core.math;


import stevebot.core.math.vectors.vec2.IVector2;
import stevebot.core.math.vectors.vec2.Vector2d;
import stevebot.core.math.vectors.vec2.Vector2f;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.math.vectors.vec3.Vector3f;

public class MathUtils {


    private static final float EPSILON = 0.000001f;


    private MathUtils() {
    }


    /**
     * @return true, if a is nearly equal to b.
     */
    public static boolean isNearlyEqualDecPlaces(float a, float b, int decPlaces) {
        final int as = (int) (a * Math.pow(10, decPlaces));
        final int bs = (int) (b * Math.pow(10, decPlaces));
        return as == bs;
    }


    /**
     * @return true, if a is nearly equal to b.
     */
    public static boolean isNearlyEqualDecPlaces(double a, double b, int decPlaces) {
        final int as = (int) (a * Math.pow(10, decPlaces));
        final int bs = (int) (b * Math.pow(10, decPlaces));
        return as == bs;
    }


    /**
     * @return true, if a is nearly equal to b.
     */
    public static boolean isNearlyEqual(float a, float b) {
        return isNearlyEqual(a, b, false);
    }


    /**
     * @return true, if a is nearly equal to b. Set "dynamicEpsilon" to true, to calculate the threshold for each pair of values.
     */
    public static boolean isNearlyEqual(float a, float b, boolean dynamicEpsilon) {
        if (dynamicEpsilon) {
            return isNearlyEqual(a, b, Math.max(Math.ulp(a), Math.ulp(b)));
        } else {
            return isNearlyEqual(a, b, EPSILON);
        }
    }


    /**
     * @return true, if a is nearly equal to b. Uses the given epsilon as a threshold
     */
    public static boolean isNearlyEqual(float a, float b, float epsilon) {
        return Math.abs(a - b) < epsilon;
    }


    /**
     * @return true, if a is nearly equal to b.
     */
    public static boolean isNearlyEqual(double a, double b) {
        return isNearlyEqual(a, b, false);
    }


    /**
     * @return true, if a is nearly equal to b. Set "dynamicEpsilon" to true, to calculate the threshold for each pair of values.
     */
    public static boolean isNearlyEqual(double a, double b, boolean dynamicEpsilon) {
        if (dynamicEpsilon) {
            return isNearlyEqual(a, b, Math.max(Math.ulp(a), Math.ulp(b)));
        } else {
            return isNearlyEqual(a, b, EPSILON);
        }
    }


    /**
     * @return true, if a is nearly equal to b. Uses the given epsilon as a threshold
     */
    public static boolean isNearlyEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }


    /**
     * limits the given value v to the given minimum / maximum value
     */
    public static short clamp(short v, short min, short max) {
        return (short) Math.min(Math.max(v, min), max);
    }


    /**
     * limits the given value v to the given minimum / maximum value
     */
    public static int clamp(int v, int min, int max) {
        return Math.min(Math.max(v, min), max);
    }


    /**
     * limits the given value v to the given minimum / maximum value
     */
    public static long clamp(long v, long min, long max) {
        return Math.min(Math.max(v, min), max);
    }


    /**
     * limits the given value v to the given minimum / maximum value
     */
    public static float clamp(float v, float min, float max) {
        return Math.min(Math.max(v, min), max);
    }


    /**
     * limits the given value v to the given minimum / maximum value
     */
    public static double clamp(double v, double min, double max) {
        return Math.min(Math.max(v, min), max);
    }


    /**
     * calculates the barycentric coordinates (with float precision) of the given point P and the triangle ABC
     *
     * @param a the first point of the triangle
     * @param b the second point of the triangle
     * @param c the third point of the triangle
     * @param p the point
     */
    public static Vector3f barycentric(IVector2 a, IVector2 b, IVector2 c, IVector2 p) {

        Vector2f v1 = new Vector2f();
        Vector2f v2 = new Vector2f();
        Vector2f q = new Vector2f();

        Vector2f.setVectorAB(a, b, v1);
        Vector2f.setVectorAB(a, c, v2);
        Vector2f.setVectorAB(a, p, q);

        final float c12 = v1.cross(v2);

        Vector3f bary = new Vector3f();
        bary.y = q.cross(v2) / c12;
        bary.z = v1.cross(q) / c12;
        bary.x = 1f - bary.y - bary.z;

        return bary;
    }


    /**
     * calculates the barycentric coordinates (with double precision) of the given point P and the triangle ABC
     *
     * @param a the first point of the triangle
     * @param b the second point of the triangle
     * @param c the third point of the triangle
     * @param p the point
     */
    public static Vector3d barycentricDouble(Vector2d a, Vector2d b, Vector2d c, Vector2d p) {

        Vector2d v1 = new Vector2d();
        Vector2d v2 = new Vector2d();
        Vector2d q = new Vector2d();

        Vector2d.setVectorAB(a, b, v1);
        Vector2d.setVectorAB(a, c, v2);
        Vector2d.setVectorAB(a, p, q);

        final double c12 = v1.cross(v2);

        Vector3d bary = new Vector3d();
        bary.y = q.cross(v2) / c12;
        bary.z = v1.cross(q) / c12;
        bary.x = 1.0 - bary.y - bary.z;

        return bary;
    }


    public static double setDecPlaces(double value, int decPlaces) {
        int p = (int) Math.pow(10, Math.max(0, decPlaces));
        return (double) ((int) (value * p)) / (double) p;
    }


    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }


    public static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }


    /**
     * @param col       the first index in the 2D array
     * @param row       the second index in the 2D array
     * @param arrWidth  the width of the array
     * @param arrHeight to height of the array
     * @return the index for a 1D array. index:  0 <= index < arrWidth*arrHeight
     */
    public static int arrayIndex_2Dto1D(int col, int row, int arrWidth, int arrHeight) {
        final int index = (col + row * arrWidth);
        return Math.max(0, Math.min(arrWidth * arrHeight, index));
    }


    /**
     * @return true, if the given value is greater or equals the lower and smaller than the upper bound
     */
    public static boolean inRange(double value, double lower, double upper) {
        return lower <= value && value < upper;
    }


    /**
     * @return true, if the given value is greater or equals the lower and smaller than the upper bound
     */
    public static boolean inRange(float value, float lower, float upper) {
        return lower <= value && value < upper;
    }


    /**
     * @return true, if the given value is greater or equals the lower and smaller than the upper bound
     */
    public static boolean inRange(long value, long lower, long upper) {
        return lower <= value && value < upper;
    }


    /**
     * @return true, if the given value is greater or equals the lower and smaller than the upper bound
     */
    public static boolean inRange(int value, int lower, int upper) {
        return lower <= value && value < upper;
    }


}
