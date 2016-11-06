package com.vrspring.service;

import com.vrspring.dao.TestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testService")
public class TestService
{
	@Autowired
	private TestDAO testDAO;
	public void queryId()
	{
		String queryId= testDAO.queryId();
		System.err.println(queryId);
	}
}
