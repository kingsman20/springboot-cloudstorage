package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Files> getUserFiles(Integer userId) {
        return filesMapper.findByUserId(userId);
    }

    public Boolean uploadFile(MultipartFile fileUpload, String username) throws IOException {
        User user = this.userMapper.getUser(username);
        Integer userId = user.getUserid();

        byte[] fileData = fileUpload.getBytes();
        String contentType = fileUpload.getContentType();
        String fileSize = String.valueOf(fileUpload.getSize());
        String fileName = fileUpload.getOriginalFilename();

        this.filesMapper.insertFile(new Files(0, fileName, contentType, fileSize, fileData, userId));
        return true;
    }

    public Boolean deleteFile(int fileid) {
        this.filesMapper.deleteFile(fileid);
        return true;
    }

    public Files getFileByFileId(Integer fileId) {
        return this.filesMapper.findOne(fileId);
    }

}
