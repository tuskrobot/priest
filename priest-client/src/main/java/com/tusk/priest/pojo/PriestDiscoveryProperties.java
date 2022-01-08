package com.tusk.priest.pojo;

import com.tusk.priest.constants.PreservedMetadataKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PriestDiscoveryProperties {

    @Autowired
    private InetUtils inetUtils;
    @Autowired
    private Environment environment;

    /**
     * group name for priest.
     */
    private String group = "DEFAULT_GROUP";

    @Value("${spring.cloud.priest.discovery.service:${spring.application.name:}}")
    private String service;


    /**
     * extra metadata to register.
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * The ip address your want to register for your service instance, needn't to set it
     * if the auto detect ip works well.
     */
    private String ip;

    /**
     * The port your want to register for your service instance, needn't to set it if the
     * auto detect port works well.
     */
    private int port = -1;

    /**
     * Heart beat interval. Time unit: millisecond.
     */
    private Integer heartBeatInterval;

    /**
     * Heart beat timeout. Time unit: millisecond.
     */
    private Integer heartBeatTimeout;

    /**
     * Ip delete timeout. Time unit: millisecond.
     */
    private Integer ipDeleteTimeout;

    /**
     * If instance is enabled to accept request. The default value is true.
     */
    private boolean instanceEnabled = true;

    /**
     * whether your service is a https service.
     */
    private boolean secure = false;

    /**
     * priest discovery server address.
     */
    private String serverAddr;

    /**
     * which network interface's ip you want to register.
     */
    private String networkInterface = "";



    @PostConstruct
    public void init() throws Exception {
        metadata.put(PreservedMetadataKeys.REGISTER_SOURCE, "PRIEST");
        if (secure) {
            metadata.put("secure", "true");
        }

        serverAddr = Objects.toString(serverAddr, "");
        if (serverAddr.endsWith("/")) {
            serverAddr = serverAddr.substring(0, serverAddr.length() - 1);
        }
//        endpoint = Objects.toString(endpoint, "");
//        namespace = Objects.toString(namespace, "");
//        logName = Objects.toString(logName, "");

        if (StringUtils.isEmpty(ip)) {
            // traversing network interfaces if didn't specify a interface
            if (StringUtils.isEmpty(networkInterface)) {
                ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            }
            else {
                NetworkInterface netInterface = NetworkInterface.getByName(networkInterface);
                if (null == netInterface) {
                    throw new IllegalArgumentException("no such interface " + networkInterface);
                }

                Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses();
                while (inetAddress.hasMoreElements()) {
                    InetAddress currentAddress = inetAddress.nextElement();
                    if (currentAddress instanceof Inet4Address
                            && !currentAddress.isLoopbackAddress()) {
                        ip = currentAddress.getHostAddress();
                        break;
                    }
                }

                if (StringUtils.isEmpty(ip)) {
                    throw new RuntimeException("cannot find available ip from network interface " + networkInterface);
                }

            }
        }



        this.overrideFromEnv(environment);
/*        if (priestServiceManager.ispriestDiscoveryInfoChanged(this)) {
            applicationEventPublisher
                    .publishEvent(new priestDiscoveryInfoChangedEvent(this));
        }*/
    }


    public void overrideFromEnv(Environment env) {
        if (StringUtils.isEmpty(this.getServerAddr())) {
            String serverAddr = env.resolvePlaceholders("${spring.cloud.priest.discovery.server-addr:}");
            if (StringUtils.isEmpty(serverAddr)) serverAddr = env.resolvePlaceholders("${spring.cloud.priest.server-addr:localhost:7654}");
            this.setServerAddr(serverAddr);
        }

        if (StringUtils.isEmpty(this.getGroup())) {
            this.setGroup(env.resolvePlaceholders("${spring.cloud.priest.discovery.group:}"));
        }

        if (StringUtils.isEmpty(this.getMetadata().get(PreservedMetadataKeys.REDIS_HOST))) {
            String redisHost = env.resolvePlaceholders("${spring.cloud.priest.redis.host:}");
            if(StringUtils.isEmpty(redisHost)) redisHost = env.resolvePlaceholders("${spring.redis.host:}");
            this.getMetadata().put(PreservedMetadataKeys.REDIS_HOST, redisHost);
        }
        if (StringUtils.isEmpty(this.getMetadata().get(PreservedMetadataKeys.REDIS_DATABASE))) {
            String redisDb = env.resolvePlaceholders("${spring.cloud.priest.redis.database:}");
            if(StringUtils.isEmpty(redisDb)) redisDb = env.resolvePlaceholders("${spring.redis.database:}");
            this.getMetadata().put(PreservedMetadataKeys.REDIS_DATABASE, redisDb);
        }
        if (StringUtils.isEmpty(this.getMetadata().get(PreservedMetadataKeys.REDIS_PASSWORD))) {
            String redisPassword = env.resolvePlaceholders("${spring.cloud.priest.redis.password:}");
            if(StringUtils.isEmpty(redisPassword)) redisPassword = env.resolvePlaceholders("${spring.redis.password:}");
            this.getMetadata().put(PreservedMetadataKeys.REDIS_PASSWORD, redisPassword);
        }
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(Integer heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public Integer getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    public void setHeartBeatTimeout(Integer heartBeatTimeout) {
        this.heartBeatTimeout = heartBeatTimeout;
    }

    public Integer getIpDeleteTimeout() {
        return ipDeleteTimeout;
    }

    public void setIpDeleteTimeout(Integer ipDeleteTimeout) {
        this.ipDeleteTimeout = ipDeleteTimeout;
    }

    public boolean isInstanceEnabled() {
        return instanceEnabled;
    }

    public void setInstanceEnabled(boolean instanceEnabled) {
        this.instanceEnabled = instanceEnabled;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
