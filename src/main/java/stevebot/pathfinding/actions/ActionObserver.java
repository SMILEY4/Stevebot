package stevebot.pathfinding.actions;

import stevebot.Stevebot;

import java.util.HashMap;
import java.util.Map;

public class ActionObserver {


	private static Map<String, Sample> samples = new HashMap<>();
	private static Map<String, Long> actionTicks = new HashMap<>();




	/**
	 * Records one tick of an action with the given tick
	 *
	 * @param actionName the name of the action
	 */
	public static void tickAction(String actionName) {
		final long ticks = actionTicks.getOrDefault(actionName, 0L) + 1;
		actionTicks.put(actionName, ticks);
	}




	/**
	 * Ends the recording of one action
	 *
	 * @param actionName the name of the action
	 * @param discard    if the current recording should be discarded
	 */
	public static void endAction(String actionName, boolean discard) {
		if (actionTicks.containsKey(actionName)) {
			final long ticks = actionTicks.get(actionName);
			actionTicks.remove(actionName);
			if (!discard) {
				final Sample sample = samples.getOrDefault(actionName, new Sample(actionName));
				sample.min = Math.min(sample.min, ticks);
				sample.max = Math.max(sample.max, ticks);
				sample.sum += ticks;
				sample.nSamples++;
				samples.put(actionName, sample);
			}
		}
	}




	/**
	 * Clears the recorded action cost samples
	 */
	public static void clear() {
		samples.clear();
		actionTicks.clear();
	}




	/**
	 * Prints all samples to the console
	 */
	public static void log() {
		Stevebot.log("=== ACTION COST SAMPLES ===");
		for (Sample sample : samples.values()) {
			Stevebot.log(sample.name + " (" + sample.nSamples + ")");
			Stevebot.log("   avg: " + ((double) sample.sum / (double) sample.nSamples));
			Stevebot.log("   min: " + sample.min);
			Stevebot.log("   max: " + sample.max);
		}
		Stevebot.log("---------------------------");
		for (Sample sample : samples.values()) {
			Stevebot.log("public final double " + sample.name + " = " + ((double) sample.sum / (double) sample.nSamples) + ";");
		}
	}




	private static class Sample {


		public final String name;
		public long min = Integer.MAX_VALUE;
		public long max = 0;
		public long sum = 0;
		public int nSamples = 0;




		public Sample(String name) {
			this.name = name;
		}

	}


}



