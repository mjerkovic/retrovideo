package com.mlj.retrovideo;

import com.mlj.retrovideo.domain.AddVideo;
import com.mlj.retrovideo.domain.JdbcVideoRepository;
import com.mlj.retrovideo.domain.VideoView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class VideoController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private JdbcVideoRepository videosRepository;

    @RequestMapping(method = POST, value = "/perform/addVideo", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@RequestBody VideoDto videoDto) {
        commandGateway.send(new AddVideo(videoDto.getVideoId(), videoDto.getTitle(), videoDto.getYear(),
                videoDto.getDuration()));
    }

    @RequestMapping(method = GET, value = "/query", produces = "application/json")
    @ResponseBody
    public List<VideoView> all() {
        List<VideoView> videos = videosRepository.all();
        return videos;
    }

    @RequestMapping(method = GET, value = "/query/{videoId}", produces = "application/json")
    @ResponseBody
    public VideoView byId(@PathVariable String videoId) {
        return videosRepository.byId(videoId);
    }

}
