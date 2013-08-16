package com.mlj.retrovideo.video;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.mlj.retrovideo.video.command.AddVideo;
import com.mlj.retrovideo.video.domain.VideoService;
import com.mlj.retrovideo.video.repository.ElasticVideoRepository;
import com.mlj.retrovideo.video.repository.JdbcVideoRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class VideoServiceTest {

    private VideoService videoService;
    @Mock
    private CommandGateway commandGateway;
    @Mock
    private JdbcVideoRepository videoRepository;
    @Mock
    private ElasticVideoRepository elasticVideoRepository;

    @Before
    public void givenAVideoService() throws Exception {
        initMocks(this);
        videoService = new VideoService(commandGateway, videoRepository, elasticVideoRepository);
    }

    @Test
    public void itShouldSendAnAddVideoCommand() throws Exception {
        AddVideo videoCommand = mock(AddVideo.class);

        videoService.addVideo(videoCommand);

        verify(commandGateway).send(videoCommand);
    }

    @Test
    public void itShouldReturnAllVideos() throws Exception {
        //VideoList allVideos = new VideoList(1, 2l, newArrayList(mock(VideoView.class), mock(VideoView.class)));
        //when(videoRepository.videosForPage()).thenReturn(videosForPage);
        //when(elasticVideoRepository.videosForPage(1, "searchKey")).thenReturn(allVideos);

        //assertThat(videoService.videosForPage(1, "searchKey"), is(equalTo(allVideos)));
    }

    @Test
    public void itShouldReturnVideoForGivenId() throws Exception {
        VideoView video = mock(VideoView.class);
        when(videoRepository.byId("abc100")).thenReturn(video);

        assertThat(videoService.byId("abc100"), is(equalTo(video)));
    }

}
