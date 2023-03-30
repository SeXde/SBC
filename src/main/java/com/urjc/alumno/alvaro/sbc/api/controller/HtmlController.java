package com.urjc.alumno.alvaro.sbc.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class HtmlController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
     ResponseEntity<String> getHtml() throws IOException {

        return ResponseEntity.ok(Files.readString(Path.of("src/main/resources/static/index.html")));

    }

}
