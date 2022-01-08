package com.tusk.priest.pojo;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.util.Map;

public class PriestRegistration implements ServiceInstance {

    private PriestDiscoveryProperties priestDiscoveryProperties;

    private ApplicationContext context;

    public PriestRegistration(PriestDiscoveryProperties priestDiscoveryProperties, ApplicationContext context) {
        this.priestDiscoveryProperties = priestDiscoveryProperties;
        this.context = context;
    }

    public PriestDiscoveryProperties getPriestDiscoveryProperties() {
        return priestDiscoveryProperties;
    }


    @Override
    public String getServiceId() {
        return priestDiscoveryProperties.getService();
    }

    @Override
    public String getHost() {
        return priestDiscoveryProperties.getIp();
    }

    @Override
    public int getPort() {
        return priestDiscoveryProperties.getPort();
    }

    @Override
    public boolean isSecure() {
        return priestDiscoveryProperties.isSecure();
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public Map<String, String> getMetadata() {
        return priestDiscoveryProperties.getMetadata();
    }

    @Override
    public String toString() {
        return "PriestRegistration{" +
                "priestDiscoveryProperties=" + priestDiscoveryProperties +
                '}';
    }
}
