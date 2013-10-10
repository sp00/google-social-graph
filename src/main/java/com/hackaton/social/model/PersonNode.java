package com.hackaton.social.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mdaleki
 */

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
public class PersonNode extends BaseNode {

	@XmlElement
	private List<CircleNode> circles = new ArrayList<>();

	public PersonNode(final String id, final String name, final String imageUrl) {
		super(id, name);
		setImageHref(imageUrl);
	}

	public List<CircleNode> getCircles() {
		return circles;
	}
}
