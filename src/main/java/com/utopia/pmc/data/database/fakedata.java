package com.utopia.pmc.data.database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utopia.pmc.data.constants.others.ConsumerWay;
import com.utopia.pmc.data.constants.others.Dose;
import com.utopia.pmc.data.constants.others.Gender;
import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.others.Role;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.MedicineRepository;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.data.repositories.UserRepository;

@Configuration
public class fakedata {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RegimenRepository regimenRepository,
            RegimenDetailRepository regimenDetailRepository, MedicineRepository medicineRepository) {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                List<User> users = new ArrayList<>();
                User admin = User.builder()
                        .username("Admin")
                        .age(18)
                        .email("admin@gmail.com")
                        .phone("012345678")
                        .password("$2a$12$XYAxwzQHfQ9GiSO1vTjUSOyDIfNv48y5SQ3fwnvGvFibieIDWpjs6")
                        .gender(Gender.MALE)
                        .role(Role.ADMIN)
                        .build();
                User baseUser = User.builder()
                        .username("baseuser")
                        .age(18)
                        .email("baseuser@gmail.com")
                        .phone("012345679")
                        .password("$2a$12$W.fEkdf/XwmU/ew78FR.deNoPi3g7tObftA3i3/pAIM./uGNyPwJa")
                        .gender(Gender.MALE)
                        .role(Role.USER)
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
                Regimen regimen = Regimen
                        .builder()
                        .createdDate(LocalDate.now())
                        .deviceToken("ExponentPushToken[BTMotuA4e1peQ7jrFyWP62]")
                        .status(RegimentStatus.INPROCESS)
                        .name("test")
                        .period(Period.DAY)
                        .doseRegiment(7)
                        .build();
                RegimenDetail regimenDetail = RegimenDetail
                        .builder()
                        .dose(Dose.PILL)
                        .firstTime(LocalTime.now().plusMinutes(1))
                        .medicine(medicine2)
                        .regiment(regimen)
                        .takenQuantity(2)
                        .build();
                

                System.out.println("Insert users");
                userRepository.saveAll(users);
                System.out.println("Insert medicine");
                medicineRepository.saveAll(medicines);
                System.out.println("Insert Regimen");
                regimenRepository.save(regimen);
                System.out.println("Insert regime detail");
                regimenDetailRepository.save(regimenDetail);
            }

        };

    }
}
