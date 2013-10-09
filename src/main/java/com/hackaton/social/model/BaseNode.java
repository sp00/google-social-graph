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
	private String avatarHref;

	public String getAvatarHref() {
		return avatarHref;
	}
}
