package net.sidwallmart.generalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import net.sidwallmart.generalapp.model.SentimentData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
        @Autowired
        private EmailService emailService;

    @KafkaListener(
            topics = "weekly-sentiments",
            groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
        System.out.println("Received from Kafka: " + sentimentData);
    }

    public void sendEmail(SentimentData sentimentData){
        emailService.sendEmail(
                sentimentData.getEmail(),
                "Sentiment for previous week",
                sentimentData.getSentiment());
    }
}
