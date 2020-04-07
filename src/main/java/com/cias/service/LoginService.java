package com.cias.service;

import java.util.List;

import com.cias.entity.TellerMaster;

public interface LoginService {
	public List<Object[]> validateLoginUser(TellerMaster tellerMaster); //



	public List<Object[]> validateSuperLoginUser(TellerMaster tellerMaster); //
}
