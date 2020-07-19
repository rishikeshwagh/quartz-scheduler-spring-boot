package com.quartz.scheduler.service;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * Purge task status monitor job. {@link DisallowConcurrentExecution} prevents multiple instances of a job (with same job key) being executed concurrently.
 * This class submits a task for purge.
 */
@DisallowConcurrentExecution
@Slf4j
@NoArgsConstructor
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Job executing at - {}", LocalDateTime.now());
    }

}