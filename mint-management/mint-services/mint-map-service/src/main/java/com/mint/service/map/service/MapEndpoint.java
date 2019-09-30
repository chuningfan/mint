package com.mint.service.map.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.mint.service.map.dto.AgencyInfo;
import com.mint.service.map.dto.QueryDto;

@RestController
@RequestMapping("/map")
public class MapEndpoint implements MapService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	@PostMapping("/saveAgencyData")
	public void saveAgencyData(@RequestBody AgencyInfo ai) {
		Point point = null;
		for (String key: ai.getKeys()) {
			point = new Point(ai.getLongitude(), ai.getLatitude());
			redisTemplate.opsForGeo().add(key, point, ai.getAgencyId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@PostMapping("/removeAgencyInfo")
	public void removeAgencyInfo(@RequestBody AgencyInfo ai) {
		for (String key: ai.getKeys()) {
			redisTemplate.opsForGeo().remove(key, ai.getAgencyId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@PostMapping("/updateAgencyInfo")
	public void updateAgencyInfo(@RequestBody AgencyInfo ai) {
		for (String key: ai.getKeys()) {
			redisTemplate.opsForGeo().remove(key, ai.getAgencyId());
			redisTemplate.opsForGeo().add(key, new Point(ai.getLongitude(), ai.getLatitude()), ai.getAgencyId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@PostMapping("/query")
	public List<Long> query(@RequestBody QueryDto queryDto) throws Exception {
		List<Long> result = Lists.newArrayList();
		GeoResults<GeoLocation<Long>> geoResults = null;
		for (String key: queryDto.getKeys()) {
			geoResults = redisTemplate.opsForGeo()
					.radius(key, 
							new Circle(
									new Point(
											queryDto.getLongitude(), 
											queryDto.getLatitude()), 
									new Distance(
											queryDto.getRadius(), 
											DistanceUnit.KILOMETERS)), 
							RedisGeoCommands
							.GeoRadiusCommandArgs
							.newGeoRadiusArgs()
							.sortAscending());
			if (geoResults != null) {
				Iterator<GeoResult<GeoLocation<Long>>> itr = geoResults.iterator();
				GeoResult<GeoLocation<Long>> gr = null;
				while (itr.hasNext()) {
					gr = itr.next();
					result.add(gr.getContent().getName());
				}
			}
		}
		return result;
	}

}
