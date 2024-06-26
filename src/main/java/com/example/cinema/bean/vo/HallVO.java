package com.example.cinema.bean.vo;

import com.example.cinema.dao.po.Hall;

public class HallVO {
    private Integer id;
    private String name;
    private Integer row;
    private Integer column;

    public HallVO(Hall hall){
        this.id = hall.getId();
        this.name = hall.getName();
        this.row = hall.getRow();
        this.column = hall.getColumn();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
