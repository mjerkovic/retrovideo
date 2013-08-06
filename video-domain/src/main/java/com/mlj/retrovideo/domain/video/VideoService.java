package com.mlj.retrovideo.domain.video;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final CommandGateway commandGateway;
    private final JdbcVideoRepository videoRepository;
    private final ElasticVideoRepository elasticVideoRepository;

    @Autowired
    public VideoService(CommandGateway commandGateway, JdbcVideoRepository videoRepository,
                        ElasticVideoRepository elasticVideoRepository) {
        this.commandGateway = commandGateway;
        this.videoRepository = videoRepository;
        this.elasticVideoRepository = elasticVideoRepository;
    }

    public void addVideo(AddVideo videoCommand) {
        commandGateway.send(videoCommand);
    }

    public List<VideoView> allVideos() {
        //return videoRepository.all();
        return elasticVideoRepository.all();
    }

    public VideoView byId(String videoId) {
        return videoRepository.byId(videoId);
    }

}
