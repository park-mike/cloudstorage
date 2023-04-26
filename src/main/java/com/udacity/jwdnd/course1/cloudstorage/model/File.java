package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private int fileID;
    private int userID;
    private String fileName;
    private String fileSize;
    private String contentType;
    private byte[] fileData;

    public File(Integer fileID, Integer userID, String fileName, String fileSize, String contentType, byte[] fileData) {
        this.fileID = fileID;
        this.userID = userID;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.fileData = fileData;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }


}
