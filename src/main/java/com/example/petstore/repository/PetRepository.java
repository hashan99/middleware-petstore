package com.example.petstore.repository;

import com.example.petstore.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    public List<Pet> findPetsByPetName(String petName);

    public void deletePetsByPetType(String petType);

    public List<Pet> findPetsByPetType(String petType);

}
