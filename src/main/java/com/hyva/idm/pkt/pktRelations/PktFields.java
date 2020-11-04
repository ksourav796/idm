package com.hyva.idm.pkt.pktRelations;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * krishna
 */
@Entity
@Data
public class PktFields {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String tableName;
    private String fieldName;
    private String groupOf;
    private String status;
}
