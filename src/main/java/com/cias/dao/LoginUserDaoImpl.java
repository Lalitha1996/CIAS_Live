package com.cias.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.TellerMaster;
import com.cias.utility.CebiConstant;

@Repository
public class LoginUserDaoImpl implements LoginDao {

	private static final Logger logger = Logger.getLogger(LoginUserDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	CebiConstant cebiConstant;
	

	@SuppressWarnings("unchecked")
	@Transactional()
	public List<Object[]> validateLoginUser(TellerMaster tellerMaster) {
		logger.info("inside validatLoginUser start time::" + System.currentTimeMillis());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT T.tellerid,T.ip,T.branchid,T.bankCode,T.ccdp FROM TellerMaster T WHERE tellerid=:tellerid AND pwd=:pwd");
		query.setParameter("tellerid", tellerMaster.getTellerid());
		query.setParameter("pwd", tellerMaster.getPwd());
		List<Object[]> results = (List<Object[]>) query.list();
		logger.info("inside validatLoginUser end time::" + System.currentTimeMillis());
		return results;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> validateSuperLoginUser(TellerMaster tellerMaster) {
		logger.info("inside validateSuperLoginUser start time::" + System.currentTimeMillis());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT T.tellerid,T.ip,T.branchid,T.bankCode,T.ccdp FROM TellerMaster T WHERE tellerid=:tellerid AND pwd=:pwd AND bankCode=:bankcode");
		query.setParameter("tellerid", tellerMaster.getTellerid());
		query.setParameter("pwd", tellerMaster.getPwd());
		query.setParameter("bankcode", tellerMaster.getBankCode());
		List<Object[]> results = (List<Object[]>) query.list();
		logger.info("inside validateSuperLoginUser end time::" + System.currentTimeMillis());
		return results;
	}


	protected Map<String,String> populateExceptions(){
		Map<String,String> exceptions = new HashMap<String, String>();
		exceptions.put(CebiConstant.NAME_ALREADY_USED_ERR, CebiConstant.NAME_ALREADY_USED_ERR);
		exceptions.put(CebiConstant.SERVICE_REQUESTED_CONNECT_ERR, CebiConstant.SERVICE_REQUESTED_CONNECT_ERR);
		exceptions.put(CebiConstant.Communication_Channel, CebiConstant.Communication_Channel);
		exceptions.put( CebiConstant.VIEW_DOESNOT_EXIST, CebiConstant.VIEW_DOESNOT_EXIST);
		return exceptions;
	}
}
