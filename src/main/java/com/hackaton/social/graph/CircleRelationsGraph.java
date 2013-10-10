package com.hackaton.social.graph;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.plus.model.Circle;
import com.google.api.services.plus.model.Person;
import com.hackaton.social.google.plus.PlusService;
import com.hackaton.social.google.plus.UserService;
import com.hackaton.social.model.BaseNode;

/**
 * @author mdaleki
 */
@Component
public class CircleRelationsGraph {
	private static final Logger log = LoggerFactory.getLogger(CircleRelationsGraph.class);

	private PlusService plusService;
	private UserService userService;

	@Autowired
	public CircleRelationsGraph(PlusService plusService, UserService userService) {
		this.plusService = plusService;
		this.userService = userService;
	}

	public BaseNode createGraph(String user, int depth) {
		final long start = System.currentTimeMillis();
		log.info("Creating graph for user {}.", user);
		final CircleGraphBuilder graphBuilder = new CircleGraphBuilder();
		final User domainUser = userService.getUser(user);
		final Person person = new Person();
		person.setDisplayName(domainUser.getName() != null ? domainUser.getName().getFullName() : null);
		person.setId(domainUser.getId());
		final Person.Image image = new Person.Image();
		image.setUrl(domainUser.getThumbnailPhotoUrl());
		person.setImage(image);
		graphBuilder.pushPerson(person);
		final BaseNode build = createGraph(graphBuilder, user, depth).build();
		log.info("Graph for user {} created successfully ({} sec.).", user,
				(System.currentTimeMillis() - start) / 1000.0);
		graphBuilder.popPerson();
		return build;
	}

	private CircleGraphBuilder createGraph(CircleGraphBuilder graphBuilder, String user, int depth) {
		if (depth <= 0) {
			return graphBuilder;
		}
		log.info("Create graph node for user {}. Depth = {}.", user, depth);
		final Collection<Circle> circles = plusService.getCircles(user);
		for (Circle circle : circles) {
			final Long totalItems = circle.getPeople().getTotalItems();
			if (totalItems > 25) {
				log.info("Circle {} of user {} has {} members. Skipping this circle from graph (accept only <={}).",
						circle.getDisplayName(), user, totalItems, 25);
				continue;
			}
			graphBuilder.pushCircle(circle);
			final Collection<Person> persons = plusService.getPersons(user, circle.getId());
			for (Person prs : persons) {
				graphBuilder.pushPerson(prs);
				log.debug("Looking for user {} ({})", prs.getDisplayName(), prs.getId());
				final User domainUser = userService.getUser(prs.getId());
				if (null != domainUser) {
					createGraph(graphBuilder, domainUser.getPrimaryEmail(), depth - 1);
				}
				graphBuilder.popPerson();
			}
			graphBuilder.popCircle();
		}
		return graphBuilder;
	}
}
