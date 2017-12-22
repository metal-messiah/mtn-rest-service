package com.mtn.test;

import com.mtn.BaseTest;
import com.mtn.model.CustomPageImpl;
import com.mtn.model.view.UserProfileView;
import com.mtn.model.view.GroupView;
import com.mtn.model.view.RoleView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseTest {

    @Test(expectedExceptions = {RestClientException.class}, dataProvider = "endpointMappings")
    public void eachEndpointShouldReturn401IfNotAuthenticated(HttpMethod httpMethod, String path) {
        try {
            unauthorizedRestTemplate().exchange(buildUri(path), httpMethod, new HttpEntity<>(new UserProfileView()), String.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class}, dataProvider = "invalidPostRequestObjects")
    public void postUserShouldReturn400IfInvalidRequest(UserProfileView userProfileView) {
        try {
            restTemplate().postForObject(buildUri("/api/user"), userProfileView, UserProfileView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 400);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void postUserShouldReturn400IfEmailAlreadyExists() {
        //Step 1 - Create valid user
        UserProfileView request = new UserProfileView();
        request.setEmail("emailalreadyexists@email.com");
        request.setFirstName("Test");
        request.setLastName("Test");

        restTemplate().postForEntity(buildUri("/api/user"), request, UserProfileView.class);

        //Step 2 - Attempt to create duplicate user
        try {
            restTemplate().postForEntity(buildUri("/api/user"), request, UserProfileView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 400);
            throw e;
        }
    }

    @Test
    public void postUserShouldReturn200WithFullyPopulatedUserProfile() {
        //Step 1 - Create group and role
        GroupView groupRequest = new GroupView();
        groupRequest.setDisplayName("Valid User Test Group");
        ResponseEntity<GroupView> groupResponse = restTemplate().postForEntity(buildUri("/api/group"), groupRequest, GroupView.class);
        GroupView group = groupResponse.getBody();

        RoleView roleRequest = new RoleView();
        roleRequest.setDisplayName("Valid User Test Role");
        ResponseEntity<RoleView> roleResponse = restTemplate().postForEntity(buildUri("/api/role"), roleRequest, RoleView.class);
        RoleView role = roleResponse.getBody();

        //Step 2 - Create user
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("validrequest@email.com");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");
        userRequest.setGroup(group);
        userRequest.setRole(role);

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertNotNull(user.getCreatedBy());
        Assert.assertNotNull(user.getCreatedDate());
        Assert.assertNotNull(user.getUpdatedBy());
        Assert.assertNotNull(user.getUpdatedDate());
        Assert.assertEquals(user.getEmail(), userRequest.getEmail());
        Assert.assertEquals(user.getFirstName(), userRequest.getFirstName());
        Assert.assertEquals(user.getLastName(), userRequest.getLastName());
        Assert.assertNotNull(user.getGroup());
        Assert.assertEquals(user.getGroup().getId(), group.getId());
        Assert.assertNotNull(user.getRole());
        Assert.assertEquals(user.getRole().getId(), role.getId());
    }

    @Test
    public void postUserShouldForceEmailToLowercase() {
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("LOWEREMAILTEST@EMAIL.COM");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        Assert.assertEquals(user.getEmail(), userRequest.getEmail().toLowerCase());
    }

    @Test
    public void deleteUserShouldSetDeletedByAndDeletedDateInDatabase() {
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("deleteusertest@email.com");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        restTemplate().delete(buildUri(String.format("/api/user/%d", user.getId())));

        String query = "SELECT user_profile_id, deleted_by, deleted_date FROM user_profile WHERE user_profile_id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());

        Map<String, Object> result = jdbcTemplate().queryForMap(query, params);
        Assert.assertNotNull(result.get("user_profile_id"));
        Assert.assertNotNull(result.get("deleted_by"));
        Assert.assertNotNull(result.get("deleted_date"));
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void deleteUserShouldReturn400IfNonExistentId() {
        try {
            restTemplate().delete(buildUri("/api/user/1000000"));
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 400);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void deleteUserShouldReturn400IfAlreadyDeleted() {
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("deleteusertwicetest@email.com");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        restTemplate().delete(buildUri(String.format("/api/user/%d", user.getId())));

        try {
            restTemplate().delete(buildUri(String.format("/api/user/%d", user.getId())));
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 400);
            throw e;
        }
    }

    @Test
    public void getUserShouldNotReturnDeletedResult() {
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("nodeleteduseringetalltest@email.com");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        restTemplate().delete(buildUri(String.format("/api/user/%d", user.getId())));

        ResponseEntity<CustomPageImpl<UserProfileView>> response = restTemplate().exchange(buildUri("/api/user"), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<UserProfileView>>() {
        });
        CustomPageImpl<UserProfileView> results = response.getBody();

        UserProfileView deletedResult = results.getContent().stream().filter(result -> result.getId().equals(user.getId())).findFirst().orElse(null);

        Assert.assertNull(deletedResult);
    }

    @Test
    public void getUserByIdShouldReturn204ForDeletedResult() {
        UserProfileView userRequest = new UserProfileView();
        userRequest.setEmail("nodeleteduseringettest@email.com");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");

        ResponseEntity<UserProfileView> userResponse = restTemplate().postForEntity(buildUri("/api/user"), userRequest, UserProfileView.class);
        UserProfileView user = userResponse.getBody();

        restTemplate().delete(buildUri(String.format("/api/user/%d", user.getId())));

        ResponseEntity<UserProfileView> response = restTemplate().getForEntity(buildUri(String.format("/api/user/%d", user.getId())), UserProfileView.class);
        UserProfileView result = response.getBody();

        Assert.assertEquals(response.getStatusCode().value(), 204);
        Assert.assertNull(result);
    }

    @Test(expectedExceptions = {RestClientException.class}, dataProvider = "invalidPutRequestObjects")
    public void putUserShouldReturn400IfInvalidRequest(UserProfileView userProfileView) {
        try {
            restTemplate().put(buildUri("/api/user/1"), userProfileView);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 400);
            throw e;
        }
    }

    @DataProvider(name = "endpointMappings")
    public static Object[][] endpointMappings() {
        return new Object[][]{
                {HttpMethod.POST, "/api/user"},
                {HttpMethod.GET, "/api/user"},
                {HttpMethod.GET, "/api/user/1"},
                {HttpMethod.DELETE, "/api/user/1"},
                {HttpMethod.PUT, "/api/user/1"}
        };
    }

    @DataProvider(name = "invalidPostRequestObjects")
    public static Object[][] invalidPostRequestObjects() {
        UserProfileView alreadyHasId = new UserProfileView();
        alreadyHasId.setId(1);
        alreadyHasId.setEmail("test@email.com");
        alreadyHasId.setFirstName("test");
        alreadyHasId.setLastName("test");

        UserProfileView missingEmail = new UserProfileView();
        missingEmail.setFirstName("test");
        missingEmail.setLastName("test");

        return new Object[][]{
                {alreadyHasId},
                {missingEmail}
        };
    }

    @DataProvider(name = "invalidPutRequestObjects")
    public static Object[][] invalidPutRequestObjects() {
        UserProfileView missingId = new UserProfileView();
        missingId.setEmail("test@email.com");
        missingId.setFirstName("test");
        missingId.setLastName("test");

        UserProfileView missingEmail = new UserProfileView();
        missingEmail.setFirstName("test");
        missingEmail.setLastName("test");

        return new Object[][]{
                {missingId},
                {missingEmail}
        };
    }
}
