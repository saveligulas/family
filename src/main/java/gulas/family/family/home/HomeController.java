package gulas.family.family.home;

import gulas.family.family.REST_data.LoginInfo;
import gulas.family.family.REST_data.RegisterInfo;
import gulas.family.family.errorHandler.handler.ApiRequestException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    UserService userService;
    @

    @GetMapping("result")
    public ModelAndView getResult(@ModelAttribute("resultMessage") String resultMessage) {
        ModelAndView modelAndView = new ModelAndView();
        String example = "This message is encrypted";

        modelAndView.setViewName("result");
        modelAndView.addObject("resultMessage", resultMessage);
        modelAndView.addObject("decodedMessage", )
        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView getHomePage(@ModelAttribute("resultMessage") String resultMessage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("info", RegisterInfo.builder().build());
        modelAndView.addObject("resultMessage", resultMessage);
        return modelAndView;
    }

    @GetMapping("login")
    public ModelAndView getLoginPage(@ModelAttribute("resultMessage") String resultMessage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("info", new LoginInfo());
        modelAndView.addObject("resultMessage", resultMessage);
        return modelAndView;
    }

    @PostMapping("submitForm")
    public ModelAndView handleSubmitForm(@ModelAttribute("info") RegisterInfo info) {
        String result = userService.registerNewUser(info);

        return new ModelAndView("redirect:/result")
                .addObject("resultMessage", result);
    }

    @SneakyThrows
    @PostMapping("submitLogin")
    public ModelAndView handleSubmitLogin(@ModelAttribute("info") LoginInfo info, HttpServletResponse response) {
        String cookie;
        try {
            cookie = userService.loginUserGetPrivateKeyString(info);
        } catch (ApiRequestException e) {
            cookie = URLEncoder.encode(e.getMessage(), "UTF-8");
        }
        System.out.println(cookie);
        Cookie webCookie = new Cookie("private_key", cookie);
        webCookie.setMaxAge(Integer.MIN_VALUE);
        response.addCookie(webCookie);

        return new ModelAndView("redirect:/result")
                .addObject("resultMessage", cookie);
    }
}
