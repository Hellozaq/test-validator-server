package com.fangxia.testvalidator.common.service;

import com.fangxia.testvalidator.common.exception.InvalidEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationCode(String receiver, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);
            try {
                helper.setFrom("fangxiatechnology@163.com", "FXEdu Bot");
            } catch (UnsupportedEncodingException uee) {
                log.warn("Can not send message with sender name, proceeding with regular format");
                helper.setFrom("fangxiatechnology@163.com");
            }

            javaMailSender.send(mimeMessage);

            log.info("Verification code has been sent to: {}", receiver);

        } catch (MessagingException me) {
            log.error("Failed to send verification code to {}: {}.", receiver, me.getMessage());
            throw new InvalidEmailException("Failed to send verification code to " + receiver, me);
        }
    }

    public void sendVerificationCode(String receiver, String code) throws InvalidEmailException {

        String subject = "FangXia Technology - Verification Code";
        String content = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif;">
                <h2 style="color: #4CAF50;">Email Verification</h2>
                <p>Hello,</p>
                <p>Your verification code is:</p>
                <div style="font-size: 24px; font-weight: bold; background: #f4f4f4; padding: 10px; width: fit-content;">
                    %s
                </div>
                <p>This code is valid for <strong>15 minutes</strong>.</p>
                <p>If you did not request this, please ignore this email.</p>
                <br>
                <p style="font-size: 12px; color: #999;">- FXEdu Team</p>
            </body>
            </html>
            """, code);

        sendVerificationCode(receiver, subject, content);

    }

}
