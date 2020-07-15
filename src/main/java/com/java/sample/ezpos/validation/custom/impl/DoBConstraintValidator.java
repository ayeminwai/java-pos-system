package com.java.sample.ezpos.validation.custom.impl;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.java.sample.ezpos.util.DateUtil;
import com.java.sample.ezpos.validation.custom.DOB;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DoBConstraintValidator implements ConstraintValidator<DOB, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean res = false;

		try {

			if (value == null || "".equals(value)) {
				return true;
			}

			// format check DD/MM/YYYY
			Date dob = DateUtil.convertDateStringToDate(value);

			if (dob != null) {

				// check for future date
				Date currDate = DateUtil.getCurrentDateNoTime();

				if (currDate.after(dob)) {
					res = true;
				}

			}

		} catch (Exception e) {
			log.error(e);
		}

		return res;
	}

	public static void main(String[] args) {
		ConstraintValidatorContext context = null;
		DoBConstraintValidator obj = new DoBConstraintValidator();
		System.out.println(obj.isValid("01/01/1999", context));
		
		Date currentDate = DateUtil.getCurrentDateNoTime();
		Date beforeDate = DateUtil.convertDateStringToDate("01/01/2000");
		if (currentDate.after(beforeDate)) {
			System.out.println("amw");
		}
	}

}
