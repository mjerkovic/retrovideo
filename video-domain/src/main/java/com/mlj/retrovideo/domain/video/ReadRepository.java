package com.mlj.retrovideo.domain.video;

import java.util.List;

public interface ReadRepository {

    VideoView byId(String videoId);

    List<VideoView> all();

    boolean userExists(String userName, String password);

}
