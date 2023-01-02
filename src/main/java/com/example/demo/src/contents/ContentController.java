package com.example.demo.src.contents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contents")
public class ContentController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ContentProvider contentProvider;
    private ContentService contentService;

    @Autowired
    public ContentController(ContentProvider contentProvider, ContentService contentService) {
        this.contentProvider = contentProvider;
        this.contentService = contentService;
    }


}
