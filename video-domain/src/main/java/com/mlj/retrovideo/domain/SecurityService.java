package com.mlj.retrovideo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final JdbcVideoRepository videoRepository;

    @Autowired
    public SecurityService(JdbcVideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public boolean authenticate(String userName, String password) {
        return videoRepository.userExists(userName, password);
    }

}
