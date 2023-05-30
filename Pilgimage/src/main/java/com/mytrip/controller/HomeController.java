package com.mytrip.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.expression.Fields;

import com.mytrip.dao.UserRepositray;
import com.mytrip.helper.Message;
import com.mytrip.modal.User;
import com.mytrip.service.Userservice;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private Userservice userservice;

	@GetMapping("/")
	public String homePage(Model m) {
		m.addAttribute("title", "home");
		return "index";
	}

	@GetMapping("/package")
	public String packkage(Model m) {
		List<String> name = List.of("sam", "ajit", "pune", "kjd");
		m.addAttribute("title", "package");
		m.addAttribute("names", name);
		return "package";
	}

	@GetMapping("/login")
	public String loginPage(Model m) {
		m.addAttribute("title", "UserLogin");
		return "login";
	}

	@GetMapping("/register")
	public String registerAccount(Model m) {
		m.addAttribute("title", "UserRegister");
		m.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/hotel")
	public String Hotel(Model m) {
		m.addAttribute("title", "UserHotel");
		return "hotel";
	}

	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("user") User user, BindingResult r,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam("cpassword") String cpass, Model m, HttpSession session) {

		try {
			if (!agreement) {
				throw new Exception("trem and condtion");
			}

			if (r.hasErrors()) {

				m.addAttribute("user", user);
				return "register";
			}
			if (!user.getPassword().equals(cpass)) {
				session.setAttribute("msg", "Something Went Wrong");
				return "register";
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

			User res = this.userservice.userSave(user);
			if(res==null) {
				session.setAttribute("msg", "Something Went Wrong");
			}

			m.addAttribute("user", new User());
			session.setAttribute("msg", "Succesfull Registration");
			return "redirect:user/login";
		} catch (Exception e) {

			m.addAttribute("user", user);
			session.setAttribute("msg", "Something Went Wrong");
			e.printStackTrace();
			return "register";
		}

	}

}
