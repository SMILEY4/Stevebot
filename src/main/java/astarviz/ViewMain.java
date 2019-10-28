package astarviz;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2i;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ViewMain extends AnchorPane {


	@FXML private Spinner<Integer> spinnerMapWidth;
	@FXML private Spinner<Integer> spinnerMapHeight;

	@FXML private Button btnClear;

	@FXML private Label labelPosition;
	@FXML private Label labelGCost;
	@FXML private Label labelHCost;
	@FXML private Label labelFCost;

	@FXML private Button btnFindPath;
	@FXML private Spinner<Integer> spinnerStep;
	@FXML private Slider sliderStep;

	@FXML private TextArea areaInfo;

	@FXML private Canvas canvas;


	private Vector2i mouseOverNode = null;
	private Map[] maps = null;
	private int currentMapIndex = 0;




	public ViewMain() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
			loader.setController(this);
			Parent parent = loader.load();
			AnchorPane.setTopAnchor(parent, 0.0);
			AnchorPane.setBottomAnchor(parent, 0.0);
			AnchorPane.setLeftAnchor(parent, 0.0);
			AnchorPane.setRightAnchor(parent, 0.0);
			this.getChildren().add(parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public void create() {

		resizeMap(20, 20);


		// map size
		spinnerMapWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, maps[0].width));
		spinnerMapHeight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, maps[0].height));

		spinnerMapWidth.valueProperty().addListener(((observable, oldValue, newValue) -> {
			resizeMap(spinnerMapWidth.getValue(), spinnerMapHeight.getValue());
		}));
		spinnerMapHeight.valueProperty().addListener(((observable, oldValue, newValue) -> {
			resizeMap(spinnerMapWidth.getValue(), spinnerMapHeight.getValue());
		}));

		// clear
		btnClear.setOnAction(e -> clear());

		// find path
		btnFindPath.setOnAction(e -> findPath());

		// path step
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		spinnerStep.valueProperty().addListener(((observable, oldValue, newValue) -> {
			viewStep(spinnerStep.getValue());
		}));

		sliderStep.setValue(0);
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		sliderStep.setBlockIncrement(1);
		sliderStep.valueProperty().addListener(((observable, oldValue, newValue) -> {
			viewStep((int) sliderStep.getValue());
		}));

		// mouse over
		canvas.setOnMouseMoved(e -> onMouseOver(toNodePosition(e.getX(), e.getY())));
		canvas.setOnMouseExited(e -> onMouseExit());

		// mouse clicker
		canvas.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				placeStart(toNodePosition(e.getX(), e.getY()));
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				placeGoal(toNodePosition(e.getX(), e.getY()));
			}
			if (e.getButton() == MouseButton.MIDDLE) {
				placeWall(toNodePosition(e.getX(), e.getY()));
			}
		});
		canvas.setOnMouseDragged(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				placeStart(toNodePosition(e.getX(), e.getY()));
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				placeGoal(toNodePosition(e.getX(), e.getY()));
			}
		});

	}




	private void findPath() {
		if (maps[0].getGoal() != null && maps[0].getStart() != null) {
			maps = Pathfinding.findPath(maps[0]);
			spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, maps.length - 1));
			sliderStep.setMin(0);
			sliderStep.setMax(maps.length - 1);
			sliderStep.setValue(maps.length - 1);
			repaint();
		} else {
			System.err.println("No start or goal set!");
		}
	}




	private void viewStep(int step) {
		sliderStep.setValue(step);
		spinnerStep.getValueFactory().setValue(step);
		currentMapIndex = step;
		repaint();
	}




	private void clear() {
		if (maps.length != 1) {
			maps = new Map[]{maps[0]};
		}
		maps[0].clear();
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		sliderStep.setValue(0);
		repaint();
	}




	private void placeStart(Vector2i pos) {
		if (maps.length != 1) {
			maps = new Map[]{maps[0]};
		}
		maps[0].setStart(pos);
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		sliderStep.setValue(0);
		repaint();
	}




	private void placeGoal(Vector2i pos) {
		if (maps.length != 1) {
			maps = new Map[]{maps[0]};
		}
		maps[0].setGoal(pos);
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		sliderStep.setValue(0);
		repaint();
	}




	private void placeWall(Vector2i pos) {
		if (maps.length != 1) {
			maps = new Map[]{maps[0]};
		}
		maps[0].setWall(pos);
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		sliderStep.setValue(0);
		repaint();
	}




	private void onMouseOver(Vector2i pos) {
		final Node node = maps[currentMapIndex].nodes[pos.x][pos.y];
		labelPosition.setText("pos: " + pos.x + ", " + pos.y);
		labelGCost.setText("gcost: " + node.gcost);
		labelHCost.setText("hcost: " + node.hcost);
		labelFCost.setText("fcost: " + node.fcost());
		repaint();
	}




	private void onMouseExit() {
		mouseOverNode = null;
		labelPosition.setText("pos: -");
		labelGCost.setText("gcost: -");
		labelHCost.setText("hcost: -");
		labelFCost.setText("fcost: -");
	}




	private Vector2i toNodePosition(double x, double y) {
		final double screenWidth = canvas.getWidth();
		final double screenHeight = canvas.getHeight();

		final int mapWidth = maps[0].width;
		final int mapHeight = maps[0].height;

		final double nodeWidth = screenWidth / mapWidth;
		final double nodeHeight = screenHeight / mapHeight;

		final int nodeX = (int) (x / nodeWidth);
		final int nodeY = (int) (y / nodeHeight);

		final Vector2i nodePos = new Vector2i();
		nodePos.x = Math.max(0, Math.min(nodeX, mapWidth - 1));
		nodePos.y = Math.max(0, Math.min(nodeY, mapHeight - 1));

		return nodePos;
	}




	private void resizeMap(int width, int height) {
		maps = new Map[]{new Map(width, height)};
		spinnerStep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maps.length - 1, 0));
		sliderStep.setValue(0);
		sliderStep.setMin(0);
		sliderStep.setMax(maps.length - 1);
		repaint();
	}



	private void refreshAreaInfo(Map map) {

		StringBuilder builder = new StringBuilder();

		for(int i=0; i<map.bestNodes.length; i++) {
			builder.append(i).append(": ");
			if(map.bestNodes[i] == null) {
				builder.append(" - ");
			} else {
				builder.append(map.bestNodes[i].x).append(",").append(map.bestNodes[i].y).append("  (").append(map.bestCosts[i]).append(")");
			}
			builder.append(System.lineSeparator());
		}

		areaInfo.setText(builder.toString());
	}



	private void repaint() {

		final GraphicsContext g = canvas.getGraphicsContext2D();

		final double screenWidth = canvas.getWidth();
		final double screenHeight = canvas.getHeight();

		final int mapWidth = maps[0].width;
		final int mapHeight = maps[0].height;

		final double nodeWidth = screenWidth / mapWidth;
		final double nodeHeight = screenHeight / mapHeight;

		// background
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, screenWidth, screenHeight);

		final Color COLOR_OPEN = Color.color(0.5f, 0.9f, 0.5f);
		final Color COLOR_CLOSED = Color.color(0.5f, 0.5f, 0.9f);

		final Color COLOR_MIN_BEST_COST = Color.GREEN;
		final Color COLOR_MAX_BEST_COST = Color.RED;


		// nodes
