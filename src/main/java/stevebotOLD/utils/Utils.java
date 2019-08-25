package stevebotOLD.utils;

import net.minecraft.util.math.Vec3d;

public class Utils {

//  -> player.getLook(...)
//	public static Vec3d rotToVector(double pitch, double yaw) {
//		final double cPitch = ((pitch + 90.0) * Math.PI) / 180.0;
//		final double cYaw = ((yaw + 90.0) * Math.PI) / 18.00;
//		final double x = Math.sin(cPitch) * Math.cos(cYaw);
//		final double y = Math.sin(cPitch) * Math.sin(cYaw);
//		final double z = Math.cos(cPitch);
//		return new Vec3d(x, y, z).normalize();
//	}




	public static double vectorToPitch(Vec3d dir) {
		double pitch = Math.asin(dir.y);
		pitch = pitch * 180.0 / Math.PI;
		return pitch;
	}




	public static double vectorToYaw(Vec3d dir) {
		double yaw = Math.atan2(dir.z, dir.x);
		yaw = yaw * 180.0 / Math.PI;
		yaw += 90f;
		return yaw;
	}

}
