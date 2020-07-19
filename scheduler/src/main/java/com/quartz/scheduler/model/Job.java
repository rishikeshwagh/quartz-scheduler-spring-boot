package com.quartz.scheduler.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.quartz.JobDataMap;

import java.util.Date;

/**
 * Job DTO
 */
@Getter
@Setter
public class Job {
    private Class<? extends org.quartz.Job> jobClass;
    private String name;
    private String group;
    private long startAt;
    private long endAt;
    private String description;
    /**
     * jobDataMap : Holds state information for Job instances
     */
    private JobDataMap jobDataMap;

}
