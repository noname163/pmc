package com.utopia.pmc.data.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utopia.pmc.data.constants.others.ConsumerWay;
import com.utopia.pmc.data.constants.others.Gender;
import com.utopia.pmc.data.constants.others.Role;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.MedicineRepository;
import com.utopia.pmc.data.repositories.UserRepository;

@Configuration
public class fakedata {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, MedicineRepository medicineRepository) {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                List<User> users = new ArrayList<>();
                User admin = User.builder()
                        .username("Admin")
                        .age(18)
                        .email("admin@gmail.com")
                        .phone("012345678")
                        .password("admin12345")
                        .gender(Gender.MALE)
                        .role(Role.ADMIN)
                        .build();
                User baseUser = User.builder()
                        .username("baseuser")
                        .age(18)
                        .email("baseuser@gmail.com")
                        .phone("012345679")
                        .password("baseuser12345")
                        .gender(Gender.MALE)
                        .role(Role.USER_LEVEL_1)
                        .build();
                users.add(baseUser);
                users.add(admin);
                List<Medicine> medicines = new ArrayList<>();
                Medicine medicine1 = Medicine
                .builder()
                .consumerWay(ConsumerWay.AFTERMEAL)
                .describe("This medicine use for headache")
                .expiredTime(2)
                .name("paradon")
                .build();
                Medicine medicine2 = Medicine
                .builder()
                .consumerWay(ConsumerWay.BEFOREMEAL)
                .describe("This medicine use for stomach-ache")
                .expiredTime(2)
                .name("puscopan")
                .build();
                medicines.add(medicine2);
                medicines.add(medicine1);
                System.out.println("Insert users");
                userRepository.saveAll(users);
                System.out.println("Insert medicine");
                medicineRepository.saveAll(medicines);
            }

        };

    }
}
