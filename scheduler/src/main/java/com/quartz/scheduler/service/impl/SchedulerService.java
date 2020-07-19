package com.quartz.scheduler.service.impl;

import com.quartz.scheduler.model.CronJob;
import com.quartz.scheduler.model.SimpleJob;
import org.quartz.SchedulerException;

/**
 * Scheduler service.
 * Methods to schedule or update cronJob and simpleJob.
 */
public interface SchedulerService {

    /**
     * Method for creating or updating simple Job.
     * It creates simpleTrigger and schedule new job or update the existing job.
     *
     * @param simpleJob has intervalInSeconds,jobDetails
     * @throws SchedulerException if there is an internal Scheduler error, or if the Job is not
     *                            durable, or a Job with the same name already exists
     */
    void scheduleSimpleJob(SimpleJob simpleJob) throws SchedulerException;

    /**
     * Method for creating or updating cron Job.
     * It creates cronTrigger and schedule new job or update the existing job.
     *
     * @param cronJob has cronExpression,timeZone,jobDetails
     * @throws SchedulerException if there is an internal Scheduler error, or if the Job is not
     *                            durable, or a Job with the same name already exists
     */
    void scheduleCronJob(CronJob cronJob) throws SchedulerException;

}

