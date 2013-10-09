package com.hackaton.social.google.plus;

import java.io.IOException;
import java.util.Arrays;
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
import com.roche.google.oauth2.SpringOAuth2Configuration;
import com.roche.google.util.AuthUtil;

/**
 * @author mdaleki
 */
@Service
public class CircleService {
	private static final Logger log = LoggerFactory.getLogger(CircleService.class);
	public static final String ME = "me";
	private static List<String> SCOPES =
			Arrays.asList(PlusScopes.PLUS_ME, PlusScopes.PLUS_STREAM_READ, PlusScopes.PLUS_CIRCLES_WRITE,
					PlusScopes.PLUS_CIRCLES_READ);

	private final Plus plus;
	private final SpringOAuth2Configuration oAuth2Configuration;

	@Autowired
	public CircleService(SpringOAuth2Configuration oAuth2Configuration) {
		this.oAuth2Configuration = oAuth2Configuration;
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		Credential credential = AuthUtil.createOath2Credential(oAuth2Configuration, SCOPES);
		this.plus = new Plus.Builder(transport, jsonFactory, credential)
				.setApplicationName(oAuth2Configuration.getApplicationName()).build();
	}

	public void loadCircles() throws IOException {
		loadCircles(ME);
	}

	public void loadCircles(final String user) throws IOException {
		CircleFeed circleFeed = buildPlusServiceForUser(user).circles().list(user).execute();
		for (Circle circle : circleFeed.getItems()) {
			log.debug("Found circle {} for {}", circle.getDisplayName(), user);
		}


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
