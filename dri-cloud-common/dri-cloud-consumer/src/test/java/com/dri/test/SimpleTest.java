package com.dri.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dri.entity.Account;
import com.dri.mapper.AccountMapper;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Test
	public void select() {
		List<Account> list = accountMapper.selectList(null);
		System.out.println(JSONObject.toJSON(list));
	}
	@Test
	public void insert() {
		Account acc=new Account();
		acc.setUserId("200");
		acc.setMoney(5000);
		int result = accountMapper.insert(acc);
		System.out.println(result);
	}
	
	@Test
	public void insertBatch() {
		Account acc=new Account();
		for(int i=500;i<520;i++) {
			acc.setUserId(i+"");
			acc.setMoney(100);
			int result = accountMapper.insert(acc);
			System.out.println(result);
		}
	}
	
	@Test
	public void update() {
		Account acc=new Account();
		acc.setId(2);;
		acc.setMoney(9999);
	    accountMapper.updateById(acc);
	}
	
	@Test
	public void delete() {
	    accountMapper.deleteById(3);
	}
	@Test
	public void selectPage() {
		IPage<Account> selectPage = accountMapper.selectPage(new Page<>(5, 5), null);
		System.out.println(JSONObject.toJSON(selectPage));
	}

}
