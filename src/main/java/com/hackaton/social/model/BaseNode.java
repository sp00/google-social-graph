package com.hackaton.social.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mdaleki
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BaseNode {

	@XmlAttribute
	private String id;

	@XmlAttribute
	private String imageHref;

	@XmlAttribute
	private String label;

	public BaseNode(final String id, final String label) {
		this.id = id;
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public void setImageHref(final String imageHref) {
		this.imageHref = imageHref;
	}

	public String getImageHref() {
		return imageHref;
	}
}
