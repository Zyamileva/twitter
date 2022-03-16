package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.CreateUserResponse;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static utils.TestUtils.stringsToArray;

public class UserServiceTest extends AbsrtactTest {
    private final UserService userService = new UserServiceImpl();
    private static final String VALID_USER_LOGIN = "@test_user";
    private static final String VALID_USERNAME = "Anna";

    private static Stream<Arguments> supplyInvalidUsers() {
        return Stream.of(
                Arguments.of(null, stringsToArray("User can't be null")),
                Arguments.of(new User("     ", VALID_USER_LOGIN), stringsToArray("Username must not be empty or blank")),
                Arguments.of(new User("", VALID_USER_LOGIN), stringsToArray("Username must not be empty or blank")),
                Arguments.of(new User("l", VALID_USER_LOGIN), stringsToArray("Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User("lllllllllllllllllllll", VALID_USER_LOGIN), stringsToArray("Username should have length from 2 to 20 characters inclusive. Spaces, numbers and other characters allowed.")),
                Arguments.of(new User(VALID_USERNAME, ""), stringsToArray("Login must not be empty or blank")),
                Arguments.of(new User(VALID_USERNAME, "          "), stringsToArray("Login must not be empty or blank")),
                Arguments.of(new User(VALID_USERNAME, "test_user"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "Test_user"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user1"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "t"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user________"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user*"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "Ty7"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "ty7"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "@test user"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "test_user"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "7776"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
                Arguments.of(new User(VALID_USERNAME, "****&"), stringsToArray("Login must start with the @ sign. It can contain an underscore character and at least one lowercase letter. It must be at least 3 and no more than 14 characters")),
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
    public void userShouldBeUpdatedIfLoginTaken() {
        String login = "@jjjj";
        User userWithTakenLogin = new User("ganna", login);
        userWithTakenLogin.setId(UUID.randomUUID());
        userWithTakenLogin.setRegisteredSince(LocalDateTime.now());

        User originalUser = new User("Kate", "@another_log");
        originalUser.setId(UUID.randomUUID());
        originalUser.setRegisteredSince(LocalDateTime.now());

        User originalUserToUpdate = originalUser.clone();
        originalUserToUpdate.setLogin(login);

        String[] expectedErrors = stringsToArray("This login: " + login + " is already taken");
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

        assertFalse(userService.subscribe(initialUser.getId(), initialUser.getId()));
    }

    @Test
    @DisplayName("InitialUser must not be subscribe because invalid userId")
    public void initialUserMustNotBeSubscribeInvalidUserId() {
        User subscriberUser = new User("ann", "@ann");
        UUID wrongId = UUID.randomUUID();

        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        when(userDaoMock.findById(wrongId)).thenReturn(Optional.empty());

        assertFalse(userService.subscribe(subscriberUser.getId(), wrongId));
    }

    @Test
    @DisplayName("SubscribeUser must not be subscribe because invalid userId")
    public void subscribeUserMustNotBeSubscribeInvalidUserId() {
        User initialUser = new User("aaan", "@an");
        UUID wrongId = UUID.randomUUID();

        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());

        when(userDaoMock.findById(wrongId)).thenReturn(Optional.empty());

        assertFalse(userService.subscribe(wrongId, initialUser.getId()));
    }

    @Test
    @DisplayName("The subscription mechanism should work without errors if all the data is correct")
    public void subscriberUser() {
        User initialUser = new User("lera", "@lera");
        User subscriberUser = new User("igor", "@igor");

        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());
        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        when(userDaoMock.findById(initialUser.getId())).thenReturn(Optional.of(initialUser));
        when(userDaoMock.findById(subscriberUser.getId())).thenReturn(Optional.of(subscriberUser));
        when(userDaoMock.save(initialUser)).thenReturn(initialUser);
        when(userDaoMock.save(subscriberUser)).thenReturn(subscriberUser);

        assertTrue(userService.subscribe(initialUser.getId(), subscriberUser.getId()));
        assertTrue(initialUser.getFollowingIds().contains(subscriberUser.getId()));
        assertTrue(subscriberUser.getFollowerIds().contains(initialUser.getId()));
    }

    @Test
    @DisplayName("The unsubscription mechanism should work without errors if all the data is correct")
    public void unsubscribeUser() {
        User initialUser = new User("nik", "@nik");
        User subscriberUser = new User("oly", "@oly");

        initialUser.setId(UUID.randomUUID());
        initialUser.setRegisteredSince(LocalDateTime.now());
        subscriberUser.setId(UUID.randomUUID());
        subscriberUser.setRegisteredSince(LocalDateTime.now());

        initialUser.getFollowingIds().add(subscriberUser.getId());
        subscriberUser.getFollowerIds().add(initialUser.getId());

        when(userDaoMock.findById(initialUser.getId())).thenReturn(Optional.of(initialUser));
        when(userDaoMock.findById(subscriberUser.getId())).thenReturn(Optional.of(subscriberUser));
        when(userDaoMock.save(initialUser)).thenReturn(initialUser);
        when(userDaoMock.save(subscriberUser)).thenReturn(subscriberUser);

        assertTrue(userService.unsubscribe(initialUser.getId(), subscriberUser.getId()));
        assertFalse(initialUser.getFollowingIds().contains(subscriberUser.getId()));
        assertFalse(subscriberUser.getFollowerIds().contains(initialUser.getId()));
    }

    @Test
    @DisplayName("Find by ids should return all users with valid id")
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

        assertEquals(userService.findByIds(uuidsSet), usersSet);
    }

    @Test
    @DisplayName("Search by IDs when passed Set IDs is empty")
    public void findByIdsSetIdsIsEmpty() {
        Set<UUID> uuidsSet = Collections.emptySet();

        assertEquals(userService.findByIds(uuidsSet), Collections.emptySet());
    }

    @Test
    @DisplayName("Exist by Id")
    public void existById() {
        User user = new User("vov", "@vov");
        user.setId(UUID.randomUUID());

        when(userDaoMock.existsById(user.getId())).thenReturn(true);

        assertTrue(userService.existById(user.getId()));
    }

    @Test
    @DisplayName("Find by Login when invalid login")
    public void findByInvalidLogin() {
        String invalidLogin = "Login";

        when(userDaoMock.findByLogin(invalidLogin)).thenReturn(Optional.empty());

        assertEquals(userService.findByLogin(invalidLogin), Optional.empty());
    }

    @Test
    @DisplayName("Find by Login")
    public void findByLogin() {
        User user = new User("lera", "@lera");
        user.setId(UUID.randomUUID());

        when(userDaoMock.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

        assertEquals(userService.findByLogin(user.getLogin()), Optional.of(user));
    }

    @Test
    @DisplayName("Find by id when invalid id")
    public void findByInvalidId() {
        UUID invalidId = UUID.randomUUID();

        when(userDaoMock.findById(invalidId)).thenReturn(Optional.empty());

        assertEquals(userService.findById(invalidId), Optional.empty());
    }

    @Test
    @DisplayName("Find by id")
    public void findById() {
        User user = new User("lesy", "@lesy");
        user.setId(UUID.randomUUID());

        when(userDaoMock.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(userService.findById(user.getId()), Optional.of(user));
    }

    @Test
    @DisplayName("Delete user")
    public void delete() {
        User user = new User("ira", "@ira");
        user.setId(UUID.randomUUID());

        when(userDaoMock.delete(user)).thenReturn(user);

        assertEquals(userDaoMock.delete(user), user);
    }
}