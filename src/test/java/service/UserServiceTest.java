package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.CreateUserResponse;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static utils.TestUtils.stringToArray;
import static utils.TestUtils.stringsToArray;

public class UserServiceTest extends AbsrtactTest {
    private final UserService userService = new UserServiceImpl();
    private static final String VALID_USER_LOGIN = "@test_user";
    private static final String VALID_USERNAME = "Anna";

    private static Stream<Arguments> supplyInvalidUsers() {
        return Stream.of(
                Arguments.of(null, stringToArray("User can't be null")),
                Arguments.of(new User("     ", VALID_USER_LOGIN), stringToArray("Username must not be empty or blank")),
                Arguments.of(new User("", VALID_USER_LOGIN), stringToArray("Username must not be empty or blank")),
                Arguments.of(new User("l", VALID_USER_LOGIN), stringToArray("Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User("lllllllllllllllllllll", VALID_USER_LOGIN), stringToArray("Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User(VALID_USERNAME, ""), stringToArray("Login must not be empty or blank")),
                Arguments.of(new User(VALID_USERNAME, "          "), stringToArray("Login must not be empty or blank")),
                Arguments.of(new User(VALID_USERNAME, "test_user"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "Test_user"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user1"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "t"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user________"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user*"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "Ty7"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "ty7"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "@test user"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "7776"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "****&"), stringToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User("k", "777"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters", "Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User("jjjjjjjjjjjjjjkkkkklkk", "@j"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters", "Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User("     ", "@ddf3"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters", "Username must not be empty or blank")),
                Arguments.of(new User("", "@d$f"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters", "Username must not be empty or blank")),
                Arguments.of(new User("D", ""), stringsToArray("Login must not be empty or blank", "Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", "          "), stringsToArray("Login must not be empty or blank", "Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed."))
        );
    }

    @ParameterizedTest
    @DisplayName("User should not be created if he is invalid")
    @MethodSource("supplyInvalidUsers")
    public void userShouldNotBeCreatedIfItsInvalid(User user, String[] expectedErrors) {
        CreateUserResponse createUserResponse = userService.saveUser(user);
        assertTrue(createUserResponse.getUserOptional().isEmpty());
        assertArrayEquals(expectedErrors, createUserResponse.getErrors().toArray());
    }

    @Test
    @DisplayName("User should be saved if he is completely valid")
    public void validUserShouldBeCreated() {
        User user = new User("Lena", "@anna");

        User expectedUser = user.clone();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setRegisteredSince(LocalDateTime.now());

        when(userDaoMock.save(user)).thenReturn(expectedUser);

        CreateUserResponse createUserResponse = userService.saveUser(user);
        User savedUser = createUserResponse.getUserOptional().get();

        assertTrue(createUserResponse.getUserOptional().isPresent());
        assertTrue(createUserResponse.getErrors().isEmpty());
        assertEquals(expectedUser, savedUser);
    }

    @Test
    @DisplayName("User should not be update if login has been taken by another user")
    public void userShouldBeCreatedIfLoginTaken() {
        String login = "@jjjj";
        User userWithTakenLogin = new User("ganna", login);
        userWithTakenLogin.setId(UUID.randomUUID());
        userWithTakenLogin.setRegisteredSince(LocalDateTime.now());

        User originalUser = new User("Kate", "@another_log");
        originalUser.setId(UUID.randomUUID());
        originalUser.setRegisteredSince(LocalDateTime.now());

        User originalUserToUpdate = originalUser.clone();
        originalUserToUpdate.setLogin(login);

        String[] expectedErrors = stringToArray("This login: " + login + " is already taken");
        when(userDaoMock.findById(originalUserToUpdate.getId())).thenReturn(Optional.of(originalUser));
        when(userDaoMock.findByLogin(originalUserToUpdate.getLogin())).thenReturn(Optional.of(userWithTakenLogin));

        CreateUserResponse createUserResponse = userService.saveUser(originalUserToUpdate);
        assertTrue(createUserResponse.getUserOptional().isEmpty());
        assertArrayEquals(expectedErrors, createUserResponse.getErrors().toArray());
    }

    @Test
    @DisplayName("User should be update if login has not be taken")
    public void userShouldBeUpdatedIfLoginIsAvailable() {
        String login = "@anna";
        String about = "Hello.";
        User userWithTakenLogin = new User("anna", login);
        userWithTakenLogin.setId(UUID.randomUUID());
        userWithTakenLogin.setRegisteredSince(LocalDateTime.now());

        User originalUser = new User("nikita", "@nik");
        originalUser.setId(UUID.randomUUID());
        originalUser.setRegisteredSince(LocalDateTime.now());

        User orginalUserToUpdate = originalUser.clone();
        orginalUserToUpdate.setAbout(about);

        when(userDaoMock.findById(orginalUserToUpdate.getId())).thenReturn(Optional.of(originalUser));
        when(userDaoMock.save(orginalUserToUpdate)).thenReturn(orginalUserToUpdate);

        CreateUserResponse createUserResponse = userService.saveUser(orginalUserToUpdate);
        assertTrue(createUserResponse.getErrors().isEmpty());

        User updatedUser = createUserResponse.getUserOptional().get();
        assertEquals(originalUser.getId(), updatedUser.getId());
        assertEquals(originalUser.getLogin(), updatedUser.getLogin());
        assertNotEquals(originalUser.getAbout(), updatedUser.getAbout());
        assertEquals(about, updatedUser.getAbout());
    }

    @Test
    @DisplayName("User must not be subscribe yourself")
    public void userMustNotBeSubscribeYourSelf() {
        User initialUser = new User("ann", "@ann");
        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());

        assertEquals(initialUser, initialUser);
        assertTrue(initialUser.getId().equals(initialUser.getId()));
        assertTrue(initialUser.getLogin().equals(initialUser.getLogin()));
        assertTrue(initialUser.getRegisteredSince().equals(initialUser.getRegisteredSince()));
    }

    @Test
    @DisplayName("InitialUser must not be subscribe because invalid userId")
    public void initialUserMustNotBeSubscribeInvalidUserId() {
        User subscriberUser = new User("ann", "@ann");
        User initialUser = null;
        Optional initialUserOptional = Optional.empty();
        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        assertTrue(initialUserOptional.isEmpty());
        assertNotEquals(initialUser, subscriberUser);
    }

    @Test
    @DisplayName("SubscribeUser must not be subscribe because invalid userId")
    public void subscribeUserMustNotBeSubscribeInvalidUserId() {
        User initialUser = new User("aaan", "@an");
        User subscriberUser = null;
        Optional subscriberUserOptional = Optional.empty();
        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());

        assertTrue(subscriberUserOptional.isEmpty());
        assertNotEquals(initialUser, subscriberUser);
    }

    @Test
    @DisplayName("Subscribe")
    public void subscribe() {
        User initialUser = new User("lera", "@Lera");
        User subscriberUser = new User("igor", "@Igor");

        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());
        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        User updateInitialUser = initialUser.clone();
        User updateSubscriberUser = subscriberUser.clone();

        updateInitialUser.getFollowingIds().add(subscriberUser.getId());
        updateSubscriberUser.getFollowerIds().add(initialUser.getId());

        when(userDaoMock.save(initialUser)).thenReturn(updateInitialUser);
        when(userDaoMock.save(subscriberUser)).thenReturn(updateSubscriberUser);

        userDaoMock.save(initialUser);
        userDaoMock.save(subscriberUser);

        assertNotEquals(updateInitialUser, updateSubscriberUser);
        assertTrue(updateInitialUser.getFollowingIds().contains(updateSubscriberUser.getId()));
        assertTrue(updateSubscriberUser.getFollowerIds().contains(updateInitialUser.getId()));
    }

