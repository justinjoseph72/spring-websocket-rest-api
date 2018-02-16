package com.yoti.app.websocket.repos;

import com.yoti.app.websocket.domain.KeyTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class SavingRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveItem(KeyTable keyTable) {

        Integer nextVal = jdbcTemplate.queryForObject("select nextval('KEY_SEQ')",Integer.class);
        //inseting the record to the database
        String sql = "insert into public.\"KEY_TABLE\" (id, serial_id,public_key) values (?, ?,?)";
        Object[] params = new Object[] {nextVal,keyTable.getSerialId(),keyTable.getPublicKey()};
        int[] types = new int[] {Types.BIGINT,Types.VARCHAR, Types.BINARY};
        jdbcTemplate.update(sql,params,types);
    }

}
