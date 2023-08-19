package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SQSService {

    private final AmazonSQS clientSQS;

    public SQSService(AmazonSQS clientSQS){
        this.clientSQS = clientSQS;
    }

    public String createStandardQueue(String queueName){
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        return clientSQS.createQueue(createQueueRequest).getQueueUrl();
    }

    private String getQueueUrl(String queueName){
        return clientSQS.getQueueUrl(queueName).getQueueUrl();
    }

    public String publishStandardQueueMessage(String queueName, Integer delaySeconds, Map<String, MessageAttributeValue> messageAttributes, String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl(queueName))
                .withMessageBody(message)
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(messageAttributes);

        return clientSQS.sendMessage(sendMessageRequest).getMessageId();
    }

    public void publishStandardQueueMessage(String queueName, Integer delaySeconds, List<Object> objects, String message){
        for (Object item: objects) {
            publishStandardQueueMessage(queueName, delaySeconds, objects, message);
        }
    }
    public Flux<Movie> receiveMessages(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds){
        List<Message> messages = receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds);
        List<Movie> movies = new ArrayList<>();
        for(Message message: messages){
            if(!message.getMessageAttributes().isEmpty()) {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(message.getMessageAttributes().get("id").getStringValue()));
                movie.setTitle(message.getMessageAttributes().get("title").getStringValue());
                movie.setGenre(message.getMessageAttributes().get("genre").getStringValue());
                movie.setCreator(message.getMessageAttributes().get("creator").getStringValue());
                movie.setDuration(Integer.parseInt(message.getMessageAttributes().get("duration").getStringValue()));
                movie.setReleaseYear(message.getMessageAttributes().get("releaseYear").getStringValue());
                movie.setViewed(Boolean.parseBoolean(message.getMessageAttributes().get("viewed").getStringValue()));
                movie.setTimeViewed(Integer.parseInt(message.getMessageAttributes().get("timeViewed").getStringValue()));
                movies.add(movie);
            }
        }
        return Flux.fromIterable(movies);
    }

    private List<Message> receiveMessagesFromQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds){
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.getQueueUrl(queueName))
                .withMaxNumberOfMessages(maxNumberMessages)
                .withMessageAttributeNames(List.of("All"))
                .withWaitTimeSeconds(waitTimeSeconds);

        return clientSQS.receiveMessage(receiveMessageRequest).getMessages();
    }

    public Mono<Object> deleteObjectMessageInQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds, String fieldName, String messageDescription){
        List<Message> messages = receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds);
        for(Message message: messages){
            if(!message.getMessageAttributes().isEmpty()) {
                if (message.getMessageAttributes().get(fieldName).getStringValue().equals(messageDescription)){
                    message.getMessageAttributes();
                }
            }
        }

        return Mono.empty();
    }
}
