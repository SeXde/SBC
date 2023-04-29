package com.urjc.alumno.alvaro.sbc.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class HtmlController {

    @Value("classpath:/static/index.html")
    private Resource htmlPage;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
     ResponseEntity<String> getHtml() throws IOException {
            return ResponseEntity.ok(htmlPage.getContentAsString(StandardCharsets.UTF_8));
    }

}
