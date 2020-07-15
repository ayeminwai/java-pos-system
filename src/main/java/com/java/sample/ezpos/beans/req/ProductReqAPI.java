package com.java.sample.ezpos.beans.req;

import lombok.Getter;

@Getter
public class ProductReqAPI extends CoreAPIReq {
	
	private String productId;
	
	private String status;
}
