package net.engineeringdigest.journalApp.schedular;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.journalEntry.JournalEntry;
import net.engineeringdigest.journalApp.journalEntry.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.services.EmailService;
import net.engineeringdigest.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    public EmailService emailService;
    @Autowired
    public UserRepositoryImpl userRepository;
    @Autowired
    public SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    public AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAMail(){
        List<User> user= userRepository.getUserForSA();
        for(User u:user){
            List<JournalEntry> journalEntries= u.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount=new HashMap<>();
            for(Sentiment s:sentiments){
                if(s!=null){
                    sentimentCount.put(s,sentimentCount.getOrDefault(s,0)+1);
                }
            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> e:sentimentCount.entrySet()){
                if(e.getValue()>maxCount){
                    maxCount=e.getValue();
                    mostFrequentSentiment=e.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                emailService.sendMail(u.getEmail(),"Sentiment for last 7 days",mostFrequentSentiment.toString());
            }
//            String entry = String.join(" ", filteredEntries);
//            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            emailService.sendMail(u.getEmail(),"Sentiment for last 7 days",sentiment);
        }
    }

    @Scheduled(cron="0 */5 * * * *")
    public void clearAppCache(){
        appCache.init();
    }
}
