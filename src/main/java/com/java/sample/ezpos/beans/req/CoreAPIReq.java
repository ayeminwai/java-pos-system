package com.java.sample.ezpos.beans.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreAPIReq {
	
	@NotEmpty(message = "{reqDateTime.not.empty}")
	@Size(min = 14, max = 14, message = "{reqDateTime.size}")
	private String reqDateTime;
}
