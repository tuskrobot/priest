package com.tusk.priest.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jarvis
 */
@Component
@NoArgsConstructor
public class ServerUtil {

    public static String getRegisterUrl(String serverAddr) {
        return getUrl(serverAddr, "register");
    }

    public static String getHeartbeatUrl(String serverAddr) {
        return getUrl(serverAddr, "heartbeat");
    }

    public static String getUrl(String serverAddr, String method) {
        return "http://" + serverAddr + "/priest/"+ method;
    }


}
