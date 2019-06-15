package com.project.controller.system;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.MessageModel.ErrorMessage;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController{

    @Autowired
    private SystemService sysService;

    /**
     * 校验账号密码
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResultObject checkLogin(@ModelAttribute User user, HttpSession session){

        if(StringUtils.isEmpty(user.getUsername())){
            return ResultObject.build(ErrorMessage.USERNAMENULL,ErrorMessage.USERNAMENULLMESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResultObject.build(ErrorMessage.PASSNULL,ErrorMessage.PHONENULLMESSAGE,null);
        }
        User checkResult =sysService.checkUser(user);

        if(ObjectUtils.isEmpty(checkResult)){
            return ResultObject.build(ErrorMessage.USERNULL,ErrorMessage.USERNULLMESSAGE,null);
        }
        //用户信息保存到session中
        session.setAttribute("user",checkResult);

        return ResultObject.success(checkResult);
    }

    /**
     * 退出登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(User user, HttpSession session){
        session.removeAttribute(Const.USER);
        return "redirect:/";
    }

    /**
     * 注册用户
     * @return
     */
    @RequestMapping("/registry")
    public ModelAndView registry(@ModelAttribute User user){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("loginController");
        return mv;
    }
}
