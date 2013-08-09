package com.mlj.retrovideo.web.video;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem {

    private CommonsMultipartFile file;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

}
