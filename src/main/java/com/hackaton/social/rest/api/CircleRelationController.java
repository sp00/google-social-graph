package com.hackaton.social.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hackaton.social.graph.CircleRelationsGraph;
import com.hackaton.social.model.BaseNode;
import com.hackaton.social.model.CircleNode;

/**
 * @author mdaleki
 */
@Controller
@RequestMapping(value = "/circlerelation")
public class CircleRelationController {

	@Autowired
	private CircleRelationsGraph circleRelationsGraph;

	@RequestMapping(value = "/circle/{id:.*}", headers = "Accept=application/json,*/*", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public CircleNode getCircleNode(@PathVariable("id") String id, @RequestParam(value = "d", required = false,
			defaultValue = "1") Long depth) {
		throw new IllegalStateException("Not yet implemented");
	}

	@RequestMapping(value = "/person/{id:.*}", headers = "Accept=application/json,*/*", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public BaseNode getPersonNode(@PathVariable("id") String id, @RequestParam(value = "d", required = false,
			defaultValue = "1") Long depth) {
		return circleRelationsGraph.createGraph(id, depth.intValue());
	}

}
