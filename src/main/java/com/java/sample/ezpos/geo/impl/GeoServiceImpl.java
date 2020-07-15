package com.java.sample.ezpos.geo.impl;

import java.io.File;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.java.sample.ezpos.config.GlobalConfiguration;
import com.java.sample.ezpos.geo.GeoService;
import com.java.sample.ezpos.geo.model.GeoIP;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

import lombok.extern.log4j.Log4j2;

@Component
@PropertySource("classpath:application.properties")
@Log4j2
public class GeoServiceImpl implements GeoService {
	@Value("${location.db}")
	private String locationDb;

	DatabaseReader dbReader = null;

	@Override
	public String getLocationInfo(String ip) throws Exception {
		log.info("GlobalConfiguration.geoFileLoaded :: " + GlobalConfiguration.geoFileLoaded);

		if (!GlobalConfiguration.geoFileLoaded) {

			log.info("load the Geo File");

			File database = loadGeo();
			dbReader = new DatabaseReader.Builder(database).build();

			GlobalConfiguration.geoFileLoaded = true;

		}

		InetAddress ipAddress = InetAddress.getByName(ip);
		CityResponse response = dbReader.city(ipAddress);

		GeoIP geoIp = new GeoIP();

		geoIp.setCountryCode(response.getCountry().getIsoCode());
		geoIp.setCountryName(response.getCountry().getName());
		geoIp.setRegion(response.getMostSpecificSubdivision().getIsoCode());
		geoIp.setRegionName(response.getMostSpecificSubdivision().getName());
		geoIp.setCity(response.getCity().getName());
		geoIp.setPostalCode(response.getPostal().getCode());
		geoIp.setLatitude(response.getLocation().getLatitude().toString());
		geoIp.setLongitude(response.getLocation().getLongitude().toString());

		log.info(geoIp.toString());

		return geoIp.toString();
	}

	@Override
	public File loadGeo() throws Exception {
		log.info("in loadGeo");
		File file = new File(locationDb);
		log.info("file " + file);
		log.info("getAbsolutePath " + file.getAbsolutePath());
		log.info("getCanonicalPath " + file.getCanonicalPath());
		return file;
	}

	@Override
	public String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}

		return xfHeader.split(",")[0];
	}

}
