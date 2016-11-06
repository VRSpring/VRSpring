package com.vrspring.Bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lenovo on 2016-11-05.
 */
public class PanoFileBean {
    private String desc;
    private MultipartFile file;
    private String fileUser;

    public String getFileUser() {
        return fileUser;
    }

    public void setFileUser(String fileUser) {
        this.fileUser = fileUser;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
