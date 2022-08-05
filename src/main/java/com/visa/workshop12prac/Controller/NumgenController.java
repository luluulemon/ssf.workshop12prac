package com.visa.workshop12prac.Controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.visa.workshop12prac.Exception.RandomNumberException;
import com.visa.workshop12prac.Model.ImageNum;

@Controller
public class NumgenController {
    private static final Logger logger = LoggerFactory.getLogger(NumgenController.class);


    @RequestMapping("/start")
    public String start1(Model model){
        model.addAttribute("ImageNum", new ImageNum());
        
        return "startpage";
    }
    
    
    @GetMapping("/generate")
    public String GenImages(@ModelAttribute ImageNum number, Model model){

        int NumOfImages = number.getAmount();
        number = generateNums(NumOfImages);
        number.setAmount(NumOfImages);
        model.addAttribute("NumList", number);
        
        return "genNumImage";
    }


    @GetMapping("/requestParam")
    public String testParam(@RequestParam String amount, Model model){

        ImageNum number = generateNums(Integer.parseInt(amount));
        number.setAmount(Integer.parseInt(amount));
        model.addAttribute("NumList", number);
        return "genNumImage";
    }

        // pathVar image does not load?
    @GetMapping("/pathVar/{amount}")
    public String PathVar(@PathVariable String amount, Model model){

        logger.info("Logger in PathVar " + amount);
        ImageNum number = generateNums(Integer.parseInt(amount));
        logger.info("Check status >>>>>>");
        number.setAmount(Integer.parseInt(amount));
        model.addAttribute("NumList", number);
        return "genNumImage";
    }


    public ImageNum generateNums(int NumOfImages){
        
        if (NumOfImages < 0 || NumOfImages > 31) {
            throw new RandomNumberException("Number is out of bounds!");
        }

        ImageNum number = new ImageNum();
        Random random = new Random();
        Set<Integer> NumContainer = new HashSet<>();
                   
            while(NumContainer.size() < NumOfImages){
                NumContainer.add(random.nextInt(31));
            }
        
        number.setSetOfNum(NumContainer);

        return number;
    }

}
