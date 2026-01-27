package com.lrtech.desafio_padroes_de_projeto.Services;

import com.lrtech.desafio_padroes_de_projeto.Entities.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep",url = "https://viacep.com.br/ws")
public interface CepService {
    //@RequestMapping(method = RequestMethod.GET, value = "/{cep}/json/")
    @GetMapping(  "/{cep}/json/") //funciona tbm com as anotações do spring
    public Endereco consultarEndereco(@PathVariable("cep") String cep);

}
