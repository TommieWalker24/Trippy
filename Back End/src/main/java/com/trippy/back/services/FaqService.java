package com.trippy.back.services;

import com.trippy.back.BackApplication;
import com.trippy.back.entities.Faq;
import com.trippy.back.repos.FaqRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;


@Service
public class FaqService {
    @Autowired
    FaqRepo FaqRepo;

    public List<Faq> getAllFaqs(){
        return FaqRepo.findAll();
    }
    //todo: potentially return http status or confirmation

    public ResponseEntity addFaq(Faq faq){
            FaqRepo.save(faq);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    public void deleteFaq(Long ID){
        if(FaqRepo.existsById(ID)){
            FaqRepo.deleteById(ID);
        }
    }

    public void updateFaq(Faq faq){
        try {
            List<Faq> list = getAllFaqs();
            if (list.contains(faq)) {
                Faq faqItems = list.get(list.indexOf(faq));
                faqItems.setMessage(faqItems.getMessage().equals(faq.getMessage()) ?  faqItems.getMessage(): faq.getMessage());
                faqItems.setAnswer(faqItems.getAnswer().equals(faq.getAnswer()) ?  faqItems.getAnswer(): faq.getAnswer());
                BackApplication.LOGGER.info("Updating faq ["+faqItems+"]");
                FaqRepo.save(faqItems);
                return;
            }
        } catch(Exception e){
            BackApplication.LOGGER.log(Level.SEVERE, "Failed to update Faq ["+faq+"]", e);
        }
        throw new RuntimeException("Faq ["+faq+"] does not exist in DB.");
    }

}

