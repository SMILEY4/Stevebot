package astarviz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AStarViz extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(Stage primaryStage) {

		ViewMain viewMain = new ViewMain();
		viewMain.create();

		Scene scene = new Scene(viewMain, 1672, 980);

		primaryStage.setTitle("A*-Viz");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
