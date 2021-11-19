package com.example.petstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.repository.PetRepository;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	@Inject
	PetRepository petRepository;

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setPetId(1);
		pet1.setPetAge(3);
		pet1.setPetName("Boola");
		pet1.setPetType("Dog");

		Pet pet2 = new Pet();
		pet2.setPetId(2);
		pet2.setPetAge(4);
		pet2.setPetName("Sudda");
		pet2.setPetType("Cat");

		Pet pet3 = new Pet();
		pet3.setPetId(3);
		pet3.setPetAge(2);
		pet3.setPetName("Peththappu");
		pet3.setPetType("Bird");

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setPetAge(3);
		pet.setPetName("Buula");
		pet.setPetType("Dog");

		return Response.ok(pet).build();
		
	}


	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search Pet by Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("{petName}")
	public Response getPetByName(@PathParam("petName") String name){
		List<Pet> petList = petRepository.findPetsByPetName(name);
		return Response.ok(petList).build();
	}


	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "change PetName", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("changePetName/{id}/{petName}")
	public Response changePetName(@PathParam("id") int id, @PathParam("petName") String petName){
		Optional<Pet> pet = petRepository.findById(id);
		if(pet.isEmpty()){
			System.out.println("Not Found");
			return Response.status(404).entity("Not found").build();
		} else{
			pet.get().setPetName(petName);
			Pet pet1 = petRepository.save(pet.get());
			return Response.ok(pet1).build();
		}

	}


	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Delete Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("deletePet/{petType}")
	public Response delPet(@PathParam("petType") String petType){

		petRepository.deletePetsByPetType(petType);
		return Response.ok().build();

	}



	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "find Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("findPet/{petType}")
	public Response findPet(@PathParam("petType") String petType){

		List<Pet> pets = petRepository.findPetsByPetType(petType);
		return Response.ok(pets).build();

	}


	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Add  pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@POST
	@Path("addPet")
	public Response addPet(@RequestBody Pet pet){
		Pet pet1 = petRepository.save(pet);
		return Response.ok(pet1).build();
	}



	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Change petType", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("showPet/{id}")
	public Response showPet(@PathParam("id") int id){
		Optional<Pet> pet = petRepository.findById(id);
		if(pet.isEmpty()){

			return Response.status(404).entity("Not found").build();
		} else{
			Pet pet1 = petRepository.findById(id).get();
			return Response.ok(pet1).build();
		}

	}

}
