package com.bsuir.spp.tasklist.service;

import lombok.Data;

@Data
public class InputTask {
    private String name;

    private String description;

    private String deadline;
}
