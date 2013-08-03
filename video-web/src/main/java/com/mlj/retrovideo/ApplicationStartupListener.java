package com.mlj.retrovideo;

import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ReplayingCluster replayingCluster;

    @Autowired
    public ApplicationStartupListener(ReplayingCluster replayingCluster) {
        this.replayingCluster = replayingCluster;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        replayingCluster.startReplay();
    }

}
