package com.tusk.priest.data;

import com.tusk.priest.util.HttpServletUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 分页排序数据
 * @author alvin
 * @date 2018/12/8
 */
public class PageSort {

    private static final Integer pageSizeDef = 15;
    private static final String orderByColumnDef = "createDate";
    private static final Sort.Direction sortDirection = Sort.Direction.DESC;

    /**
     * 创建分页排序对象
     */
    public static PageRequest pageRequest(){
        return pageRequest(pageSizeDef, orderByColumnDef, sortDirection);
    }

    /**
     * 创建分页排序对象
     * @param sortDirection 排序方式默认值
     */
    public static PageRequest pageRequest(Sort.Direction sortDirection){
        return pageRequest(pageSizeDef, orderByColumnDef, sortDirection);
    }

    /**
     * 创建分页排序对象
     * @param orderByColumnDef 排序字段名称默认值
     * @param sortDirection 排序方式默认值
     */
    public static PageRequest pageRequest(String orderByColumnDef, Sort.Direction sortDirection){
        return pageRequest(pageSizeDef, orderByColumnDef, sortDirection);
    }

    public static PageRequest pageRequest4Post(Integer pageIndex, Integer limit, String orderByColumnDef, Sort.Direction sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection.toString()), orderByColumnDef);
        return PageRequest.of(pageIndex-1, limit, sort);
    }


    /**
     * 创建分页排序对象
     * @param pageSizeDef 分页数据数量默认值
     * @param orderByColumnDef 排序字段名称默认值
     * @param sortDirection 排序方式默认值
     */
    public static PageRequest pageRequest(Integer pageSizeDef, String orderByColumnDef, Sort.Direction sortDirection){
        Integer pageIndex = HttpServletUtil.getParameterInt("page", 1);
        Integer pageSize = HttpServletUtil.getParameterInt("size", pageSizeDef);
        String orderByColumn = HttpServletUtil.getParameter("orderByColumn", orderByColumnDef);
        String direction = HttpServletUtil.getParameter("isAsc", sortDirection.toString());
        Sort sort = Sort.by(Sort.Direction.fromString(direction), orderByColumn);
        return PageRequest.of(pageIndex-1, pageSize, sort);
    }



}
