package com.fangxia.testvalidator.test;

import com.fangxia.testvalidator.common.exception.InvalidEmailException;
import com.fangxia.testvalidator.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fangxia.testvalidator.common.constant.ApiConstants.TEST_URL;

@RestController
@RequestMapping(TEST_URL)
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping("/email")
    public String sendEmail(@RequestParam String receiver) {
        String testCode = "123456";
        try{
            emailService.sendVerificationCode(receiver, testCode);
        } catch (InvalidEmailException iee) {
            return iee.getMessage();
        }
        return "Email sent successfully to " + receiver;
    }

}
