package com.yoti.app.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "KEY_TABLE", schema = "public")
public class KeyTable implements Serializable {
    private static final long serialVersionUID = -0234324L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KEY_SEQ_GEN")
    @SequenceGenerator(name = "KEY_SEQ_GEN", sequenceName = "key_seq", schema = "public")
    private Long id;
    private String serialId;
    public byte[] publicKey;
}
