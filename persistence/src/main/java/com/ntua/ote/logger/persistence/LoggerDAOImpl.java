package com.ntua.ote.logger.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.ntua.ote.logger.core.common.Utils;
import com.ntua.ote.logger.core.enums.Direction;
import com.ntua.ote.logger.core.enums.LogType;
import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.jpa.Log;
import com.ntua.ote.logger.persistence.jpa.Log_;

@ApplicationScoped
public class LoggerDAOImpl implements LoggerDAO {

	@PersistenceContext(unitName = "logger")
	private EntityManager entityManager;

	private static final Logger LOGGER = Logger.getLogger(LoggerDAOImpl.class);

	@Override
	public long addLog(Log log) {
		// EntityTransaction transaction = entityManager.getTransaction();
		try {
			// transaction.begin();
			entityManager.persist(log);

			entityManager.flush();
			// transaction.commit();
			return log.getId();
		} catch (Exception e) {
			LOGGER.error("<add Log> :", e);
			return -1L;
		}
	}

	@Override
	public int updateLocation(long id, double longitude, double latitude) {
		try {
			Log log = entityManager.find(Log.class, id);
			if (log != null) {
				log.setLongitude(longitude);
				log.setLatitude(latitude);
				entityManager.flush();
				return 1;
			} else {
				LOGGER.error("<updateLocation> id not found");
				return -1;
			}
		} catch (Exception e) {
			LOGGER.error("<updateLocation> :", e);
			return -1;
		}
	}

	@Override
	public int updateDuration(long id, int duration) {
		try {
			Log log = entityManager.find(Log.class, id);
			if (log != null) {
				log.setDuration(duration);
				entityManager.flush();
				return 1;
			} else {
				LOGGER.error("<updateLocation> id not found");
				return -1;
			}
		} catch (Exception e) {
			LOGGER.error("<updateDuration> :", e);
			return -1;
		}
	}
	
	public List<LogDetails> getLogDetails() {
		try {
			List<Log> logs = entityManager.createQuery("SELECT l FROM Log l").getResultList();
			return convertLog(logs);
		} catch (Exception e) {
			LOGGER.error("<updateDuration> :", e);
			return null;
		}
	}

	public List<LogDetails> search(SearchCriteria searchCriteria) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Log> query = cb.createQuery(Log.class);
			Root<Log> sm = query.from(Log.class);
			List<Predicate> predicates = new ArrayList<>();
			if(StringUtils.hasText(searchCriteria.getPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.phoneNumber), searchCriteria.getPhoneNumber()));
			}
			if(searchCriteria.getDateFrom() != null) {
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.dateTime), new Timestamp(searchCriteria.getDateFrom().getTime())));			
			}
			if(searchCriteria.getDateTo() != null) {
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.dateTime), new Timestamp(searchCriteria.getDateTo().getTime())));			
			}
			if(StringUtils.hasText(searchCriteria.getExternalPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.extPhoneNumber), searchCriteria.getExternalPhoneNumber()));
			}
			if(StringUtils.hasText(searchCriteria.getDirection())) {
				predicates.add(cb.equal(sm.get(Log_.direction), Direction.valueOf(searchCriteria.getDirection().toUpperCase())));
			}
			if(StringUtils.hasText(searchCriteria.getLogType())) {
				predicates.add(cb.equal(sm.get(Log_.logType), LogType.valueOf(searchCriteria.getLogType().toUpperCase())));
			}
			if(searchCriteria.getLongitude() != null && searchCriteria.getLatitude() != null && searchCriteria.getRadius() > 0) {
				double[] diffLatLng = Utils.calculateLatLngFromRadius(searchCriteria.getRadius(), searchCriteria.getLatitude().doubleValue(), 
						searchCriteria.getLongitude().doubleValue());
				
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.latitude), searchCriteria.getLatitude().doubleValue() - diffLatLng[0]));
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.latitude), searchCriteria.getLatitude().doubleValue() + diffLatLng[0]));
				
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.longitude), searchCriteria.getLongitude().doubleValue() - diffLatLng[1]));
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.longitude), searchCriteria.getLongitude().doubleValue() + diffLatLng[1]));
			}
			if(!predicates.isEmpty()) {
				Predicate andClause = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(andClause);
			}
			
			List<Log> logs = entityManager.createQuery(query).getResultList();
			return convertLog(logs);
		} catch (Exception e) {
			LOGGER.error("<search> :", e);
			return null;
		}
	}
	
	private List<LogDetails> convertLog(List<Log> logs){
		List<LogDetails> logDetails = new ArrayList<LogDetails>();
		for(Log log : logs) {
			LogDetails logDetail = new LogDetails();
			logDetail.setDateTime(new Date(log.getDateTime().getTime()));
			logDetail.setExternalPhoneNumber(log.getExtPhoneNumber());
			logDetail.setPhoneNumber(log.getPhoneNumber());
			logDetail.setDuration(Utils.timeToString(log.getDuration()));
			logDetail.setLatitude(log.getLatitude());
			logDetail.setLongitude(log.getLongitude());
			logDetail.setBrandModel(log.getBrandModel());
			logDetail.setVersion(log.getVersion());
			logDetail.setCellId(log.getCellId());
			logDetail.setDirection(log.getDirection());
			logDetail.setImei(log.getImei());
			logDetail.setImsi(log.getImsi());
			logDetail.setLac(log.getLac());
			logDetail.setLogType(log.getLogType());
			logDetail.setLteCQI(log.getLteCQI());
			logDetail.setLteRSRP(log.getLteRSRP());
			logDetail.setLteRSRQ(log.getLteRSRQ());
			logDetail.setLteRSSNR(log.getLteRSSNR());
			logDetail.setRssi(log.getRssi());
			logDetail.setRat(log.getRat());
			logDetails.add(logDetail);
		}
		return logDetails;
	}
}
