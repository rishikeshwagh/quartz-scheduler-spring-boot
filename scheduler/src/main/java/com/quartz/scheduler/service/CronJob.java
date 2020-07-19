package com.quartz.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Cron Job Class.
 */

@DisallowConcurrentExecution
@Slf4j
public class CronJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        //get calender instance and set Time values to 0
       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.HOUR_OF_DAY, 0);
       calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 0);
       calendar.set(Calendar.MILLISECOND, 0);
       //end time
       Date endTime = calendar.getTime();
       calendar.add(Calendar.DATE, -1);
       //start time
       Date startTime = calendar.getTime();

        log.info("Getting Account list", LocalDateTime.now());


    }

}


