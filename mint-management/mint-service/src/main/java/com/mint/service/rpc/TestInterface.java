package com.mint.service.rpc;

import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.common.enums.ProtocolType;

@MintRpc(protocolType = ProtocolType.HTTP, serviceName = "test-service")
public interface TestInterface {

	@MethodMapping(requestMethod = RequestMethod.POST, value="/testmethod")
	String test(String name);
	
	public static void main(String[] args) {
		RpcHandler h = new RpcHandler();
		TestInterface ti = h.get(TestInterface.class);
		ti.test("hello");
	}
	
}
