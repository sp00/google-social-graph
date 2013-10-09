package com.hackaton.social.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hackaton.social.google.plus.CircleService;
import com.hackaton.social.model.CircleNode;
import com.hackaton.social.model.PersonNode;

/**
 * @author mdaleki
 */
@Controller
@RequestMapping(value = "/circlerelation")
public class CircleRelationController {

	@Autowired
	private CircleService circleService;

	@RequestMapping(value = "/circle/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public CircleNode getCircleNode(@PathVariable("id") Long id) {
		return new CircleNode();
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PersonNode getPersonNode(@PathVariable("id") Long id) {
		return new PersonNode();
	}

}
