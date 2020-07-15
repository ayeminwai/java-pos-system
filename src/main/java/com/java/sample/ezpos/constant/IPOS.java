package com.java.sample.ezpos.constant;

public interface IPOS {

	public interface IQualifier {
		public final String USER_VALIDATION = "userValidationImpl";
		public final String PRODUCT_VALIDATION = "productValidationImpl";
	}

	public interface IKey {
		public interface IResponse {
			public final String STATUS_CODE = "statusCode";
			public final String DESC = "description";
			public final String RESPONSE = "response";
			public final String ERROR = "errors";
		}
	}

	public interface IValue {
		public interface IResponse {
			public final String SUCCESS = "Success";
			public final String INTERNAL_SERVER_ERROR = "Internal Server Error";
		}
	}

	public interface IHeader {
		public final String REQ_DATETIME = "ReqDatetime";
		public final String RES_DATETIME = "ResDatetime";
	}

	public interface IPayLoad {
		public final String RES_DATETIME = "resDatetime";
		public final String REQ_DATETIME = "reqDateTime";
	}

	public interface ITranxCode {
		public final String CREATE = "CREATE";
		public final String INQUERY = "INQUERY";
		public final String REFUND = "REFUND";
		public final String REVERSAL = "REVERSAL";
	}

	public interface ISwagger {
		public final String REFERENCE = "oauth2schema";
	}

	public interface IUpdated {
		public final String BY = "EzposAPI";
	}

	public interface IAppStatus {
		public final String PROCESSED = "4";
	}

	public interface IOauthUserStatus {
		public final String ACTIVE = "A";
		public final String INACTIVE = "I";
	}

	public interface IDateTime {
		public final String FORMAT = "ddMMyyyyHHmmss";
		public final Integer LENGTH = 14;
	}
	
	public interface IUser {
		public interface Gender {
			public final String MALE = "M";
			public final String FEMALE = "F";
		}
		
		public interface Role {
			public final String ADMIN = "ROLE_ADMIN";
			public final String USER = "ROLE_USER";
		}
	}
}
