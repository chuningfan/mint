package com.mint.service.security.token;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.mint.common.context.LiteUserContext;
import com.mint.common.context.TokenHandler;
import com.mint.common.context.UserContext;

@Component
public class TokenHandlerImpl implements TokenHandler {

	private static final Logger LOG = LoggerFactory.getLogger(TokenHandlerImpl.class);
	
	public static final String SECRET = "af243f37c254";
	
	private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
	
	@Override
	public boolean validate(String token) {
		try {
            verifier.verify(token);
            return true;
		} catch (Exception e) {
			LOG.error("Invalid token: {}", e.getMessage());
			return false;
		}
	}

	@Override
	public String create(UserContext context, long expirePeriod, TimeUnit unit) {
		Date signTime = new Date();
		Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MILLISECOND, Integer.parseInt(unit.toMillis(expirePeriod) + ""));
        Date expireDate = nowTime.getTime();
        Map<String, Object> map = Maps.newHashMap();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map) // header
                .withClaim("accountId", context.getAccountId().toString()) // payload
                .withClaim("expMs", String.valueOf(unit.toMillis(expirePeriod)))
                .withClaim("cdm", context.getCookieDomain())
                .withClaim("pl", System.currentTimeMillis())
                .withIssuedAt(signTime) // sign time
                .withExpiresAt(expireDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature
		return token;
	}

	@Override
	public LiteUserContext parse(String token) {
		validate(token);
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> map = jwt.getClaims();
		LiteUserContext context = new LiteUserContext();
		Claim c = map.get("accountId");
		context.setAccountId(c.asLong());
		c = map.get("cdm");
		context.setCookieDomain(c.asString());
		c = map.get("expMs");
		context.setExpirePeriodMs(c.asLong());
		c = map.get("pl");
		context.setPrevLoginTime(c.asLong());
		return context;
	}

	@Override
	public JWTVerifier getVerifier() {
		return verifier;
	}
	
}
