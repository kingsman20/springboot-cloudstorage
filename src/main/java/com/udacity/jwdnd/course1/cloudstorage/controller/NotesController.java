package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
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
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ModelAndView createOrUpdateNote(Authentication authentication, Notes notes, ModelMap model) {
        try {
            String userName = authentication.getName();
            User user = userService.getUser(userName);
            if (notes.getNoteid() != null) {
                notesService.updateNote(notes);
                model.addAttribute("noteUpdatedSuccess", true);
            } else {
                notes.setUserid(user.getUserid());
                notesService.addNote(notes);
                model.addAttribute("noteAddedSuccess", true);
            }

            model.addAttribute("notes", notesService.getAllNotes(user.getUserid()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("home", model);
    }

    @GetMapping("/delete/{noteid}")
    public ModelAndView deleteFile(@PathVariable("noteid") Integer noteid, Authentication authentication, ModelMap model) {
        try {
            String userName = authentication.getName();
            User user = userService.getUser(userName);
            this.notesService.deleteNote(noteid);
            model.addAttribute("noteDeleteSuccess", true);
            model.addAttribute("notes", notesService.getAllNotes(user.getUserid()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ModelAndView("home", model);
    }
}
