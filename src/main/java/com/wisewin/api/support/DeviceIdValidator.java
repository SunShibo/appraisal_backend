package com.wisewin.api.support;

import com.wisewin.api.common.constants.SysConstants;
import org.apache.commons.lang.StringUtils;


/**
 * 设备唯一标识码较验器
 * @author Felix Lee
 * @Date 2014-1-6
 */
public final class DeviceIdValidator {
	
	/**
	 * 判断指定的IMEI号是否为合法的IMEI
	 * @param imei IMEI号
	 * @return 是否为合法的IMEI
	 */
	public static boolean isValidImei(String imei) {
		if (StringUtils.isNotBlank(imei)) {
    		if (StringUtils.isNumeric(imei)) {
    			//纯数字，15位为合法IMEI号码
    			if (imei.length() == SysConstants.VALID_IMEI_LENGTH) {
    				return true;
    			}
    		}
		}
		return false;
	}
	/**
	 * 判断指定的MEID号是否为合法的MEID
	 * @param meid MEID号
	 * @return 是否为合法的MEID
	 */
	public static boolean isValidMeid(String meid) {
		if (StringUtils.isNotBlank(meid)) {
			//非纯数字，14位为合法MEID号码
			if (meid.length() == SysConstants.VALID_MEID_LENGTH) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断指定的uuid号是否为合法的UUID
	 * @param uuid uuid号码
	 * @return 是否为合法的UUID
	 */
	public static boolean isValidUUID(String uuid) {
		return StringUtils.isNotBlank(uuid) && uuid.length() == SysConstants.VALID_UUID_LENGTH;
	}
	/**
	 * 判断指定的设备号是否为合法的设备号（IMEI或MEID或UUID）
	 * @param deviceId 设备号
	 * @return 是否为合法的设备号（IMEI或MEID或UUID）
	 */
	public static boolean isValidId(String deviceId) {
		return isValidImei(deviceId) || isValidMeid(deviceId) || isValidUUID(deviceId);
	}
	
}
