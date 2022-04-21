package com.example.grapgqldemo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InputTask {
    private String name;

    private String description;

    private String deadline;

    private MultipartFile file;
}