package spring_file_storage.demo;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPayload {

    private final UUID id;

    private String username;
    private boolean admin;

    public static UserPayload fromUser(User user) {
        UserPayload payload = new UserPayload(user.getId());
        payload.setAdmin(user.isAdmin());
        payload.setUsername(user.getUsername());

        return payload;
    }

}
