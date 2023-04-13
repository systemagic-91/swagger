package com.db.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class Controller {


    @Operation(
            summary = "Retorna uma lista com todos os dados da API",
            description = "Descrição detalhada da operação"
    )
    @GetMapping
    public String getAll(){
        return "GET ALL";
    }



    @Operation(
            summary = "Retorna um dado da API",
            description = "Descrição detalhada da operação",
            tags = {"Usuários"}
    )
    @GetMapping("/{id}")
    public String get(@PathVariable("id") Long id){
        return "GET id:" + id;
    }


        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "sucesso"),
                @ApiResponse(responseCode = "209", description = "conflito")
        })
        @PostMapping
        public String post(@RequestBody String any){
            return "POST any: " + any;
        }

    @PutMapping
    public String put(@RequestBody String any){
        return "PUT any:" + any;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        return "DELETE id: " + id;
    }
}
