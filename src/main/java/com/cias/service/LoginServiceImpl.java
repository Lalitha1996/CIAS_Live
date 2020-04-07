package com.cias.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.LoginDao;
import com.cias.entity.TellerMaster;

@Service
public class LoginServiceImpl implements LoginService {
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	LoginDao loginDao;

	@Override
	public List<Object[]> validateLoginUser(TellerMaster tellerMaster) {
		logger.info("Inside validateLoginUser()");
	//	tellerMaster.setPwd(AES.getMD5EncryptedValue(tellerMaster.getPwd()));
		return loginDao.validateLoginUser(tellerMaster);
	}

	
	@Override
	public List<Object[]> validateSuperLoginUser(TellerMaster tellerMaster) {
		//tellerMaster.setPwd(AES.getMD5EncryptedValue(tellerMaster.getPwd()));
		return loginDao.validateSuperLoginUser(tellerMaster);
	}

}
