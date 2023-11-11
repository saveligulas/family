package gulas.family.family.home;

import gulas.family.family.REST_data.RegisterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    LoginService loginService;

    @GetMapping("")
    public ModelAndView getHomePage(@ModelAttribute("resultMessage") String resultMessage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("info", new RegisterInfo());
        modelAndView.addObject("resultMessage", resultMessage); 
        return modelAndView;
    }

    @PostMapping("submitForm")
    public ModelAndView handleSubmitForm(@ModelAttribute("info") RegisterInfo info) {
        String result = loginService.registerNewUser(info);

        return new ModelAndView("redirect:/")
                .addObject("resultMessage", result);
    }
}
