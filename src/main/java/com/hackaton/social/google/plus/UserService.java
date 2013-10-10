package com.hackaton.social.google.plus;

import static java.lang.String.*;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.roche.google.oauth2.SpringOAuth2Configuration;
import com.roche.google.util.AuthUtil;

/**
 * @author mdaleki
 */
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private static String[] SCOPES =
			{DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY, DirectoryScopes.ADMIN_DIRECTORY_ORGUNIT_READONLY,
					DirectoryScopes.ADMIN_DIRECTORY_GROUP_READONLY,
					DirectoryScopes.ADMIN_DIRECTORY_GROUP_MEMBER_READONLY};

	private final Directory directory;

	@Autowired
	public UserService(SpringOAuth2Configuration oAuth2Configuration) {
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		Credential credential = AuthUtil.createOath2Credential(oAuth2Configuration, Arrays.asList(SCOPES));
		this.directory = new Directory.Builder(transport, jsonFactory, credential)
				//				.setHttpRequestInitializer(new ExponentialBackOffRequestInitializer())
				.setApplicationName(oAuth2Configuration.getApplicationName()).build();
	}

	public User getUser(final String userKey) {
		try {
			return directory.users().get(userKey).execute();
		} catch (GoogleJsonResponseException e) {
			log.error(format("User %s not found in domain.", userKey));
		} catch (IOException e) {
			log.error(format("Can't get user %s", userKey), e);
		}
		return null;
	}
}
