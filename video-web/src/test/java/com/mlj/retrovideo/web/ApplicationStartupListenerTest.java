package com.mlj.retrovideo.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartupListenerTest {

    private ApplicationStartupListener listener;
    @Mock
    private ReplayingCluster replayingCluster;

    @Before
    public void givenAnApplicationStartupListener() {
        initMocks(this);
        listener = new ApplicationStartupListener(replayingCluster);
    }

    @Test
    public void itShouldReplayEventsWhenApplicationContextIsRefreshed() {
        listener.onApplicationEvent(mock(ContextRefreshedEvent.class));

        verify(replayingCluster).startReplay();
    }

}
