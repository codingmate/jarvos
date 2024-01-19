package codingmate.jarvos.quartz

import codingmate.jarvos.notification.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class ParticularMatterNotificationJob(
    private val telegramBotService: TelegramBotService
): Job{
    override fun execute(context: JobExecutionContext) {
        telegramBotService.sendMessage()
        println(">>>>>>>>>>>>>>>>>>>${System.currentTimeMillis()}")
    }
}