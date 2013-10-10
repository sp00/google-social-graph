package com.hackaton.social.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mdaleki
 */

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
public class PersonNode extends BaseNode {
	private static final String TYPE = "person";

	public PersonNode(final String id, final String name, final String imageUrl) {
		super(id, name);
		setImageHref(imageUrl);
	}

	@Override
	public String getType() {
		return TYPE;
	}
}
