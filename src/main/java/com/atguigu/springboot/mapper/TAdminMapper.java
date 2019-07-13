package com.atguigu.springboot.mapper;


import java.util.List;

import com.atguigu.springboot.bean.TAdmin;
import org.apache.ibatis.annotations.Param;
//查询所有
public interface TAdminMapper {
    List<TAdmin> selectTAdmin();

    void deleteById(Integer id);

    void addAdmin(TAdmin admin);


    TAdmin selectTAdminById(Integer id);

    void updateTadmin(TAdmin admin);
}
