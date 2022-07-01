package me.dan.orbittrial.user;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.user.data.UserData;

import java.util.UUID;

@Getter
@Setter
public class EcoUserData extends UserData {

    private double balance = 0;
    private long lastEarnTime = 0;

    public EcoUserData(UUID uuid) {
        super(uuid);
    }
}
