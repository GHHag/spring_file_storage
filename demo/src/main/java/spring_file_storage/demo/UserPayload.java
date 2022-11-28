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

    public static UserPayload fromUser(User user) {
        UserPayload payload = new UserPayload(user.getId());
        payload.setUsername(user.getUsername());

        return payload;
    }

}
