package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    protected Long id;

    @Column(name = "created_at")
    protected Timestamp createdAt;

    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
