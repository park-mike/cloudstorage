package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    int byteRead;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public String[] getFileListings(Integer userID) {
        return fileMapper.getFileListings(userID);
    }

    public void addFile(MultipartFile multipartFile, String userName) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        while ((byteRead = inputStream.read(b, 0, b.length)) != -1) {
            byteArrayOutputStream.write(b, 0, byteRead);
        }
        byteArrayOutputStream.flush();
        byte[] fileData = byteArrayOutputStream.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userID = userMapper.getUser(userName).getUserID();
        File file = new File(0, userID, fileName, contentType, fileSize, fileData);
        fileMapper.insert(file);
    }

    public File getFIle(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}