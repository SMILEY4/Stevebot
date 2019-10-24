package stevebot.data.modification;

public class HealthChangeModification implements Modification {


	private final int healthChange;




	/**
	 * @param healthChange the change of the players health (1hp = half a heart)
	 */
	HealthChangeModification(int healthChange) {
		this.healthChange = healthChange;
	}




	/**
	 * @return the change of the players health (1hp = half a heart)
	 */
	public int getHealthChange() {
		return healthChange;
	}


}
