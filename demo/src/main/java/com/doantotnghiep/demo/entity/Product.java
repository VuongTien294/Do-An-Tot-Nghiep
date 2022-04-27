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
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel{

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "sold_quantity")
    private Integer soldQuantity;

//    @Lob
    @Column(name = "description",columnDefinition = "TEXT",length = 65555)
    private String description; //mo ta dai

//    @Lob
    @Column(name = "manufacturer",columnDefinition = "TEXT",length = 65555)
    private String manufacturer; //mo ta ngan

    @Column(name = "total_rating")
    private Integer totalRating; // = total review

    @Column(name = "total_star")
    private Long totalStar;

    @Column(name = "image")
    private String image;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "style")
    private Integer style;

    @Column(name = "color")
    private Integer color;

    @Column(name = "material")
    private Integer material;

    @Column(name = "technology")
    private Integer technology;
}
