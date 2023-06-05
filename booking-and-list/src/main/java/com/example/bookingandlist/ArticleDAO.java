package com.example.bookingandlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.bookingandlist.HomeController.*;

@Service
public class ArticleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ArticleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void put(ArticleItem articleItem) {
        SqlParameterSource pram = new BeanPropertySqlParameterSource(articleItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("articles");
        insert.execute(pram);
    }

    public List<ArticleItem> collectAll() {
        String query = "select * from articles ORDER BY date desc";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<ArticleItem> articleItems = result.stream()
                .map((Map<String, Object> row) -> new ArticleItem(
                        row.get("id").toString(),
                        row.get("date").toString(),
                        row.get("title").toString(),
                        row.get("content").toString(),
                        row.get("url") != null ? row.get("url").toString() : null))
                .toList();


        return articleItems;

    }
}