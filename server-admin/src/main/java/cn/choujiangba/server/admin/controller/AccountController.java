package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.config.C;
import cn.choujiangba.server.bal.api.AdminService;
import cn.choujiangba.server.bal.exception.BizException;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 管理员登陆注册相关接口
 *
 * Author:zhangyu
 * create on 15/10/19.
 */
@Controller
@RequestMapping("/account")
public class AccountController extends KaptchaExtend {

    private final static Logger logger= LoggerFactory.getLogger(AccountController.class);

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        super.captcha(servletRequest, servletResponse);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("Host", C.HOST);
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
                        HttpServletRequest servletRequest,
                        @RequestParam(value="username",required=true)String username,
                        @RequestParam(value="password",required=true)String password,
                        @RequestParam(value="kaptcha",required = true)String kaptcha
    ) throws BizException {
        logger.info(String.format("login [username=%s,password=%s,kaptcha=%s]",username,password,kaptcha));

        Map<String,String> result=new HashMap<>();

        //判断验证码
        if(!super.getGeneratedKey(servletRequest).equals(kaptcha)){
            result.put("result","failed");
            result.put("message","验证码错误");
            return result;
        }

        try{
            adminService.validate(username,password);

            result.put("result","success");
            result.put("message",C.HOST+"/home");
        }catch (BizException e){
            if(e.getErrorCode().equals(BizException.ERROR_CODE_USER_NOT_FOUND))
            {
                result.put("result","failed");
                result.put("message","账户不存在");
            }

            throw e;
        }

        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest servletRequest){
        return null;
    }
}
