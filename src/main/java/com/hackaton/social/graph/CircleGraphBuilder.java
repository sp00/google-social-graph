package com.hackaton.social.graph;

import java.util.Stack;

import com.google.api.services.plus.model.Circle;
import com.google.api.services.plus.model.Person;
import com.hackaton.social.model.BaseNode;
import com.hackaton.social.model.CircleNode;
import com.hackaton.social.model.PersonNode;

/**
 * @author mdaleki
 */
public class CircleGraphBuilder {
	private BaseNode rootNode;
	private Stack<PersonNode> personsStack = new Stack<>();
	private Stack<CircleNode> circlesStack = new Stack<>();

	public CircleGraphBuilder() {
	}

	public CircleGraphBuilder pushPerson(Person person) {
		PersonNode personNode = new PersonNode(person.getId(), person.getDisplayName(),
				person.getImage() == null ? "DEFAULT" : person.getImage().getUrl());
		if (!circlesStack.empty()) {
			circlesStack.peek().getChildren().add(personNode);
		}
		personsStack.push(personNode);
		setRootNode(personNode);
		return this;
	}

	private void setRootNode(final BaseNode node) {
		if (null == rootNode) {
			rootNode = node;
			rootNode.setRoot(true);
		}
	}

	public CircleGraphBuilder pushCircle(Circle circle) {
		CircleNode circleNode = new CircleNode(circle.getId(), circle.getDisplayName());
		if (!personsStack.empty()) {
			personsStack.peek().getChildren().add(circleNode);
		}
		circlesStack.push(circleNode);
		setRootNode(circleNode);
		return this;
	}

	public void popPerson() {
		if (!personsStack.empty()) {
			personsStack.pop();
		}
	}

	public void popCircle() {
		if (!circlesStack.empty()) {
			circlesStack.pop();
		}
	}

	public BaseNode build() {
		return rootNode;
	}
}
