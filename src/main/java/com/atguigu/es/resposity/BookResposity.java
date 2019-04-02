package com.atguigu.es.resposity;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.atguigu.es.bean.Book;

public interface BookResposity extends ElasticsearchCrudRepository<Book, Integer> {

	List<Book> findByBookNameLike(String bookName);
	
}
