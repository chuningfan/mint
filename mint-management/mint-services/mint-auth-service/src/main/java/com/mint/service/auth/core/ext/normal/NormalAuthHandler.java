package com.mint.service.auth.core.ext.normal;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.TokenHandler;
import com.mint.common.context.UserContext;
import com.mint.common.dto.web.WebResponse;
import com.mint.common.enums.LoginType;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.HttpUtil;
import com.mint.service.auth.core.AuthHandler;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.rpc.RpcHandler;
import com.mint.service.security.token.TokenResolverImpl;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.reg.CredentialFormData;
import com.mint.service.user.service.AuthOperationService;

@Component
public class NormalAuthHandler extends AuthHandler {
	
	@Autowired
	private RpcHandler rpcHandler;
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Autowired
	private TokenHandler tokenHandler;
	
	@Value("${auth.redis.expire.timeSc}")
	private String expireSc;
	
	@Value("${auth.cookie.domain}")
	private String domain;
	
	@Override
	protected WebResponse<Boolean> doReg(Object... data) throws MintException {
		try {
			String username = data[0].toString();
			String password = data[1].toString();
			AuthOperationService aoh = rpcHandler.get(AuthOperationService.class);
			CredentialFormData formData = new CredentialFormData();
			formData.setUsername(username);
			formData.setPassword(password);
			return aoh.doReg(formData);
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

	@Override
	protected WebResponse<Boolean> reg(Object... data) {
		
		return null;
	}

	@Override
	protected WebResponse<Boolean> doLogin(Object... data) throws MintException {
		HttpServletRequest req = (HttpServletRequest) data[0];
		String token = HttpUtil.getCookieValue(req, UserContextKeys.USER_CONTEXT);
		if (StringUtils.isEmpty(token)) {
			token = req.getHeader(UserContextKeys.USER_CONTEXT);
		}
		UserContext context;
		try {
			if (StringUtils.isNotBlank(token) && tokenHandler.validate(token)) {
				return new WebResponse<Boolean>(true);
			}
		} catch (Exception e1) {
			throw MintException.getException(e1, null);
		}
		HttpServletResponse resp = (HttpServletResponse) data[1];
		String username = data[2].toString();
		String password = data[3].toString();
		AuthOperationService aos = rpcHandler.get(AuthOperationService.class);
		LoginFormData formData = new LoginFormData();
		formData.setUsername(username);
		formData.setPassword(password);
		formData.setLoginType(LoginType.NORMAL);
		context = aos.doLogin(formData).getData();
		if (context == null) {
			throw MintException.getException(Error.INVALID_CONTEXT_ERROR, null, null);
		}
		try {
			Long expireSeconds = Long.valueOf(expireSc);
			context.setPrevLoginTime(System.currentTimeMillis());
			context.setCookieDomain(domain);
			context.setExpirationPeriodMs(expireSeconds);
			context.setFromIP(HttpUtil.getIpAddress(req));
			token = tokenHandler.create(context, expireSeconds * 1000, TimeUnit.MILLISECONDS);
			TokenResolverImpl.newCookie(resp, UserContextKeys.USER_CONTEXT, token, domain);
//			contextWrapper.storeIntoCache(context.getAccountId().toString(), context);
			redisHelper.store(context.getAccountId().toString(), context, expireSeconds, TimeUnit.SECONDS);
			return new WebResponse<Boolean>(true);
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

	@Override
	protected WebResponse<Boolean> login(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> logout(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> doUpdatePwd(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> updatePwd(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> doGetBackPwd(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> getBackPwd(Object... data) {
		return null;
	}

	@Override
	protected WebResponse<Boolean> checkDuplicateUserName(Object... data) {
		return null;
	}
	
	@Override
	public LoginType getLoginType() {
		return LoginType.NORMAL;
	}

}
