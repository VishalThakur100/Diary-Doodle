package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest      //   this indicates that start the spring boot application  like start the application context
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(3,2+1);
        assertNotNull(userRepository.findByUserName("akash"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "thakur",
            "akash"
    })
    public void findUser(String name){
        assertNotNull(userRepository.findByUserName(name));
    }

}
