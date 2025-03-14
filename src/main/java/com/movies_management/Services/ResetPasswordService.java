package com.movies_management.Services;

import com.movies_management.DTO.EmailResponse;
import com.movies_management.DTO.UserNewPasswordRequest;
import com.movies_management.Entities.ResetPassword;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.ResetPasswordRepo;
import com.movies_management.Repository.UserRepo;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Data
@Service
public class ResetPasswordService {

    private final UserRepo userRepo;
    private final EmailSenderService emailSenderService;
    private final ResetPasswordRepo resetPasswordRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResetPasswordService(UserRepo userRepo, EmailSenderService emailSenderService, ResetPasswordRepo resetPasswordRepo) {
        this.userRepo = userRepo;
        this.emailSenderService = emailSenderService;
        this.resetPasswordRepo = resetPasswordRepo;
    }

    public Boolean verifyEmail(EmailResponse emailResponse) {
        return userRepo.findByemail(emailResponse.getEmail()) != null;
    }

    @Transactional
    public void sendmail(EmailResponse emailResponse) {
        ResetPassword resetPassword = resetPasswordRepo.findByemail(emailResponse.getEmail());

        if (resetPassword == null) {
            throw new IllegalArgumentException("No reset password entry found for email: " + emailResponse.getEmail());
        }

        emailSenderService.SendEmail(
                emailResponse.getEmail(),
                "Don't reply to this Message",
                "You have requested to reset your password on our Movies Website. " +
                        "If you didn't ask for a password reset, ignore this message. " +
                        "Your Verification Code is: " + resetPassword.getCode()
        );
    }

    @Transactional
    public void saveEmailAndCodeinDB(EmailResponse emailResponse) {
        ResetPassword resetPass = new ResetPassword();
        resetPass.setEmail(emailResponse.getEmail());
        resetPass.setCode(generateVerificationCode());
        resetPasswordRepo.save(resetPass);
    }

    public String generateVerificationCode() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public boolean verifyCode(String email, String userProvidedCode) {
        ResetPassword resetPassword = resetPasswordRepo.findByemail(email);

        if (resetPassword == null) {
            return false;
        }

        return resetPassword.getCode().equals(userProvidedCode);
    }

    public boolean codeHandlingInDB(String email) {
        return resetPasswordRepo.existsByemail(email);
    }

    @Transactional
    public void deleteCode(String email) {
        ResetPassword resetPassword = resetPasswordRepo.findByemail(email);

        if (resetPassword == null) {
            return;
        }

        Instant createdAt = resetPassword.getCreatedat();
        Instant expiryTime = createdAt.plusSeconds(2 * 60); // Expiry in 2 minutes

        if (expiryTime.isBefore(Instant.now())) {
            resetPasswordRepo.delete(resetPassword);
        }
    }

    public Boolean updateUserPassword(String email, UserNewPasswordRequest userNewPasswordRequest) {
        Users user = userRepo.findByemail(email);

        if (user == null) {
            return false;
        }

        try {
            user.setPassword(encoder.encode(userNewPasswordRequest.getPassword()));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }
}
