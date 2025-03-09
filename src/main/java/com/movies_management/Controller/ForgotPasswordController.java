package com.movies_management.Controller;



import com.movies_management.DTO.EmailResponse;
import com.movies_management.DTO.UserNewPasswordRequest;
import com.movies_management.DTO.UserProvidedcodeResponse;
import com.movies_management.Services.ResetPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {
    private final ResetPasswordService resetPasswordService;

    public ForgotPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/ResetPassword")
    public ResponseEntity ResetPassword(@RequestBody EmailResponse emailResponse) {
        if (resetPasswordService.verifyEmail(emailResponse)) {
            resetPasswordService.saveEmailAndCodeinDB(emailResponse);
            resetPasswordService.sendmail(emailResponse);
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/verifyCode")
    public ResponseEntity verifyCode(@RequestParam String email, @RequestBody UserProvidedcodeResponse userProvidedcodeResponse) {
        if(resetPasswordService.verifyCode(email, userProvidedcodeResponse.getCode())){

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/UpdatePassword")
    public ResponseEntity UpdatePassword (@RequestParam String email, @RequestBody UserNewPasswordRequest userNewPasswordRequest){

        if ( resetPasswordService.updateUserPassword(email,userNewPasswordRequest)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
