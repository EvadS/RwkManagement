package com.se.management;

import com.se.management.repository.SearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RwManagementApplication  implements CommandLineRunner {
    @Autowired
    private SearcherRepository searcherRepository;

    public static void main(String[] args) {
        SpringApplication.run(RwManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Cleanup database tables.
//        userRepository.deleteAll();
//
//        // Insert a user with multiple phone numbers and addresses.
//        Set<SkillItem> phoneNumbers = new HashSet<>();
//        phoneNumbers.add(new SkillItem("C++", "12"));
//        phoneNumbers.add(new SkillItem("Java", "12"));
//
//        Set<Address> addresses = new HashSet<>();
//        addresses.add(new Address("747", "Golf View Road", "Bangalore",
//                "Karnataka", "India", "560008"));
//        addresses.add(new Address("Plot No 44", "Electronic City", "Bangalore",
//                "Karnataka", "India", "560001"));
//
//        User user = new User("Rajeev Kumar Singh", "rajeev@callicoder.com",
//                phoneNumbers, addresses);
//
//        userRepository.save(user);
    }
}
