package com.mlj.retrovideo.web.video;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import com.mlj.retrovideo.domain.video.AddVideo;
import com.mlj.retrovideo.domain.video.VideoBreakdown;
import com.mlj.retrovideo.domain.video.VideoList;
import com.mlj.retrovideo.domain.video.VideoService;
import com.mlj.retrovideo.domain.video.VideoView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class VideoController {

    private final VideoService videoService;
    private final ReplayingCluster replayingCluster;
    private final ObjectMapper objectMapper;

    @Autowired
    public VideoController(VideoService videoService, ReplayingCluster replayingCluster, ObjectMapper objectMapper) {
        this.videoService = videoService;
        this.replayingCluster = replayingCluster;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(method = POST, value = "/video", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@RequestBody VideoDto videoDto) {
        videoService.addVideo(new AddVideo(UUID.randomUUID().toString(), WordUtils.capitalizeFully(videoDto.getTitle()),
                videoDto.getYear(), videoDto.getCountry(), videoDto.getDuration()));
    }

    @RequestMapping(method = POST, value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(UploadItem item) {
        System.out.println("file.getName() = " + item.getFile().getName());
        StringReader reader = null;
        BufferedReader br = null;
        VideoDto videoDto = null;
        String videoJson;
        try {
            reader = new StringReader(new String(item.getFile().getBytes()));
            br = new BufferedReader(reader);
            while ((videoJson = br.readLine()) != null) {
                videoDto = objectMapper.readValue(videoJson, VideoDto.class);
                videoService.addVideo(new AddVideo(UUID.randomUUID().toString(), WordUtils.capitalizeFully(videoDto.getTitle()),
                        videoDto.getYear(), videoDto.getCountry(), videoDto.getDuration()));
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JsonParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(reader);
        }
    }

    @RequestMapping(method = GET, value = "/video", produces = "application/json")
    @ResponseBody
    public VideoList allVideos() {
        return videoService.all();
    }

    @RequestMapping(method = GET, value = "/video/page/{pageNo}", produces = "application/json")
    @ResponseBody
    public VideoList videosByPage(@PathVariable int pageNo, @RequestParam(value = "searchKey") String searchKey) {
        return videoService.videosForPage(pageNo, searchKey);
    }

    @RequestMapping(method = GET, value = "/video/stats/country", produces = "application/json")
    @ResponseBody
    public VideoBreakdown totalsByCountry() {
        return videoService.totalsByCountry();
    }

    @RequestMapping(method = GET, value = "/video/{videoId}", produces = "application/json")
    @ResponseBody
    public VideoView byId(@PathVariable String videoId) {
        return videoService.byId(videoId);
    }

    @RequestMapping(method = PUT, value="replayAll")
    @ResponseStatus(HttpStatus.OK)
    public void replayEvents() {
        replayingCluster.startReplay();
    }

}
