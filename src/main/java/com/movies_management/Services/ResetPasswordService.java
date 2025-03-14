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
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public ResetPasswordService(UserRepo userRepo, EmailSenderService emailSenderService, ResetPasswordRepo resetPasswordRepo){

        this.userRepo=userRepo;
        this.emailSenderService=emailSenderService;
        this.resetPasswordRepo = resetPasswordRepo;
    }

    public Boolean verifyEmail (EmailResponse emailResponse){
        return userRepo.findByemail(emailResponse.getEmail()) != null;
    }


    @Transactional
    public void sendmail(EmailResponse emailResponse){
        ResetPassword resetPassword = resetPasswordRepo.findByemail(emailResponse.getEmail());
        emailSenderService.SendEmail(emailResponse.getEmail(),
                "Don't reply to this Message",
                "you have Requested to reset Your password on our Movies Website.." +
                        " if you didn't ask for Resetting Password, ignore this Message," +
                        " your Verification Code is: "+resetPassword.getCode());


    }
    @Transactional
    public void saveEmailAndCodeinDB (EmailResponse emailResponse){
        ResetPassword resetPass = new ResetPassword();
        resetPass.setEmail(emailResponse.getEmail());
        resetPass.setCode(generateVerificationCode());
        resetPasswordRepo.save(resetPass);
    }
    public String generateVerificationCode(){

        return RandomStringUtils.randomAlphabetic(10);
    }
    public boolean verifyCode(String email, String userProvidedCode) {

        ResetPassword resetPassword = resetPasswordRepo.findByemail(email);

        return resetPassword.getCode().equals(userProvidedCode);


    }
    public boolean codeHandlingInDB (String email){
        return resetPasswordRepo.existsByemail(email);


    }
    public void deleteCode(String email){
        ResetPassword resetPassword = resetPasswordRepo.findByemail(email);
        Instant createdAt = resetPassword.getCreatedat();
        Instant expiryTime = createdAt.plusSeconds(2 * 60);
        if (expiryTime.isBefore(Instant.now())) {
            resetPasswordRepo.delete(resetPassword);
    }}


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

