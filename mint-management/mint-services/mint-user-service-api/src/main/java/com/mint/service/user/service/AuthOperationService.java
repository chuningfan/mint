package com.mint.service.user.service;

import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.common.context.UserContext;
import com.mint.common.dto.web.WebResponse;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.login.UpdatePwdData;
import com.mint.service.user.dto.reg.BasicInfo;
import com.mint.service.user.dto.reg.CredentialFormData;

@MintRpc(requestMapping = "/u_auth", serviceName = "mint-user-service")
public interface AuthOperationService {
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doReg")
	WebResponse<Boolean> doReg(CredentialFormData data);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doUpdatePwd")
	WebResponse<Boolean> updatePwd(UpdatePwdData data);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doLogin")
	WebResponse<UserContext> doLogin(LoginFormData data);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doSaveInfo")
	WebResponse<Boolean> saveOrUpdateBasicInfo(BasicInfo info);
	
//	@MethodMapping(requestMethod = RequestMethod.GET, value = "/doTest/{param1}/{param2}")
//	boolean doTest(@PathVariable(name="param1") String param1, @PathVariable(name="param2") String param2);
	
}
