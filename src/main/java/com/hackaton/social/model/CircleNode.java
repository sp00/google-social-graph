package com.hackaton.social.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mdaleki
 */
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
public class CircleNode extends BaseNode {
	private static final String TYPE = "circle";

	public CircleNode(final String id, final String label) {
		super(id, label);
	}

	@Override
	public String getType() {
		return TYPE;
	}
}
