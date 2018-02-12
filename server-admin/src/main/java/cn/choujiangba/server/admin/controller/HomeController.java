package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.config.C;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author:zhangyu
 * create on 15/10/21.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("Host", C.HOST);
        return "admin";
    }
}
