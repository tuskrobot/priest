package com.tusk.priest.event;

import com.tusk.priest.pojo.PriestRegistration;
import org.springframework.context.ApplicationEvent;

public class PriestClientRegisterEvent extends ApplicationEvent {

    public PriestRegistration priestRegistration;

    public PriestClientRegisterEvent(Object source) {
        super(source);
    }

    public PriestClientRegisterEvent(Object source, PriestRegistration priestRegistration) {
        super(source);
        this.priestRegistration = priestRegistration;
    }

    public PriestRegistration getPriestRegistration() {
        return priestRegistration;
    }

    public void setPriestRegistration(PriestRegistration priestRegistration) {
        this.priestRegistration = priestRegistration;
    }
}
