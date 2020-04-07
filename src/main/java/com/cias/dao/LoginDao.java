package com.cias.dao;

import java.util.List;

import com.cias.entity.TellerMaster;

public interface LoginDao {
	
	public List<Object[]> validateLoginUser(TellerMaster tellerMaster);

	public List<Object[]> validateSuperLoginUser(TellerMaster tellerMaster);

}
