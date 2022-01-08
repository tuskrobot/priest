package com.tusk.priest.event;

import com.tusk.priest.pojo.PriestDiscoveryProperties;
import com.tusk.priest.pojo.PriestRegistration;
import org.springframework.context.ApplicationEvent;

public class PriestServerRegisterEvent extends ApplicationEvent {

    public PriestDiscoveryProperties discoveryProperties;

    public PriestServerRegisterEvent(Object source) {
        super(source);
    }

    public PriestServerRegisterEvent(Object source, PriestDiscoveryProperties discoveryProperties) {
        super(source);
        this.discoveryProperties = discoveryProperties;
    }

    public PriestDiscoveryProperties getDiscoveryProperties() {
        return discoveryProperties;
    }

    public void setDiscoveryProperties(PriestDiscoveryProperties discoveryProperties) {
        this.discoveryProperties = discoveryProperties;
    }
}
