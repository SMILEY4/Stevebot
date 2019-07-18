package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;

public interface IAction {

	double getCost();

	Node getFrom();

	Node getTo();
}
