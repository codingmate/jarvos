package codingmate.jarvos.quartz

import codingmate.jarvos.notification.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired


class ParticularMatterNotificationJob: Job {

    @Autowired
    private lateinit var telegramBotService: TelegramBotService

    override fun execute(context: JobExecutionContext?) {

    }
}