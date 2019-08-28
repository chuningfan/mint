package com.mint.service.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mint.common.context.UserContextThreadLocal;

@RequestMapping("/app")
@Controller
public class IndexController {
	
	@GetMapping
	public String index(HttpServletResponse resp) throws Exception   {
		if (UserContextThreadLocal.get() == null) {
			resp.sendRedirect("http://localhost:8088/mint-auth/page/login");
		}
		return "/index";
	}
	
}
