package com.bsuir.spp.tasklist.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(name="name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Long statusId;

    @Column(name = "deadline")
    private LocalDateTime deadlineDate;

    public TaskStatus getStatus() {
        return TaskStatus.getTaskByLong(this.getStatusId());
    }

    public String getDeadlineDateString() {
        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.deadlineDate.format(aFormatter);
    }
}
