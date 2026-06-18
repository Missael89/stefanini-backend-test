package com.example.demo.service.impl;

import com.example.demo.dto.request.RequestPostPetDTO;
import com.example.demo.dto.response.ResponseAPIPostPetDTO;
import com.example.demo.dto.response.ResponseErrorDTO;
import com.example.demo.dto.response.ResponseGetPetDTO;
import com.example.demo.dto.response.ResponsePostPetDTO;
import com.example.demo.exception.PetErrorException;
import com.example.demo.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PetServiceImpl implements PetService {
    private Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private RestClient.Builder restClientBuilder;

    @Value("${api.petstore.url}")
    private String restClientUrl;

    @Value("${api.petstore.get-pet}")
    private String restClientGetPet;

    @Value("${api.petstore.post-pet}")
    private String restClientPostPet;

    public PetServiceImpl(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @Override
    public ResponseGetPetDTO getPet(Long id) {
        try {
            RestClient restClient = restClientBuilder.baseUrl(restClientUrl).build();

            ResponseGetPetDTO response = restClient.get()
                    .uri(restClientGetPet, id)
                    .retrieve()
                    .body(ResponseGetPetDTO.class);
            logger.info("Pet with id {} retrieved successfully: {}", id, response);

            return response;
        } catch (HttpClientErrorException.NotFound e) {
            throw new PetErrorException("Pet not found");
        }
    }

    @Override
    public ResponsePostPetDTO postPet(RequestPostPetDTO request) {
        RestClient restClient = restClientBuilder.baseUrl(restClientUrl).build();

        ResponseAPIPostPetDTO responseAPIPostPetDTO = restClient.post()
                .uri(restClientPostPet)
                .body(request)
                .retrieve()
                .body(ResponseAPIPostPetDTO.class);

        ResponsePostPetDTO response = new ResponsePostPetDTO();
        response.setTransactionId(UUID.randomUUID());
        response.setDateCreated(LocalDateTime.now());
        response.setName(responseAPIPostPetDTO.getName());
        response.setStatus(true);

        logger.info("Pet add successfully: {}", response);

        return response;
    }
}
