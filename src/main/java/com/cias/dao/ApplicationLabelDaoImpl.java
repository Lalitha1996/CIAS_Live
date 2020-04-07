package com.cias.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.ApplicationLabel;
import com.cias.utility.ApplicationLabelCache;

@Repository
public class ApplicationLabelDaoImpl implements ApplicationLabelDao {

	private static final Logger logger = Logger.getLogger(ApplicationLabelDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ApplicationLabel> retrieveAllLabels() {
		logger.info("retrieveAllLabels start time:: " + System.currentTimeMillis());
		List<ApplicationLabel> applicationLabels = null;
		ApplicationLabel label =null;
		List<ApplicationLabel> labels = new ArrayList<ApplicationLabel>();
		Map<String, List<ApplicationLabel>> cache = ApplicationLabelCache.getLabelInstance();
		if (cache.get("labels")==null) {
			Session session = sessionFactory.getCurrentSession();
			applicationLabels = (List<ApplicationLabel>) session.createQuery("FROM ApplicationLabel").setCacheable(true).list();
			
			for(ApplicationLabel lbl:applicationLabels){
				label = new ApplicationLabel();
				label.setLabelCode(lbl.getLabelCode().trim());
				label.setAppLabel(lbl.getAppLabel().trim());
				labels.add(label);
			}
			
			cache.put("labels", labels);
		} else {
			for (Map.Entry<String, List<ApplicationLabel>> entry : cache.entrySet()) {
				applicationLabels = (List<ApplicationLabel>) entry.getValue();
			}
		}

		logger.info("retrieveAllLabels End Time:: " + System.currentTimeMillis());
		return applicationLabels;
	}

}
