package org.acme;

import com.example.petstore.Pet;
import com.example.petstore.repository.PetRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class PetResourceTest {

    @Inject
    PetRepository petRepository;

    @Test
    public void testPetEndpoint() {
        given()
                .when().get("/v1/pets")
                .then()
                .statusCode(200);
//             .body(hasItem(
// 		            allOf(
//    		                hasEntry("pet_id", "1"),
//    		                hasEntry("pet_type", "Dog"),
//    		                hasEntry("pet_name", "Boola"),
//    		                hasEntry("pet_age", "3")
//    		            )
//    		      )
//    		 );
    }


    @Test
    public void testChangePetType() {

        given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/changePetType/1/Bird")
                .then()
                .statusCode(200)
                .body("petType", is("Bird"));
    }

    @Test
    public void testDeletePetByPetType() {
        Pet pet = new Pet();
        pet.setPetName("Nemo");
        pet.setPetType("Fish");
        pet.setPetAge(2);
        petRepository.save(pet);

        given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/deletePet/Fish")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFindPetsByPetType() {

        given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/findPet/Cat")
                .then()
                .statusCode(200)
                .body("size", notNullValue());
    }


    @Test
    public void testGetPetByName() {

        given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/Tommy")
                .then()
                .statusCode(200).body("size", notNullValue());
    }

    @Test
    public void testShowPet() {
        Pet pet = new Pet();
        pet.setPetId(2);
        pet.setPetName("nemo");
        pet.setPetType("Fish");
        pet.setPetAge(6);
        petRepository.save(pet);

        given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/showPet/2")
                .then()
                .statusCode(200)
                .body("petName", is("nemo"));
    }



}