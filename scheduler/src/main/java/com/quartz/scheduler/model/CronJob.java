package com.quartz.scheduler.model;

import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

/**
 * Cron Job DTO
 */
@Getter
@Setter
public class CronJob extends Job{

    private String cronExpression;
    private TimeZone timeZone;

}
