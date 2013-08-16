package com.mlj.retrovideo.video.domain;

import com.mlj.retrovideo.repository.FacetResults;
import com.mlj.retrovideo.repository.ItemList;
import com.mlj.retrovideo.video.VideoView;
import com.mlj.retrovideo.video.command.AddStock;
import com.mlj.retrovideo.video.command.AddVideo;
import com.mlj.retrovideo.video.repository.ElasticVideoRepository;
import com.mlj.retrovideo.video.repository.JdbcVideoRepository;
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

    public ItemList<VideoView> videosForPage(int pageNo) {
        //return videoRepository.videosForPage();
        return elasticVideoRepository.videosForPage(pageNo);
    }

    public VideoView byId(String videoId) {
        return videoRepository.byId(videoId);
    }

    public FacetResults totalsFor(String category) {
        return elasticVideoRepository.totalsFor(category);
    }

    public void addStock(AddStock addStock) {
        commandGateway.send(addStock);
    }

}
