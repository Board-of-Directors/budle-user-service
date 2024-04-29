package ru.nsu.fit.directors.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.request.RequestCodeDto;
import ru.nsu.fit.directors.userservice.service.CodeService;

@RestController
@RequestMapping(value = "/user/code", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(allowCredentials = "true", originPatterns = {"*"})
public class CodeController {
    private final CodeService codeService;

    @GetMapping
    public Boolean getCode(@RequestParam String phoneNumber) {
        return codeService.generateCode(phoneNumber);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkCode(@RequestBody RequestCodeDto codeDto) {
        return codeService.checkCode(codeDto.phoneNumber(), codeDto.code());
    }
}
