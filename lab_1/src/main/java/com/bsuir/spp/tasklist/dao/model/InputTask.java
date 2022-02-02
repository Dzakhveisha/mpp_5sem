package com.bsuir.spp.tasklist.dao.model;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class InputTask {
    private String name;

    private String description;

    private String deadline;
}
