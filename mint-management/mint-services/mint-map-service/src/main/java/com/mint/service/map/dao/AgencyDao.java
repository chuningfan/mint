package com.mint.service.map.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mint.service.map.dto.AgencyInfo;

@Repository
public class AgencyDao {

	@Autowired
	private SolrTemplate solrTemplate;
	
	public void saveAgencyInfo(String collectionName, AgencyInfo info) throws SolrServerException, IOException {
		SolrClient sc = solrTemplate.getSolrClient();
		Collection<SolrInputDocument> docs = Lists.newArrayList();  
		SolrInputDocument doc = new SolrInputDocument();   
		doc.addField("positionInfo",info.getLatitude()+","+info.getLongitude());
		doc.addField("agencyId", info.getAgencyId());
		doc.addField("keys", info.getKeys());
		docs.add(doc);
		sc.add(docs);
		sc.commit();
	}
	
	public void updateAgencyInfo(String collectionName, AgencyInfo info) throws SolrServerException, IOException {
		SolrClient sc = solrTemplate.getSolrClient();
		Map<String, String> map = Maps.newHashMap();
		map.put("agencyId", info.getAgencyId().toString());
		SolrParams sp = new MapSolrParams(map);
		QueryResponse qr = sc.query(sp);
		Collection<SolrInputDocument> docs = Lists.newArrayList();  
		SolrInputDocument doc = new SolrInputDocument();   
		doc.addField("positionInfo",info.getLatitude()+","+info.getLongitude());
		doc.addField("agencyId", info.getAgencyId());
		doc.addField("keys", info.getKeys());
		docs.add(doc);   
		sc.add(docs);
		sc.commit();
	}
	
	public List<Long> findAgenciesByPostion(String collectionName, Set<String> keys, double longitude, double latitude) throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.addFilterQuery("{!geofilt}");
		query.set("sfield","agencyInfo");
		QueryResponse resp = solrTemplate.getSolrClient().query(query);
		if (resp != null) {
			List<AgencyInfo> infoList = resp.getBeans(AgencyInfo.class);
			if (CollectionUtils.isNotEmpty(infoList)) {
				return infoList.stream().map(AgencyInfo::getAgencyId).collect(Collectors.toList());
			}
		}
		return null;
	}
	
	public void deleteDataByAgencyId(String collectionName, Long id) {
		SolrDataQuery q = new SimpleFilterQuery();
		Criteria c = new Criteria("agencyId");
		c.equals(id);
		q.addCriteria(c);
		solrTemplate.delete(collectionName, q);
	}
	
}
