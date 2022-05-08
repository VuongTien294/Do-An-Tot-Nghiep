package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "intro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intro extends BaseModel{
    private String subject;

    @Lob
    @Column(name = "content",columnDefinition = "TEXT",length = 65555)
    private String content;

    private String video;
}