    @Test
    @DisplayName("Unsubscribe")
    public void unsubscribe() {
        User initialUser = new User("nik", "@nik");
        User subscriberUser = new User("oly", "@oly");

        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());
        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        User updateInitialUser = initialUser.clone();
        User updateSubscriberUser = subscriberUser.clone();

        updateInitialUser.getFollowingIds().add(subscriberUser.getId());
        updateSubscriberUser.getFollowerIds().add(initialUser.getId());

        updateInitialUser.getFollowingIds().remove(subscriberUser.getId());
        updateSubscriberUser.getFollowerIds().remove(initialUser.getId());

        when(userDaoMock.save(initialUser)).thenReturn(updateInitialUser);
        when(userDaoMock.save(subscriberUser)).thenReturn(updateSubscriberUser);

        userDaoMock.save(initialUser);
        userDaoMock.save(subscriberUser);

        assertNotEquals(updateInitialUser, updateSubscriberUser);
        assertFalse(updateInitialUser.getFollowingIds().contains(updateSubscriberUser.getId()));
        assertFalse(updateSubscriberUser.getFollowerIds().contains(updateInitialUser.getId()));
    }

    @Test
    @DisplayName("Find by Ids. Set Ids is not empty")
    public void findByIds() {
        User firstUser = new User("gena", "@gena");
        firstUser.setId(UUID.randomUUID());
        User secondUser = new User("pety", "@pety");
        secondUser.setId(UUID.randomUUID());

        Set<User> usersSet = new HashSet<>();
        usersSet.add(firstUser);
        usersSet.add(secondUser);

        Set<UUID> uuidsSet = new HashSet<>();
        uuidsSet.add(firstUser.getId());
        uuidsSet.add(secondUser.getId());

        when(userDaoMock.findByIds(uuidsSet)).thenReturn(usersSet);

        assertEquals(userDaoMock.findByIds(uuidsSet), usersSet);
    }

    @Test
    @DisplayName("Find by Ids. Set Ids is empty")
    public void findByIdsSetIdsIsEmpty() {
        Set<UUID> uuidsSet = new HashSet<>();

        assertTrue(uuidsSet.isEmpty());
        assertEquals(uuidsSet, Collections.emptySet());
    }

    @Test
    @DisplayName("Exist by Id")
    public void existById() {
        User user = new User("vov", "@vov");
        user.setId(UUID.randomUUID());

        when(userDaoMock.existsById(user.getId())).thenReturn(true);

        assertTrue(userDaoMock.existsById(user.getId()));
    }

    @Test
    @DisplayName("Find by Login")
    public void findByLogin() {
        User user = new User("lera", "@lera");
        user.setId(UUID.randomUUID());

        when(userDaoMock.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

        assertEquals(user, userDaoMock.findByLogin(user.getLogin()).get());
        assertFalse(userDaoMock.findByLogin(user.getLogin()).isEmpty());
    }

    @Test
    @DisplayName("Delete")
    public void delete() {
        User user = new User("ira", "@ira");
        user.setId(UUID.randomUUID());

        assertEquals(userDaoMock.delete(user), null);
    }
}