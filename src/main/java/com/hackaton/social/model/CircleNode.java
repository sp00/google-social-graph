package com.hackaton.social.model;

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
public class CircleNode extends BaseNode {

	@XmlElement
	private List<PersonNode> persons;

	public List<PersonNode> getPersons() {
		return persons;
	}
}
