package com.quartz.scheduler.service;


import com.quartz.scheduler.model.CronJob;
import com.quartz.scheduler.model.Job;
import com.quartz.scheduler.model.SimpleJob;
import com.quartz.scheduler.service.impl.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * Implementation of {@link SchedulerService}
 */
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    /**
     * scheduler from SchedulerFactoryBean
     */
    private final Scheduler scheduler;

    @Override
    public void scheduleSimpleJob(SimpleJob simpleJob) throws SchedulerException {
        final var simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(simpleJob.getIntervalInSeconds()).repeatForever();
        scheduleJob(simpleJob, simpleScheduleBuilder); scheduler.getCurrentlyExecutingJobs();
    }

    @Override
    public void scheduleCronJob(CronJob cronJob) throws SchedulerException {
        final var cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronJob.getCronExpression()).inTimeZone(cronJob.getTimeZone());
        scheduleJob(cronJob, cronScheduleBuilder);
    }

    /**
     * Create Trigger.
     *
     * @param job             has JobName,jobGroup,JobClass,startAt,endAt
     * @param scheduleBuilder has CronSchedule Or SimpleSchedule
     */
    private Trigger createTrigger(Job job, ScheduleBuilder scheduleBuilder) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(job.getName(), job.getGroup())
                .startAt(job.getStartAt() == 0 ? Date.from(Instant.now()) : new Date(job.getStartAt()))
                .endAt(new Date(job.getEndAt()))
                .build();
    }

    /**
     * Schedule Job.
     * It creates or update a new Job
     *
     * @param job             has jobName,jobGroup.
     * @param scheduleBuilder job
     */
    private void scheduleJob(Job job, ScheduleBuilder scheduleBuilder) throws SchedulerException {
        final var jobDetail = JobBuilder.newJob()
                .ofType(job.getJobClass())
                .withIdentity(job.getName(), job.getGroup())
                .usingJobData(job.getJobDataMap() == null ? new JobDataMap() : job.getJobDataMap())
                .requestRecovery()
                .storeDurably()
                .withDescription(job.getDescription())
                .build();
        final var trigger = createTrigger(job, scheduleBuilder);

        if (scheduler.getJobDetail(JobKey.jobKey(job.getName(), job.getGroup())) == null) {
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.rescheduleJob(scheduler.getTrigger(trigger.getKey()).getKey(), trigger);
            scheduler.addJob(jobDetail, true);
        }
    }
}
