package com.duoduo.crawler.service;

import org.springframework.stereotype.Service;

@Service
public interface IService<T> {
	int save(T entity);
}
