package com.cias.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class GenerateCheckDigitServiceImpl implements GenerateCheckDigitService {

	Properties prop = new Properties();
	InputStream input = null;

	@Override
	public String populateParameters(List<String> checkDigitColumns, String table) {
		String parameters = null;
		List<String> paraList = new ArrayList<String>();
		populateCheckDigitForTable(paraList, checkDigitColumns);
		parameters = String.join(", ", paraList);
		return parameters;
	}

	private void populateCheckDigitForTable(List<String> paraList, List<String> checkDigitColumns) {
		for (String list : checkDigitColumns) {
			if (list.contains("KEY_1")) {
				list = "substr(KEY_1,4,16) || CHECKDIGIT.GENERATE(KEY_1) As KEY_1";
			} else if (list.contains("CUSTOMER_NO")) {
				list = "substr(KEY_1,4,16) || CHECKDIGIT.GENERATE(CUSTOMER_NO) As CUSTOMER_NO";
			}
			paraList.add(list);
		}
	}

}
