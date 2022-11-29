package spring_file_storage.demo;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A Class that defines the payload with user data we want to pass to our
 * client.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Getter
@Setter
@RequiredArgsConstructor
public class UserPayload {

    private final UUID id;
    private String username;

    /**
     * Creates a new UserPayload from the given User object and returns it.
     * 
     * @param user - A User object to create a payload from.
     * @return - A UserPayload object.
     */
    public static UserPayload fromUser(User user) {
        UserPayload payload = new UserPayload(user.getId());
        payload.setUsername(user.getUsername());

        return payload;
    }

}
