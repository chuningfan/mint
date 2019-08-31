package com.mint.service.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mint.common.context.TokenThreadLocal;

@RequestMapping("/app")
@Controller
public class IndexController {
	
	@GetMapping
	public String index(HttpServletResponse resp) throws Exception   {
		if (TokenThreadLocal.get() == null) {
			resp.sendRedirect("http://localhost:8088/mint-auth/page/login");
		}
		return "/index";
	}
	
}
