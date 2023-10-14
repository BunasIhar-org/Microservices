package com.itm.space.backendresources.service;

import com.itm.space.backendresources.api.request.*;
import com.itm.space.backendresources.api.response.*;
import com.itm.space.backendresources.exception.*;
import com.itm.space.backendresources.mapper.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloakClient;

    private final UserMapper userMapper;

    @Value("${keycloak.realm}")
    private String realm;

    public void createUser(UserRequest userRequest) {
        CredentialRepresentation password = preparePasswordRepresentation(userRequest.getPassword());
        UserRepresentation user = prepareUserRepresentation(userRequest, password);
        try {
            Response response = keycloakClient.realm(realm).users().create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);
            log.info("Created UserId: {}", userId);
        } catch (WebApplicationException ex) {
            log.error("Exception on \"createUser\": ", ex);
            throw new BackendResourcesException(ex.getMessage(), HttpStatus.resolve(ex.getResponse().getStatus()));
        }
    }

    @Override
    public UserResponse getUserById(UUID id) {
        UserRepresentation userRepresentation;
        List<RoleRepresentation> userRoles;
        List<GroupRepresentation> userGroups;
        try {
            userRepresentation = keycloakClient.realm(realm).users().get(String.valueOf(id)).toRepresentation();
            userRoles = keycloakClient.realm(realm)
                    .users().get(String.valueOf(id)).roles().getAll().getRealmMappings();
            userGroups = keycloakClient.realm(realm).users().get(String.valueOf(id)).groups();
        } catch (RuntimeException ex) {
            log.error("Exception on \"getUserById\": ", ex);
            throw new BackendResourcesException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userMapper.userRepresentationToUserResponse(userRepresentation, userRoles, userGroups);
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UserRepresentation prepareUserRepresentation(UserRequest userRequest,
                                                         CredentialRepresentation credentialRepresentation) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(userRequest.getUsername());
        newUser.setEmail(userRequest.getEmail());
        newUser.setCredentials(List.of(credentialRepresentation));
        newUser.setEnabled(true);
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        return newUser;
    }
}