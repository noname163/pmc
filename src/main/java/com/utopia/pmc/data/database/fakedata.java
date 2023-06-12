package com.utopia.pmc.data.database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utopia.pmc.data.constants.others.ConsumerWay;
import com.utopia.pmc.data.constants.others.Gender;
import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.others.Role;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.PaymentPlan;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.entities.medicine.DosageForm;
import com.utopia.pmc.data.entities.medicine.MedicationUse;
import com.utopia.pmc.data.entities.medicine.Medicine;
import com.utopia.pmc.data.entities.medicine.MedicineClassification;
import com.utopia.pmc.data.repositories.PaymentPlanRepository;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.data.repositories.UserRepository;
import com.utopia.pmc.data.repositories.medicine.DosageFormRepository;
import com.utopia.pmc.data.repositories.medicine.MedicationUseRepository;
import com.utopia.pmc.data.repositories.medicine.MedicineClassificationRepository;
import com.utopia.pmc.data.repositories.medicine.MedicineRepository;

@Configuration
public class fakedata {
        @Bean
        CommandLineRunner initDatabase(UserRepository userRepository, RegimenRepository regimenRepository,
                        RegimenDetailRepository regimenDetailRepository, MedicineRepository medicineRepository,
                        DosageFormRepository dosageFormRepository, MedicationUseRepository medicationUseRepository,
                        MedicineClassificationRepository medicineClassificationRepository,
                        PaymentPlanRepository paymentPlanRepository) {
                return new CommandLineRunner() {

                        @Override
                        public void run(String... args) throws Exception {
                                List<User> users = new ArrayList<>();
                                PaymentPlan paymentPlan = PaymentPlan.builder().name("level1")
                                                .expriredTime(5)
                                                .numberOfMedicine(100)
                                                .numberOfRegiment(100).build();
                                User admin = User.builder()
                                                .username("Admin")
                                                .age(18)
                                                .email("admin@gmail.com")
                                                .phone("012345678")
                                                .password("$2a$12$XYAxwzQHfQ9GiSO1vTjUSOyDIfNv48y5SQ3fwnvGvFibieIDWpjs6")
                                                .gender(Gender.MALE)
                                                .paymentPlan(paymentPlan)
                                                .role(Role.ADMIN)
                                                .build();
                                User baseUser = User.builder()
                                                .username("baseuser")
                                                .age(18)
                                                .email("baseuser@gmail.com")
                                                .paymentPlan(paymentPlan)
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
                                                .firstTime(LocalTime.now().plusMinutes(1))
                                                .medicine(medicine2)
                                                .regimen(regimen)
                                                .numberOfMedicine(10)
                                                .takenQuantity(2)
                                                .build();
                                DosageForm dosageForm = DosageForm
                                                .builder()
                                                .form("PILL")
                                                .build();
                                MedicationUse medicationUse = MedicationUse
                                                .builder()
                                                .use("Heart-Attack")
                                                .description("Use to reduce heart attack")
                                                .build();
                                MedicineClassification medicineClassification = MedicineClassification
                                                .builder()
                                                .classification("over-the-counter")
                                                .description("Can buy by anyone")
                                                .build();

                                System.out.println("Insert payment plan");
                                paymentPlanRepository.save(paymentPlan);
                                System.out.println("Insert users");
                                userRepository.saveAll(users);
                                System.out.println("Insert medicine");
                                medicineRepository.saveAll(medicines);
                                System.out.println("Insert Regimen");
                                regimenRepository.save(regimen);
                                System.out.println("Insert regime detail");
                                regimenDetailRepository.save(regimenDetail);
                                System.out.println("Insert dosage form");
                                dosageFormRepository.save(dosageForm);
                                System.out.println("Insert medication use");
                                medicationUseRepository.save(medicationUse);
                                System.out.println("Insert medication classification");
                                medicineClassificationRepository.save(medicineClassification);
                        }

                };

        }
}
