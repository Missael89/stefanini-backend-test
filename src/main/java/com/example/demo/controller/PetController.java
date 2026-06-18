package com.example.demo.controller;

import com.example.demo.dto.request.RequestPostPetDTO;
import com.example.demo.dto.response.ResponseErrorDTO;
import com.example.demo.dto.response.ResponseGetPetDTO;
import com.example.demo.dto.response.ResponsePostPetDTO;
import com.example.demo.exception.PetErrorException;
import com.example.demo.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPet(@PathVariable("id") Long id) {
        try {
            ResponseGetPetDTO response = petService.getPet(id);
            return ResponseEntity.ok(response);
        } catch (PetErrorException e) {
            ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO();
            responseErrorDTO.setCode(1);
            responseErrorDTO.setType("error");
            responseErrorDTO.setMessage("Pet not found");
            return ResponseEntity.badRequest().body(responseErrorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<?> postPet(@RequestBody RequestPostPetDTO request) {
        try {
            ResponsePostPetDTO response = petService.postPet(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO();
            responseErrorDTO.setCode(1);
            responseErrorDTO.setType("error");
            responseErrorDTO.setMessage("Pet not created");
            return ResponseEntity.badRequest().body(responseErrorDTO);
        }
    }
}
