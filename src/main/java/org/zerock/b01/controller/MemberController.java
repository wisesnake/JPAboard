package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.service.MemberService;

import java.nio.file.attribute.UserPrincipal;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    MemberService memberService;



    @GetMapping("/login")
    public void loginGET(String error, String logout){
        log.info("login get........");
        log.info("logout: " + logout);

        if(logout != null){
            log.info("user logout...");
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/join")
    public void joinGET() {

        log.info("join.......");

    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){
        log.info("join post....");
        log.info(memberJoinDTO);

        try{
            memberService.join(memberJoinDTO);
        } catch(MemberService.MidExistException e){
            redirectAttributes.addFlashAttribute("error","mid");
            return "redirect:/member/join";
        }

        redirectAttributes.addAttribute("result","success");

        return "redirect:/member/login";
    }
}
