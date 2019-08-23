package com.mint.service.auth.normal.action;

import org.springframework.web.client.RestTemplate;

import com.mint.service.auth.normal.data.DataWithReqAndResp;

public enum Action {
	
	DO_REG {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	}, REG {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	}, DO_LOGIN {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	}, LOGIN {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	}, DO_UPDATE_PASSWORD {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	}, UPDATE_PASSWORD {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			// TODO Auto-generated method stub
			
		}
	}, LOGOUT {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			// TODO Auto-generated method stub
			
		}
	}, GET_BACK_PASSWORD {
		@Override
		public void doAction(RestTemplate restTemplate, DataWithReqAndResp data) {
			
		}
	};
	
	public abstract void doAction(RestTemplate restTemplate, DataWithReqAndResp data);
	
}
