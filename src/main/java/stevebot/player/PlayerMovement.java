package stevebot.player;

import stevebot.data.blockpos.BaseBlockPos;

public interface PlayerMovement {


	/**
	 * Moves the player one tick towards the given position
	 *
	 * @param pos     the target position
	 * @param ignoreY true to ignore the y-coordinate while checking if the player reached the target
	 * @return true, if the player reached the given target position
	 */
	boolean moveTowards(BaseBlockPos pos, boolean ignoreY);

	/**
	 * Moves the player one tick towards the given position
	 *
	 * @param x the target x position
	 * @param y the target y position
	 * @param z the target z position
	 * @return true, if the player reached the given target position
	 */
	boolean moveTowards(double x, double y, double z);

	/**
	 * Moves the player one tick towards the given x and z coordinates
	 *
	 * @param x the target x position
	 * @param z the target z position
	 * @return true, if the player reached the given coordinates
	 */
	boolean moveTowards(double x, double z);


	/**
	 * Moves the player one tick towards the given position without surpassing the given max speed
	 *
	 * @param x           the target x position
	 * @param y           the target y position
	 * @param z           the target z position
	 * @param maxHorSpeed the max. horizontal speed while moving to the given position
	 * @return true, if the player reached the given target position
	 */
	boolean moveTowardsSpeed(double x, double y, double z, double maxHorSpeed);


	/**
	 * Moves the player one tick towards the given x and z coordinates without surpassing the given max speed
	 *
	 * @param x           the target x position
	 * @param z           the target z position
	 * @param maxHorSpeed the max. horizontal speed while moving to the given position
	 * @return true, if the player reached the given coordinates
	 */
	boolean moveTowardsSpeed(double x, double z, double maxHorSpeed);


	/**
	 * Tries to slow down the player to the given target speed (ignores movement on the y-axis)
	 *
	 * @param prefSpeed the preferred target speed
	 * @return true, if the preferred speed was reached
	 */
	boolean slowDown(double prefSpeed);

}
