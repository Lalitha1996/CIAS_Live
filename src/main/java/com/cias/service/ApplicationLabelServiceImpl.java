package com.cias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.dao.ApplicationLabelDao;
import com.cias.entity.ApplicationLabel;

@Repository
public class ApplicationLabelServiceImpl implements ApplicationLabelService {

	@Autowired
	ApplicationLabelDao applicationLabelDao;

	public List<ApplicationLabel> retrieveAllLabels() {

		return applicationLabelDao.retrieveAllLabels();
	}
}