package com.foya.model;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FY_TB_LOOKUP_TYPE")
@Data
public class LookupType implements Serializable {
    @Column(name="lookup_Type")
    @Id
    String lookupType;

    String dscr;

}
