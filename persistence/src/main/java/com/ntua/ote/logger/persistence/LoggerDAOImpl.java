package com.ntua.ote.logger.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.ntua.ote.logger.core.common.Utils;
import com.ntua.ote.logger.core.enums.Direction;
import com.ntua.ote.logger.core.enums.LogType;
import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.Node;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.core.models.SearchResults;
import com.ntua.ote.logger.persistence.jpa.Log;
import com.ntua.ote.logger.persistence.jpa.Log_;

@ApplicationScoped
public class LoggerDAOImpl implements LoggerDAO {

	@PersistenceContext(unitName = "logger")
	private EntityManager entityManager;

	private static final Logger LOGGER = Logger.getLogger(LoggerDAOImpl.class);

	/** Adds a new Log entry in the database */
	@Override
	public long addLog(Log log) {
		try {
			entityManager.persist(log);
			entityManager.flush();
			return log.getId();
		} catch (Exception e) {
			LOGGER.error("<add Log> :", e);
			return -1L;
		}
	}

	/** Updates a Log entry in the database with the location */
	@Override
	public int updateLocation(long id, double longitude, double latitude) {
		try {
			Log log = entityManager.find(Log.class, id);
			if (log != null) {
				log.setLongitude(longitude);
				log.setLatitude(latitude);
				log.setRadius(0);
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

	/**
	 * Updates a Log entry in the database with the location and radius obtained
	 * from MLS
	 */
	@Override
	public int updateLocation(long id, double longitude, double latitude, double radius) {
		try {
			Log log = entityManager.find(Log.class, id);
			if (log != null) {
				log.setLongitude(longitude);
				log.setLatitude(latitude);
				log.setRadius(radius);
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

	/** Updates a Log entry in the database with the duration */
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

	/** Fetches a Log entry from the database with id */
	@Override
	public Log get(Long id) {
		try {
			Log log = entityManager.find(Log.class, id);
			return log;
		} catch (Exception e) {
			LOGGER.error("<get> :", e);
			return null;
		}
	}

	/** Gets the @limit latest logs from database */
	public List<LogDetails> getLogDetails(int limit) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Log> query = cb.createQuery(Log.class);
			Root<Log> sm = query.from(Log.class);
			query.orderBy(cb.desc(sm.get(Log_.dateTime)));
			List<Log> logs = entityManager.createQuery(query).setMaxResults(limit).getResultList();
			return convertLog(logs);
		} catch (Exception e) {
			LOGGER.error("<updateDuration> :", e);
			return null;
		}
	}

	/** Searches the database based on the given criteria */
	public List<LogDetails> search(SearchCriteria searchCriteria) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Log> query = cb.createQuery(Log.class);
			Root<Log> sm = query.from(Log.class);
			List<Predicate> predicates = new ArrayList<>();
			if (searchCriteria.getId() > 0) {
				predicates.add(cb.equal(sm.get(Log_.id), searchCriteria.getId()));
			}
			if (StringUtils.hasText(searchCriteria.getPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.phoneNumber), searchCriteria.getPhoneNumber()));
			}
			if (searchCriteria.getDateFrom() != null) {
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateFrom().getTime())));
			}
			if (searchCriteria.getDateTo() != null) {
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateTo().getTime())));
			}
			if (StringUtils.hasText(searchCriteria.getExternalPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.extPhoneNumber), searchCriteria.getExternalPhoneNumber()));
			}
			if (StringUtils.hasText(searchCriteria.getDirection())) {
				predicates.add(cb.equal(sm.get(Log_.direction),
						Direction.valueOf(searchCriteria.getDirection().toUpperCase())));
			}
			if (StringUtils.hasText(searchCriteria.getLogType())) {
				predicates.add(
						cb.equal(sm.get(Log_.logType), LogType.valueOf(searchCriteria.getLogType().toUpperCase())));
			}
			if (StringUtils.hasText(searchCriteria.getSmsContent())) {
				String[] tokens = searchCriteria.getSmsContent().split(" ");
				for (String s : tokens) {
					predicates.add(cb.like(sm.get(Log_.smsContent), "%" + s + "%"));
				}
			}
			if (searchCriteria.getLongitude() != null && searchCriteria.getLatitude() != null
					&& searchCriteria.getRadius() > 0) {
				double[] diffLatLng = Utils.calculateLatLngFromRadius(searchCriteria.getRadius(),
						searchCriteria.getLatitude().doubleValue(), searchCriteria.getLongitude().doubleValue());

				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.latitude),
						searchCriteria.getLatitude().doubleValue() - diffLatLng[0]));
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.latitude),
						searchCriteria.getLatitude().doubleValue() + diffLatLng[0]));

				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.longitude),
						searchCriteria.getLongitude().doubleValue() - diffLatLng[1]));
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.longitude),
						searchCriteria.getLongitude().doubleValue() + diffLatLng[1]));

				predicates.add(cb.notEqual(sm.get(Log_.latitude), 0));
				predicates.add(cb.notEqual(sm.get(Log_.longitude), 0));
			}
			if (!predicates.isEmpty()) {
				Predicate andClause = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(andClause);
			}
			query.orderBy(cb.desc(sm.get(Log_.dateTime)));
			List<Log> logs = entityManager.createQuery(query).getResultList();
			return convertLog(logs);
		} catch (Exception e) {
			LOGGER.error("<search> :", e);
			return null;
		}
	}

	/** Searches the database based on the given criteria */
	public List<LogDetails> searchPath(SearchCriteria searchCriteria) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Log> query = cb.createQuery(Log.class);
			Root<Log> sm = query.from(Log.class);
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.hasText(searchCriteria.getPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.phoneNumber), searchCriteria.getPhoneNumber()));
			}
			if (searchCriteria.getDateFrom() != null) {
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateFrom().getTime())));
			}
			if (searchCriteria.getDateTo() != null) {
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateTo().getTime())));
			}
			predicates.add(cb.notEqual(sm.get(Log_.latitude), 0));
			predicates.add(cb.notEqual(sm.get(Log_.longitude), 0));

			if (!predicates.isEmpty()) {
				Predicate andClause = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(andClause);
			}
			query.orderBy(cb.asc(sm.get(Log_.dateTime)));
			List<Log> logs = entityManager.createQuery(query).getResultList();
			return convertLog(logs);
		} catch (Exception e) {
			LOGGER.error("<search> :", e);
			return null;
		}
	}

	/**
	 * Retrieves logs from tha database as List of nodes in order to create a
	 * graph
	 */
	public SearchResults searchRelation(SearchCriteria searchCriteria) {
		SearchResults results = new SearchResults();
		List<Node> nodes = new ArrayList<>();
		results.setNodes(nodes);
		if (StringUtils.hasLength(searchCriteria.getPhoneNumber())) {
			Node root = new Node(searchCriteria.getPhoneNumber(), 0);

			nodes.add(root);
			searchIntRelation(searchCriteria, root);
			searchExtRelation(searchCriteria, root);
			Set<String> uniqueSet = new HashSet<>();
			uniqueSet.add(searchCriteria.getPhoneNumber());
			List<Node> list = new ArrayList<>();
			for (Node s : root.getChildren()) {
				list.add(s);
				s.setLevel(1);
			}
			while (!list.isEmpty()) {
				Node node = list.get(0);
				if (node.getPhoneNumber().equals(searchCriteria.getExternalPhoneNumber())) {
					results.setRelationFound(true);
				}
				String phoneNumber = node.getPhoneNumber();
				int level = node.getLevel();
				if (!uniqueSet.contains(phoneNumber)) {
					searchIntRelation(searchCriteria, node);
					searchExtRelation(searchCriteria, node);
					for (Node s : node.getChildren()) {
						list.add(s);
						s.setLevel(level + 1);
					}
					nodes.add(node);
					uniqueSet.add(phoneNumber);
				}
				list.remove(0);
			}
		}
		return results;
	}

	private void searchIntRelation(SearchCriteria searchCriteria, Node parent) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Log> sm = query.from(Log.class);
			query.multiselect(sm.get(Log_.extPhoneNumber));
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.hasText(searchCriteria.getPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.phoneNumber), parent.getPhoneNumber()));
			}
			if (searchCriteria.getDateFrom() != null) {
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateFrom().getTime())));
			}
			if (searchCriteria.getDateTo() != null) {
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateTo().getTime())));
			}

			if (!predicates.isEmpty()) {
				Predicate andClause = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(andClause);
			}

			List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();
			for (Tuple t : tupleResult) {
				parent.getChildren().add(new Node((String) t.get(0)));
			}
		} catch (Exception e) {
			LOGGER.error("<search> :", e);
		}
	}

	private void searchExtRelation(SearchCriteria searchCriteria, Node parent) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Log> sm = query.from(Log.class);
			query.multiselect(sm.get(Log_.phoneNumber));
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.hasText(searchCriteria.getPhoneNumber())) {
				predicates.add(cb.equal(sm.get(Log_.extPhoneNumber), parent.getPhoneNumber()));
			}
			if (searchCriteria.getDateFrom() != null) {
				predicates.add(cb.greaterThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateFrom().getTime())));
			}
			if (searchCriteria.getDateTo() != null) {
				predicates.add(cb.lessThanOrEqualTo(sm.get(Log_.dateTime),
						new Timestamp(searchCriteria.getDateTo().getTime())));
			}

			if (!predicates.isEmpty()) {
				Predicate andClause = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(andClause);
			}

			List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();
			for (Tuple t : tupleResult) {
				parent.getChildren().add(new Node((String) t.get(0)));
			}
		} catch (Exception e) {
			LOGGER.error("<search> :", e);
		}
	}

	private List<LogDetails> convertLog(List<Log> logs) {
		List<LogDetails> logDetails = new ArrayList<LogDetails>();
		for (Log log : logs) {
			LogDetails logDetail = new LogDetails();
			logDetail.setId(log.getId());
			logDetail.setDateTime(new Date(log.getDateTime().getTime()));
			logDetail.setExternalPhoneNumber(log.getExtPhoneNumber());
			logDetail.setPhoneNumber(log.getPhoneNumber());
			logDetail.setDuration(Utils.timeToString(log.getDuration()));
			logDetail.setSmsContent(log.getSmsContent());
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
			logDetail.setMnc(log.getMnc());
			logDetail.setMcc(log.getMcc());
			logDetail.setRadius(log.getRadius());
			logDetails.add(logDetail);
		}
		return logDetails;
	}

	/** Checks if a username-password pair exists in the database */
	public boolean login(String userName, String password) {
		String query = "select count(u) from Users u where u.userName = :userName"
				+ " and u.password = PASSWORD(:userPassword)";
		Query jpqlQuery = entityManager.createQuery(query).setParameter("userName", userName)
				.setParameter("userPassword", password);
		long count = (long) jpqlQuery.getSingleResult();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
