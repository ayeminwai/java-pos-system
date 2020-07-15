-- ------ Create DB Schema ---- --
CREATE SCHEMA `ezpos` ;

-- ------ Create auth_user table for login authentication ---- --
CREATE TABLE `auth_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  
-- ------ Create api req/res log table ---- --
  
CREATE TABLE `api_req_res_log` (
  `api_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `req_url` varchar(100) DEFAULT NULL,
  `process_time` int(11) DEFAULT NULL,
  `process_time_ms` int(11) DEFAULT NULL,
  `response` varchar(10) DEFAULT NULL,
  `req_payload` varchar(800) DEFAULT NULL,
  `res_payload` varchar(1000) DEFAULT NULL,
  `remote_address` varchar(100) DEFAULT NULL,
  `session_id` varchar(100) DEFAULT NULL,
  `req_method` varchar(20) DEFAULT NULL,
  `is_req_payload_skip` char(1) DEFAULT NULL,
  `is_res_payload_skip` char(1) DEFAULT NULL,
  `api_code` varchar(4) DEFAULT NULL,
  `req_datetime_on_hdr` varchar(14) DEFAULT NULL,
  `res_datetime_on_hdr` varchar(14) DEFAULT NULL,
  `error_details` varchar(200) DEFAULT NULL,
  `remote_ip` varchar(50) DEFAULT NULL,
  `remote_ip_location` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`api_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  
  -- --- User Details Table ---------------
  
CREATE TABLE `user_details` (
  `user_id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `mobile_no` varchar(15) DEFAULT NULL,
  `primary_address` varchar(200) DEFAULT NULL,
  `current_address` varchar(200) DEFAULT NULL,
  `status` char(1) DEFAULT 'A',
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  
  -- --- Product Table ------------------
    
  -- --- Category Table -----------------
  
  -- --- Sales User Table ---------------
  
  -- --- Counter Table ------------------
  
  -- --- Sale header Table --------------
  
  -- --- Sale Details Table -------------

  -- --- Transaction Log Table ----------
