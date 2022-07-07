package com.sks;


import com.sks.model.Customer;
import com.sks.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDeployInK8sApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ToDeployInK8sApplication.class);

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(ToDeployInK8sApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        customerRepository.save(new Customer(0, "monsieur Dupont", "dupont@gmail.com"));
        customerRepository.save(new Customer(0, "monsieur durand", "durand@gmail.com"));
        customerRepository.save(new Customer(0, "madame Reine", "reine@gmail.com"));
        customerRepository.findAll().forEach(System.out::println);
    }


}
