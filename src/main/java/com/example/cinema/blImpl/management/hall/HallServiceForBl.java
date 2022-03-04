package com.example.cinema.blImpl.management.hall;

import com.example.cinema.po.Hall;
/**
 * Created by HJZ on 2021/10/20.
 */
public interface HallServiceForBl {
    /**
     * 搜索影厅
     * @param id
     * @return
     */
    Hall getHallById(int id);
}
