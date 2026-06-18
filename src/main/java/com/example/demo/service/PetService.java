package com.example.demo.service;

import com.example.demo.dto.request.RequestPostPetDTO;
import com.example.demo.dto.response.ResponseGetPetDTO;
import com.example.demo.dto.response.ResponsePostPetDTO;

public interface PetService {
    ResponseGetPetDTO getPet(Long id);
    ResponsePostPetDTO postPet(RequestPostPetDTO request);
}
