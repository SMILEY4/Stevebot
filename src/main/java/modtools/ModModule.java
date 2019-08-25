package modtools;

public abstract class ModModule {


	private final ModBase modHandler;




	public ModModule(ModBase modHandler) {
		this.modHandler = modHandler;
	}




	public ModBase getModHandler() {
		return modHandler;
	}





}
