package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/upload")
    public ModelAndView upload(@ModelAttribute("SpringWeb") MultipartFile fileUpload, Authentication authentication, ModelMap model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);

        // validate file upload request
        if (fileUpload.isEmpty()) {
            model.addAttribute("fileUploadEmpty", true);
            return new ModelAndView("model", model);
        }

        try {
            fileService.uploadFile(fileUpload, user.getUsername());
            model.addAttribute("fileUploadSuccess", true);
            model.addAttribute("files", fileService.getUserFiles(user.getUserid()));
        } catch (Exception err) {
            System.out.println("Error: " + err);
        }

        return new ModelAndView("home", model);
    }

    @GetMapping("/delete/{fileid}")
    public ModelAndView deleteFile(@PathVariable("fileid") Integer fileid, Authentication authentication, ModelMap model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Boolean isSuccess = this.fileService.deleteFile(fileid);
        model.addAttribute("fileDeleteSuccess", true);
        model.addAttribute("files", fileService.getUserFiles(user.getUserid()));

        return new ModelAndView("home", model);
    }

    @GetMapping(value = "/view/{fileid}")
    public void showFile(HttpServletResponse response, @PathVariable("fileid") Integer fileid) throws IOException {
        Files files = fileService.getFileByFileId(fileid);
        IOUtils.copy(new ByteArrayInputStream(files.getFiledata()), response.getOutputStream());
    }

    @GetMapping(value = "/download/{fileid}")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable("fileid") Integer fileid){
        Files files = fileService.getFileByFileId(fileid);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + files.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(files.getFiledata());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(Long.parseLong(files.getFilesize()))
                .contentType(MediaType.parseMediaType(files.getContenttype()))
                .body(resource);
    }

}
