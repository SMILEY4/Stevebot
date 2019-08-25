package modtools.commands;

public class CommandArgument<T> {


	private final T value;




	public CommandArgument(T value) {
		this.value = value;
	}




	public CommandArgument() {
		this(null);
	}




	public boolean existsValue() {
		return value != null;
	}




	public T getValue() {
		return value;
	}

}
