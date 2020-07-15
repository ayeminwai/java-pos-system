package com.java.sample.ezpos.constant;

public interface IMessages {

	public interface IServer {
		public static final String INTERNAL_SERVER_ERROR =  "Server Error";
	}

	public interface IError {
		public static final String VALIDATION_FAILED =  "Fileds Validations Failed";
		public static final String INTERNAL_ERROR =  "Internal Error on Processing the Request";
		public static final String UNAUTHORIZED =  "Unauthorized Request";
		public static final String BAD_REQUEST =  "Some of the Validations Failed";
		public static final String DATETIME_VALIDATION_FAILED = "Datetime Validation Failed";

		public interface IProduct {
			public static final String ERROR_CARDSTAUS_FIND =  "Error on Product Info Finding";
			public static final String STATUS_NOT_AVAILABLE =  "No Product Status available";
		}

		public interface IQRCAuth {
			public static final String REGISTERED = "Account is already registered";
			public static final String GO_LOGIN = "Please login first";
			public static final String LOGIN_OVER_LIMIT_COUNT = "Your Login over limit count";
			public static final String INVALID_PASSWORD = "Invalid Password.";
			public static final String INVALID_MOBILE_NO = "Invalid Mobile no.";
			public static final String INVALID_USER = "Invalid user account.";
			public static final String INVALID_REGISTER = "Invalid register";
		}
	}

	public interface ISuccess {
		public static final String SUCCESS =  "Request Processed Successfully";
		public static final String LIST_SUCCESS =  "List Request Processed Successfully";
		public static final String CREATE_SUCCESS =  "Create Request Processed Successfully";

		public interface IProduct {
			
		}
		
		public interface IQRCAuth {
			
		}
	}

}
