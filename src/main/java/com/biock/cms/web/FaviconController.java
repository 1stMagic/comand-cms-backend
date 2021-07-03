package com.biock.cms.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @GetMapping("/favicon.ico")
    public void favicon() {

        // Return nothing - this is just to prevent warning messages in the log
    }
}
