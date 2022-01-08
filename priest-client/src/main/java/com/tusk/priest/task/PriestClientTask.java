package com.tusk.priest.task;

import com.alibaba.fastjson.JSON;
import com.tusk.priest.pojo.PriestRegistration;
import com.tusk.priest.service.PriestRegisterClientService;
import com.tusk.priest.util.OkHttpUtil;
import com.tusk.priest.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class PriestClientTask {

    private static AtomicBoolean registered = new AtomicBoolean(false);

    @Autowired
    private PriestRegisterClientService priestRegisterClientService;

    public static void startRegisterTask(PriestRegistration priestRegistration) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if(PriestClientTask.registered.get()) {
                        log.debug("Priest is registered, skip.....");
                        return;
                    }

                    String url = ServerUtil.getRegisterUrl(priestRegistration.getPriestDiscoveryProperties().getServerAddr());
                    String res = OkHttpUtil.getInstance().doPostJson(url, JSON.toJSONString(priestRegistration.getPriestDiscoveryProperties()));
                    if(res.equals("")) throw new Exception("http post error");
                    PriestClientTask.registered.set(true);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    PriestClientTask.registered.set(false);
                }
            }
        }, 10, 5, TimeUnit.SECONDS);
    }

    public static void startHeartbeatTask(PriestRegistration priestRegistration) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!PriestClientTask.registered.get()) {
                        log.debug("Priest is not registered, skip.....");
                        return;
                    }
                    String url = ServerUtil.getHeartbeatUrl(priestRegistration.getPriestDiscoveryProperties().getServerAddr());
                    OkHttpUtil.getInstance().doPostJson(url, JSON.toJSONString(priestRegistration.getPriestDiscoveryProperties()));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    PriestClientTask.registered.set(false);
                }
            }
        }, 10, 5, TimeUnit.SECONDS);
    }
}
