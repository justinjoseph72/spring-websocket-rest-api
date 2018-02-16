package com.yoti.app.websocket.repos;

import com.yoti.app.websocket.domain.KeyTable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KeyRepository extends CrudRepository<KeyTable, Long> {

    Optional<KeyTable> findBySerialId(String serialId);

    KeyTable save(KeyTable data);
}
