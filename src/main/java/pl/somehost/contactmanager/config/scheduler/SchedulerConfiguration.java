package pl.somehost.contactmanager.config.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfiguration {

    @Value("${scheduler.trys}")
    private Integer messageTrys;
    @Value("${scheduling.period}")
    private Long schedulingPeriod;

    public Integer getMessageTrays() {
        return messageTrys;
    }

    public long getSchedulingPeriod() {
        return schedulingPeriod;
    }
}
