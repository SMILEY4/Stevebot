package stevebot.commands;

public class CommandArgument<T> {


	private final T value;




	/**
	 * Create a new argument with the given value.
	 *
	 * @param value the value of this argument
	 */
	public CommandArgument(T value) {
		this.value = value;
	}




	/**
	 * Create a new argument with the null as its value.
	 */
	public CommandArgument() {
		this(null);
	}




	/**
	 * @return true, if the value of this argument is not null.
	 */
	public boolean existsValue() {
		return value != null;
	}




	/**
	 * @return the value of this argument or null.
	 */
	public T getValue() {
		return value;
	}

}
