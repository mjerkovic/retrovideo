package com.mlj.retrovideo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.UUID;

import com.mlj.retrovideo.domain.AddVideo;
import com.mlj.retrovideo.domain.VideoService;
import com.mlj.retrovideo.domain.VideoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping(method = POST, value = "/video", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@RequestBody VideoDto videoDto) {
        videoService.addVideo(new AddVideo(UUID.randomUUID().toString(), videoDto.getTitle(), videoDto.getYear(),
                videoDto.getDuration()));
    }

    @RequestMapping(method = GET, value = "/video", produces = "application/json")
    @ResponseBody
    public List<VideoView> all() {
        return videoService.allVideos();
    }

    @RequestMapping(method = GET, value = "/video/{videoId}", produces = "application/json")
    @ResponseBody
    public VideoView byId(@PathVariable String videoId) {
        return videoService.byId(videoId);
    }

}
