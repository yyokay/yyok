package com.yyok.acquisite.datax.client.service;


import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.client.entity.JobTemplate;

import java.util.Map;

public interface IJobTemplateService {

	/**
	 * page list
	 *
	 * @param start
	 * @param length
	 * @param jobGroup
	 * @param jobDesc
	 * @param executorHandler
	 * @param userId
	 * @return
	 */
	Map<String, Object> pageList(int start, int length, int jobGroup, String jobDesc, String executorHandler, int userId,Integer[] projectIds);
	/**
	 * add job
	 *
	 * @param jobTemplate
	 * @return
	 */
	ReturnT<String> add(JobTemplate jobTemplate);

	/**
	 * update job
	 *
	 * @param jobTemplate
	 * @return
	 */
	ReturnT<String> update(JobTemplate jobTemplate);

	/**
	 * remove job
	 * 	 *
	 * @param id
	 * @return
	 */
	ReturnT<String> remove(int id);
}
