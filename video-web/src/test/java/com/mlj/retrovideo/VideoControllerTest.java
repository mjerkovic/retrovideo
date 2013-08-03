package com.mlj.retrovideo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.mlj.retrovideo.domain.AddVideo;
import com.mlj.retrovideo.domain.VideoService;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class VideoControllerTest {

    private VideoController controller;
    @Mock
    private VideoService videoService;
    @Mock
    private ReplayingCluster replayingCluster;

    @Before
    public void givenAVideoController() throws Exception {
        initMocks(this);
        controller = new VideoController(videoService, replayingCluster);
    }

    @Test
    public void itShouldDelegateToVideoServiceWhenAddingVideo() throws Exception {
        controller.addVideo(new VideoDto("title", 2013, 90));

        verify(videoService).addVideo(any(AddVideo.class));

    }

    @Test
    public void testAll() throws Exception {

    }

    @Test
    public void testById() throws Exception {

    }

    @Test
    public void testReplayEvents() throws Exception {

    }
}
