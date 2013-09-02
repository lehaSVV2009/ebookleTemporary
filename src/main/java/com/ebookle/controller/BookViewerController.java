package com.ebookle.controller;

import com.ebookle.entity.Book;
import com.ebookle.entity.Chapter;
import com.ebookle.entity.Prefer;
import com.ebookle.entity.User;
import com.ebookle.service.BookService;
import com.ebookle.service.PreferService;
import com.ebookle.service.impl.UserServiceImpl;
import com.ebookle.util.Encoder;
import com.petebevin.markdown.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 28.08.13
 * Time: 7:00
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BookViewerController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MarkdownProcessor markdownProcessor;

    @Autowired
    private PreferService preferService;

    @RequestMapping(value = "/{userLogin}/editBook/{bookTitle}/{chapterNumber}/show", method = RequestMethod.GET)
    public String showChapter (Principal principal,
                               @PathVariable("chapterNumber") Integer chapterNumber,
                               @PathVariable("userLogin") String userLogin,
                               @PathVariable("bookTitle") String bookTitle,
                               ModelMap modelMap) {
        bookTitle = Encoder.decode(bookTitle);
        User user = userService.findByLogin(userLogin);
        Book book = bookService.findByTitleAndUserIdWithChapters(bookTitle, user);
        Chapter currentChapter = book.getChapters().get(chapterNumber - 1);
        String htmlChapterText = markdownProcessor.markdown(currentChapter.getText());
        modelMap.addAttribute("book", book);
        modelMap.addAttribute("userLogin", userLogin);
        modelMap.addAttribute("currentChapter", currentChapter);
        modelMap.addAttribute("userAction", "show");
        modelMap.addAttribute("tags", bookService.findByTitleAndUserIdWithTags(bookTitle, user).getTags());
        modelMap.addAttribute("htmlChapterText", htmlChapterText);
        if (principal == null) {
            modelMap.addAttribute("person", "guest");
        } else if (!userLogin.equals(principal.getName())) {
            modelMap.addAttribute("person", "notOwnUser");
            User markAuthor = userService.findByLogin(principal.getName());
            Prefer prefer = preferService.findByBookAndMarkAuthor(book, markAuthor);
            if (prefer == null) {
                modelMap.addAttribute("mark", "showAll");
            } else if (prefer.getMark() == 1) {
                modelMap.addAttribute("mark", "showJustDislike");
            } else {
                modelMap.addAttribute("mark", "showJustLike");
            }
        } else {
            modelMap.addAttribute("person", "ownUser");
        }
        return "edit_book";
    }



}
