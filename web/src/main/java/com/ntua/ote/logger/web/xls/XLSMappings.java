package com.ntua.ote.logger.web.xls;

/**
 * A mapping classes that holds the mapping between a variable name and the bundle code
 */
public class XLSMappings {
	
	/** The Constant backendNotifHeader. */
	public static final String logDetailsHeader = "log.details.title";

	/** The Constant backendNotifMapping. */
	public static final XLSColumn[] logDetailsMapping = {
			new XLSColumn("log.details.phoneNumber", "phoneNumber"),
			new XLSColumn("log.details.externalPhoneNumber", "externalPhoneNumber"),
			new XLSColumn("log.details.dateTime", "dateTime"),
			new XLSColumn("log.details.logType", "logType"),
			new XLSColumn("log.details.duration", "duration"),
			new XLSColumn("log.details.smsContent", "smsContent"),
			new XLSColumn("log.details.latitude", "latitude"),
			new XLSColumn("log.details.longitude", "longitude"),
			new XLSColumn("log.details.brandModel", "brandModel"),
			new XLSColumn("log.details.version", "version"),
			new XLSColumn("log.details.imei", "imei"),
			new XLSColumn("log.details.imsi", "imsi"),
			new XLSColumn("log.details.direction", "direction"),
			new XLSColumn("log.details.cellId", "cellId"),
			new XLSColumn("log.details.lac", "lac"),
			new XLSColumn("log.details.rat", "rat"),
			new XLSColumn("log.details.mnc", "mnc"),
			new XLSColumn("log.details.mcc", "mcc"),
			new XLSColumn("log.details.rssi", "rssi"),
			new XLSColumn("log.details.lteRSRP", "lteRSRP"),
			new XLSColumn("log.details.lteRSRQ", "lteRSRQ"),
			new XLSColumn("log.details.lteRSSNR", "lteRSSNR"),
			new XLSColumn("log.details.lteCQI", "lteCQI")};

}
