package com.hackaton.social.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mdaleki
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class BaseNode {

	@XmlAttribute
	private String uid;

	@XmlAttribute
	private String imageHref;

	@XmlAttribute
	private String label;

	@XmlAttribute
	private boolean isRoot;

	@XmlElement
	private List<BaseNode> children = new ArrayList<>();

	public BaseNode(final String id, final String label) {
		this.uid = id;
		this.label = label;
	}

	public String getUid() {
		return uid;
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

	public List<BaseNode> getChildren() {
		return children;
	}

	public abstract String getType();

	public void setRoot(final boolean root) {
		isRoot = root;
	}

	public boolean isRoot() {
		return isRoot;
	}
}
