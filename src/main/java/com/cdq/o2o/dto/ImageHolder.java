package com.cdq.o2o.dto;

import java.io.InputStream;

public class ImageHolder {

    private String imgName;
    private InputStream inputStream;

    public ImageHolder(String imgName,InputStream inputStream){
        this.imgName=imgName;
        this.inputStream=inputStream;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
