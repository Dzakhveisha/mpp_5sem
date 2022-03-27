package com.bsuir.spp.tasklist.dao.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Long statusId;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "user_id")
    private Long userId;

    public Task(String name, String description, String deadline, String fileName) {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ISO_DATE_TIME);
    }

    public Task(String name, String description, String deadline) {
        this.name = name;
        this.description = description;
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ISO_DATE_TIME);
    }

    public TaskStatus getStatus() {
        return TaskStatus.getTaskByLong(this.getStatusId());
    }

    public String getDeadlineDateString() {
        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.deadline.format(aFormatter);
    }
}
