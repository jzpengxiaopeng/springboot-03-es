package com.atguigu.es.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.es.bean.Article;
import com.atguigu.es.bean.Book;
import com.atguigu.es.resposity.BookResposity;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {

	@Autowired
	JestClient jestClient;
	
	@Autowired
	BookResposity bookResposity;
	
	@Test
	public void testIndexByJest() {
		Article article = new Article();
		article.setId(1);
		article.setTitle("好消息");
		article.setAuthor("zhangsan");
		article.setContent("Hello World");
		
		Index index = new Index.Builder(article).index("atguigu").type("news").build();
		try {
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchByJest() {
		String json ="{\n" +
				"    \"query\" : {\n" +
				"        \"match\" : {\n" +
				"            \"content\" : \"hello\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		
		Search query = new Search.Builder(json).addIndex("atguigu").addType("news").build();
		try {
			SearchResult result = jestClient.execute(query);
			System.out.println(result.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testIndexBySpringDataES() {
		bookResposity.save(new Book(1, "西游记", "吴承恩"));
		bookResposity.save(new Book(2, "东游记", "彭承恩"));
		bookResposity.save(new Book(3, "水浒传", "施耐庵"));
	}
	
	@Test
	public void testSearchBySpringDataES() {
		bookResposity.findByBookNameLike("游记").stream().forEach((b) -> System.out.println(b));
	}
}
