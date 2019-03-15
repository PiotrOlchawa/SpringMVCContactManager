package pl.somehost.contactmanager.config.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfiguration {

    @Value("${scheduler.trys}")
    private Integer messageTrys;
    @Value("${scheduling.period}")
    private Long schedulingPeriod;

    public Integer getMessageTrys() {
        return messageTrys;
    }

    public long getSchedulingPeriod() {
        return schedulingPeriod;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }
}
