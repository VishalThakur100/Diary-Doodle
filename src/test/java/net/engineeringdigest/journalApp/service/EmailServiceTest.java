package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Disabled
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

//    @MockBean
//    private EmailService mockEmailService; // {{ edit_1 }}

    @Disabled
    @Test
    public void test() {
        emailService.sendMail("akashinnov8@gmail.com", "Life advice", "Block your Mediocre friends bcoz if you do better than them they will try to bring you to their level");
//        verify(mockEmailService).sendMail(anyString(), anyString(), anyString()); // {{ edit_2 }}
    }
}