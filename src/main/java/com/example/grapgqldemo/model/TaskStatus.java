package com.example.grapgqldemo.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum TaskStatus {
    AWAIT(1L),
    DONE(2L),
    EXPIRED(3L);

    @Getter
    private final Long statusNumber;

    TaskStatus(long statusId) {
        this.statusNumber = statusId;
    }

    public static TaskStatus getTaskByLong(long roleNumber) {
        Optional<TaskStatus> foundRole = Arrays.stream(TaskStatus.values())
                .filter((role) -> role.statusNumber.equals(roleNumber))
                .findFirst();
        return foundRole.orElse(AWAIT);
    }
}
