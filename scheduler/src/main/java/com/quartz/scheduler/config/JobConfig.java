package com.quartz.scheduler.config;

import com.quartz.scheduler.model.CronJob;
import com.quartz.scheduler.model.SimpleJob;
import com.quartz.scheduler.service.SchedulerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.ManagedBean;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final Scheduler scheduler;

    @Bean
    public void testScheduler() throws Exception {

        SchedulerServiceImpl schedulerService = new SchedulerServiceImpl(scheduler);

       JobDataMap simpleJobDataMap = new JobDataMap();
       simpleJobDataMap.put("jobClass", SimpleJob.class);

       SimpleJob simpleJob = new SimpleJob();
       simpleJob.setJobClass(com.quartz.scheduler.service.SimpleJob.class);
       simpleJob.setName("simple-job");
       simpleJob.setGroup("test-jobs");
       simpleJob.setIntervalInSeconds(10);
       simpleJob.setDescription("Description For Simple Job");
       simpleJob.setStartAt(1595107854181L);
       simpleJob.setEndAt(1595189854181L);
       simpleJob.setJobDataMap(simpleJobDataMap);
       schedulerService.scheduleSimpleJob(simpleJob);

        JobDataMap cronJobDataMap = new JobDataMap();
        cronJobDataMap.put("jobClass", CronJob.class);

        CronJob cronJob = new CronJob();
        cronJob.setJobClass(com.quartz.scheduler.service.CronJob.class);
        cronJob.setName("cron-job");
        cronJob.setGroup("test-cron-jobs");
        cronJob.setCronExpression("0 */3 * ? * *");
        cronJob.setDescription("Description For Cron Job");
        cronJob.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));
        cronJob.setStartAt(1595108220000L);
        cronJob.setEndAt(1621373820000L);
        cronJob.setJobDataMap(cronJobDataMap);
        schedulerService.scheduleCronJob(cronJob);

    }
}
