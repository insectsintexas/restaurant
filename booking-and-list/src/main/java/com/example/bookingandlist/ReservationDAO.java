package com.example.bookingandlist;

import com.example.bookingandlist.HomeController.FormItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(FormItem formItem) {
        SqlParameterSource pram = new BeanPropertySqlParameterSource(formItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservations");
        insert.execute(pram);
    }

    public List<FormItem> findAll() {
        String query = "select * from reservations ORDER BY date, time";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<FormItem> formItems = result.stream()
                .map((Map<String, Object> row) -> new FormItem(
                        row.get("id").toString(),
                        row.get("name").toString(),
                        row.get("number").toString(),
                        row.get("email").toString(),
                        row.get("date").toString(),
                        row.get("time").toString(),
                        Integer.parseInt(row.get("people").toString()),
                        row.get("message").toString()))
                .toList();

        return formItems;
    }

    public int delete(String id) {
        int idNumber = jdbcTemplate.update("delete from reservations where id = ?", id);
        return idNumber;
    }

}
