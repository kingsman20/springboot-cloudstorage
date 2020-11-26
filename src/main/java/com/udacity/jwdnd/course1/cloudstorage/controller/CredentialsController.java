package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ModelAndView saveOrUpdateCredentials(Authentication authentication, Credentials credentials, ModelMap model) {
        try {
            String userName = authentication.getName();
            User user = userService.getUser(userName);

            if(credentials.getCredentialid() != null) {
                credentialsService.updateCredential(credentials);
                model.addAttribute("credentialsUpdatedSuccess", true);
            } else {
                credentials.setUserid(user.getUserid());
                model.addAttribute("credentialsAddedSuccess", true);
                credentialsService.addCredential(credentials);
            }

            model.addAttribute("credentials", credentialsService.getAllCredentials(user.getUserid()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ModelAndView("home", model);
    }

    @GetMapping("/delete/{credentialid}")
    public ModelAndView deleteCredential(@PathVariable("credentialid") Integer credentialid, Authentication authentication, ModelMap model) {
        try {
            String userName = authentication.getName();
            User user = userService.getUser(userName);
            this.credentialsService.deleteCredential(credentialid);
            model.addAttribute("credentialsDeleteSuccess", true);
            model.addAttribute("credentials", credentialsService.getAllCredentials(user.getUserid()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ModelAndView("home", model);
    }
}
