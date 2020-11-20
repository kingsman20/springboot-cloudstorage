package com.udacity.jwdnd.course1.cloudstorage.model;

public class Files {

    private int fileid;
    private String filename;
    private String contenttype;
    private Long filesize;
    private byte[] filedata;
    private Integer userid;

    public Files(int fileid, String filename, String contenttype, Long filesize, byte[] filedata, Integer userid) {
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.filedata = filedata;
        this.userid = userid;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