//		double maxBestCost = -Node.INFINITY;
//		double minBestCost = Node.INFINITY;
//		for (int x = 0; x < mapWidth; x++) {
//			for (int y = 0; y < mapHeight; y++) {
//				final Node node = maps[currentMapIndex].nodes[x][y];
//				if (node.isWall || node.isGoal || node.isStart || !node.processed || Math.abs(node.bestCost) > Node.INFINITY) {
//					continue;
//				}
//				maxBestCost = Math.max(node.bestCost, maxBestCost);
//				minBestCost = Math.min(node.bestCost, minBestCost);
//			}
//		}

		Map currentMap = maps[currentMapIndex];
		refreshAreaInfo(currentMap);

		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				final Node node = currentMap.nodes[x][y];

//				double pBestCost = (node.bestCost - minBestCost) / (maxBestCost - minBestCost);
//				pBestCost = pBestCost * pBestCost;

				// set fill
				if (node.isWall) {
					g.setFill(Color.DARKGRAY);
				} else if (node.isStart) {
					g.setFill(Color.DARKGREEN);
				} else if (node.isGoal) {
					g.setFill(Color.DARKBLUE);
				} else {

					if (node.processed) {
						g.setFill(node.open ? COLOR_OPEN : COLOR_CLOSED);
//						Color colorBestCost = Color.color(
//								COLOR_MIN_BEST_COST.getRed() * (1.0 - pBestCost) + COLOR_MAX_BEST_COST.getRed() * (pBestCost),
//								COLOR_MIN_BEST_COST.getGreen() * (1.0 - pBestCost) + COLOR_MAX_BEST_COST.getGreen() * (pBestCost),
//								COLOR_MIN_BEST_COST.getBlue() * (1.0 - pBestCost) + COLOR_MAX_BEST_COST.getBlue() * (pBestCost)
//						);
//						g.setFill(colorBestCost);
//
//						if(MathUtils.isNearlyEqual(node.bestCost, minBestCost)) {
//							g.setFill(Color.YELLOW);
//						}

						for (int j = 0; j < currentMap.bestCosts.length; j++) {
							if (currentMap.bestNodes[j] == null) {
								continue;
							}
							if (node.x == currentMap.bestNodes[j].x && node.y == currentMap.bestNodes[j].y) {
								g.setFill(Color.YELLOW);
								break;
							}
						}

					} else {
						g.setFill(Color.WHITE);
					}

				}

				// set outline
				if (mouseOverNode != null && mouseOverNode.x == x && mouseOverNode.y == y) {
					g.setStroke(Color.BLACK);
				} else {
					g.setStroke(Color.GRAY);
				}

				// draw node
				g.fillRect(x * nodeWidth, y * nodeHeight, nodeWidth, nodeHeight);
				g.strokeRect(x * nodeWidth, y * nodeHeight, nodeWidth, nodeHeight);
			}
		}


		if (maps[currentMapIndex].path != null) {
			Path path = maps[currentMapIndex].path;
			g.setStroke(Color.BLUE);
			for (int i = 0; i < path.nodes.size() - 1; i++) {
				final Node from = path.nodes.get(i);
				final Node to = path.nodes.get(i + 1);
				final double x0 = from.x * nodeWidth + nodeWidth / 2;
				final double y0 = from.y * nodeHeight + nodeHeight / 2;
				final double x1 = to.x * nodeWidth + nodeWidth / 2;
				final double y1 = to.y * nodeHeight + nodeHeight / 2;
				g.strokeLine(x0, y0, x1, y1);
			}
		}


	}


}
