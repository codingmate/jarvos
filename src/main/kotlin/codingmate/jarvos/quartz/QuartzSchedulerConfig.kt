package codingmate.jarvos.quartz

import jakarta.annotation.PostConstruct
import org.quartz.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class QuartzSchedulerConfig {

    @Autowired
    private lateinit var schedulerFactoryBean: SchedulerFactoryBean

    @PostConstruct
    fun startScheduler() {

        val scheduler: Scheduler = schedulerFactoryBean.`object`!!

        scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particular", "0 0 7 * * ?")

        scheduler.start()
    }
    private fun <T : Job> scheduleJob(scheduler: Scheduler, jobClass: Class<T>, name: String, cronExpression: String) {
        val jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity("${name}NotificationJob")
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity("${name}NotificationTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }
}