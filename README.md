Technologies:

- Spring Boot
- Spring Data
- Spring Kafka
- PostgreSQL

Annotations:

- @Bean
- @Column
- @Component
- @Configuration
- @DeleteMapping
- @EnableAsync
- @EnableKafka
- @EnableScheduling
- @Entity
- @GeneratedValue
- @GetMapping
- @Id
- @JsonFormat
- @KafkaListener
- @PathVariable
- @PostMapping
- @PutMapping
- @Repository
- @RequestBody
- @RequestMapping
- @RestController
- @Service
- @SpringBootApplication
- @Table
- @Temporal
- @UniqueConstraint
- @Value

Description:

The 1st Service manages passports (Passport domain model) using Spring Boot & REST architecture

It provides the following CRUD mappings:
- /save
- /update?id=*
- /delete?id=*
- /find
- /find?serial=*
- /unavailable
- /find-replaceable

The 2nd Service (Spring Boot) calls the methods of the 1st Service by RestTemplate. 
Both realize the synchronous (blocking) model of communication.

Kafka is also set up in the project (in a basic way) to demonstrate asynchronous messaging.

Two config beans for Producer and Consumer. Producer's output is a configured KafkaTemplate bean that receives as 
a Constructor parameter the bean of ProducerFactory, for this case its realization DefaultKafkaProducerFactory, 
that needs a Map containing values of kafkaServer url, and the key/value serializers. Similarly, 
the Consumer's output is KafkaListenerContainerFactory bean, i.e. its realization 
ConcurrentKafkaListenerContainerFactory. Its Constructor needs ConsumerFactory, we take its realization 
DefaultKafkaConsumerFactory, that finally receives a Map with values of kafkaServer, serializers and kafkaGroupId.

KafkaProducerController at a scheduled manner (each 10 seconds for purposes of the project) asks 
the PassportService the list of expired passports (if any), and sends via KafkaTemplate each passport 
(in String format) to the "expired" topic. It receives in return an object of ListenableFuture parametrized 
by SendResult<Integer, String> object. We invoke callBack on that future object with printing (simply to console) 
the result (either on success or on fail). While Consumer controller is just being "subscribed" 
to the "expired" topic listening to it and receiving Strings of expired passports.
