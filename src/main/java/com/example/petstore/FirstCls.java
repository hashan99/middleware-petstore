package com.example.petstore;


import com.example.petstore.repository.PetRepository;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class FirstCls {


 @Inject
 PetRepository petRepository;

    void onStart(@Observes StartupEvent ev) {
        Pet pet1 = new Pet("Cat", "Sisi", 4);
        Pet pet2 = new Pet("Dog", "Boola", 6);
        Pet pet3 = new Pet("Parrot", "Peththappu", 1);
        Pet pet4 = new Pet("Rabbit", "Sudu", 2);
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);
        petRepository.save(pet4);


        System.out.println(pet1.getPetName());

    }

    void onStop(@Observes ShutdownEvent ev) {
        System.out.println("Application Shutdown...");
    }


}

