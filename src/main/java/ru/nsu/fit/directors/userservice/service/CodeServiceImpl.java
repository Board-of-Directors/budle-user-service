package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.enums.CodeType;
import ru.nsu.fit.directors.userservice.exception.IncorrectPhoneNumberFormatException;
import ru.nsu.fit.directors.userservice.exception.UserAlreadyExistsException;
import ru.nsu.fit.directors.userservice.exception.VerificationCodeWasFalseException;
import ru.nsu.fit.directors.userservice.model.Code;
import ru.nsu.fit.directors.userservice.repository.CodeRepository;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final RequestSenderService requestSenderService;

    @Override
    public boolean checkCode(String phoneNumber, String codeString) {
        log.info("Checking code");
        Optional<Code> code = codeRepository.findByPhoneNumberAndCode(phoneNumber, codeString);
        if (code.isPresent()) {
            codeRepository.delete(code.get());
            log.info("Checking code was true");
            return true;
        } else {
            throw new VerificationCodeWasFalseException();
        }
    }

    @Override
    public boolean generateCode(String phoneNumber) {
        log.info("Generating code");
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            log.warn("User with number " + phoneNumber + " already exists");
            throw new UserAlreadyExistsException();
        } else {
            Map<String, Object> response = requestSenderService.sendRequest(phoneNumber);
            if (codeRequestWasFalse(response)) {
                log.warn("Checking code for " + phoneNumber + " was false");
                throw new IncorrectPhoneNumberFormatException();
            } else {
                log.info("Creating new instance of code");
                Code code = new Code();
                code.setCode((String) response.get("code"));
                code.setPhoneNumber(phoneNumber);
                code.setType(CodeType.registration);
                codeRepository.save(code);
                log.info("Code " + code + "was created successfully ");
                return true;
            }
        }
    }

    private boolean codeRequestWasFalse(Map<String, Object> response) {
        return response.get("status").equals(false);
    }

}
