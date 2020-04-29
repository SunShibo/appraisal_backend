package com.wisewin.api.util.env;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 孙诗博
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class ResourceUtil {
	
	private static PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
	
	public static Resource getResource(String locationPattern) {
		try {
			Resource[] resources = resourceLoader.getResources(locationPattern);
			return (resources != null && resources.length > 0) ? resources[0] : null;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static Resource[] getResources(String locationPattern) {
		return getResources(locationPattern, false);
	}

	public static Resource[] getResources(String locationPattern, boolean ignoreFileNotFound) {
		try {
			return resourceLoader.getResources(locationPattern);
		} catch (FileNotFoundException e) {
			if (ignoreFileNotFound) {
				return null;
			}
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static Resource[] getResources(String[] locationPatterns) {
		return getResources(locationPatterns, false);
	}

	public static Resource[] getResources(String[] locationPatterns, boolean ignoreFileNotFound) {
		List<Resource> resourceList = new ArrayList<Resource>();
		for (String locationPattern : locationPatterns) {
			Resource[] resources = getResources(locationPattern, ignoreFileNotFound);
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					resourceList.add(resource);
				}
			}
		}
		return resourceList.toArray(new Resource[] {});
	}
}