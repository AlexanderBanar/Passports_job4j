package job4j.passports.control;

import job4j.passports.domain.Passport;
import job4j.passports.repository.PassportAPI;
import job4j.passports.service.PassportService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class KafkaProducerController {
    private KafkaTemplate<Integer, String> template;
    private PassportService passportService;

    public KafkaProducerController(KafkaTemplate<Integer, String> template, PassportService passportService) {
        this.template = template;
        this.passportService = passportService;
    }

    @Async
    @Scheduled(fixedDelay = 10_000, initialDelay = 3_000)
    public void scheduleAsyncMailing() {
        List<Passport> expiredPassports = passportService.findAllByExpirationBefore(new Date());
        if (!expiredPassports.isEmpty()) {
            ListenableFuture<SendResult<Integer, String>> future;
            for (Passport passport : expiredPassports) {
                future = template.send("expired", passport.toString());
                future.addCallback(System.out::println, System.err::println);
                template.flush();
            }
        }
    }
}