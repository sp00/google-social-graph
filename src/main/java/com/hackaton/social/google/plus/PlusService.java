package com.hackaton.social.google.plus;

import static java.lang.String.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Circle;
import com.google.api.services.plus.model.CircleFeed;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.roche.google.oauth2.SpringOAuth2Configuration;
import com.roche.google.util.AuthUtil;

/**
 * @author mdaleki
 */
@Service
public class PlusService {
	private static final Logger log = LoggerFactory.getLogger(PlusService.class);
	public static final String ME = "me";
	private static List<String> SCOPES =
			Arrays.asList(PlusScopes.PLUS_ME, PlusScopes.PLUS_STREAM_READ, PlusScopes.PLUS_CIRCLES_WRITE,
					PlusScopes.PLUS_CIRCLES_READ);

	private final SpringOAuth2Configuration oAuth2Configuration;

	@Autowired
	public PlusService(SpringOAuth2Configuration oAuth2Configuration) {
		this.oAuth2Configuration = oAuth2Configuration;
	}

	public Collection<Circle> getCircles(final String user) {
		final List<Circle> list = new ArrayList<>();
		try {
			final Plus.Circles.List circlesListDS = buildPlusServiceForUser(user).circles().list(ME);
			CircleFeed circleFeed;
			do {
				circleFeed = circlesListDS.execute();
				list.addAll(circleFeed.getItems());
				circlesListDS.setPageToken(circleFeed.getNextPageToken());
			} while (circleFeed.getNextPageToken() != null);
		} catch (Throwable t) {
			log.error(format("Can't get circles for %s", user), t);
		}
		return list;
	}

	public Collection<Person> getPersons(final String user, final String circleId) {
		final List<Person> list = new ArrayList<>();
		try {
			final Plus.People.ListByCircle peopleListDS = buildPlusServiceForUser(user).people().listByCircle(circleId);
			PeopleFeed peopleFeed;
			do {
				peopleFeed = peopleListDS.execute();
				list.addAll(peopleFeed.getItems());
				peopleListDS.setPageToken(peopleFeed.getNextPageToken());
			} while (peopleFeed.getNextPageToken() != null);
		} catch (Throwable t) {
			log.error(format("Can't get circles for %s", user), t);
		}
		return list;
	}

	private Plus buildPlusServiceForUser(String user) {
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		Credential credential = user == null ? AuthUtil.createOath2Credential(oAuth2Configuration, SCOPES) :
				AuthUtil.createOath2Credential(oAuth2Configuration, user, SCOPES);
		return new Plus.Builder(transport, jsonFactory, credential)
				.setApplicationName(oAuth2Configuration.getApplicationName()).build();
	}
}
