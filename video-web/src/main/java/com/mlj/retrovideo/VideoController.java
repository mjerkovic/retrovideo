package com.mlj.retrovideo;

import com.mlj.retrovideo.domain.AddVideo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class VideoController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.POST, value = "/perform/addVideo", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@RequestBody VideoDto videoDto) {
        commandGateway.send(new AddVideo(videoDto.getVideoId(), videoDto.getTitle()));
    }

}
