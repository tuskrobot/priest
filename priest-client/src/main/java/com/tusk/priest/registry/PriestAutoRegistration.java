package com.tusk.priest.registry;

import com.tusk.priest.pojo.PriestRegistration;
import com.tusk.priest.service.PriestRegisterClientService;
import com.tusk.priest.task.PriestClientTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * auto register to priest server if the client is imported
 * @author luosu
 * */
@Slf4j
public class PriestAutoRegistration implements ApplicationContextAware, ApplicationListener<WebServerInitializedEvent> {

    private PriestRegistration priestRegistration;

    private ApplicationContext context;

    private AtomicBoolean running = new AtomicBoolean(false);

    @Autowired
    private PriestRegisterClientService priestRegisterClientService;

    public PriestAutoRegistration(PriestRegistration priestRegistration) {
        this.priestRegistration = priestRegistration;
    }


    /**
     * get registration properties
     * upload
     */
//    @PostConstruct
    public void register() {
        priestRegisterClientService.register(this.priestRegistration);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        bind(event);
    }

    @Deprecated
    public void bind(WebServerInitializedEvent event) {
        // 调用了start()方法
        this.start();
    }


    public void start() {
        if(!this.running.get()) {
            this.register();
            this.running.compareAndSet(false, true);
            PriestClientTask.startRegisterTask(this.priestRegistration);
            PriestClientTask.startHeartbeatTask(this.priestRegistration);
        }
    }


}
