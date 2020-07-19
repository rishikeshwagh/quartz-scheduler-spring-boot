package com.quartz.scheduler.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple Job DTO
 */
@Getter
@Setter
public class SimpleJob extends Job {
    private int intervalInSeconds;
}
