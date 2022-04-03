package job4j.passports.control;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class KafkaConsumerController {

    @KafkaListener(topics = "expired")
    public void msgListener(String passport){
        System.out.println(passport);
    }
}