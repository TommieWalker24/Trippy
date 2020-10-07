package com.trippy.back.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.trippy.back.BackApplication;
import com.trippy.back.entities.Account;
import com.trippy.back.repos.UserRepo;
import com.trippy.back.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;


@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtGenerator jwtGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;




    public String login(Account account){
       Account onFileAccount = userRepo.findByEmail(account.getEmail());
       if(onFileAccount != null){
           if(passwordEncoder.matches(account.getPassword(), onFileAccount.getPassword())){
               String token = jwtGenerator.generate(onFileAccount);
               onFileAccount.setToken(token);
               userRepo.save(onFileAccount);
               return token;
           }
       }
       //todo: delete this
        return "delete";
    }
    public List<Account> getAllUsers(){
        return userRepo.findAll();
    }
    //todo: potentially return http status or confirmation
    public void deleteUser(Long id){
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
        }
    }
    public String findEmail(Long id){
        //checking if user's id exist in the database table of "user". If it does then return the user's corresponding email. if not return null.
        String email = null;
        try{
            if(userRepo.existsById(id)){
                Account account = userRepo.getOne(id);
                email = account.getEmail();
            }
            else{
                email = null;
            }
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return email;
    }

    public ResponseEntity createUser(Account account){
        if(userRepo.findByEmail(account.getEmail()) != null ){
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
        else{
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            userRepo.save(account);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    public void update(Account account){
        try {
            List<Account> list = getAllUsers();
            if (list.contains(account)) {
                Account dbAccount = list.get(list.indexOf(account));
                dbAccount.setDescription(dbAccount.getDescription().equals(account.getDescription()) ?  dbAccount.getDescription(): account.getDescription());
                dbAccount.setFirstName(dbAccount.getFirstName().equals(account.getFirstName()) ?  dbAccount.getFirstName(): account.getFirstName());
                dbAccount.setLastName(dbAccount.getLastName().equals(account.getLastName()) ?  dbAccount.getLastName(): account.getLastName());
                BackApplication.LOGGER.info("Updating user ["+dbAccount+"]");
                userRepo.save(dbAccount);
                return;
            }
        } catch(Exception e){
            BackApplication.LOGGER.log(Level.SEVERE, "Failed to update User ["+account+"]", e);
        }
        throw new RuntimeException("User ["+account+"] does not exist in DB.");
    }

}
