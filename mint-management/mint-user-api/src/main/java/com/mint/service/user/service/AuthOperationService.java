package com.mint.service.user.service;

import javax.jws.WebParam;

import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.login.UpdatePwdData;
import com.mint.service.user.dto.reg.BasicInfo;
import com.mint.service.user.dto.reg.CredentialFormData;

@MintRpc(requestMapping = "/service/u_auth", serviceName = "mint-user")
public interface AuthOperationService {
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doReg")
	boolean doReg(@WebParam(name="data")CredentialFormData data, @WebParam(name="message")String message);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doUpdatePwd")
	boolean updatePwd(UpdatePwdData data);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doLogin")
	boolean doLogin(LoginFormData data);
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/doSaveInfo")
	boolean saveOrUpdateBasicInfo(BasicInfo info);
	
}
