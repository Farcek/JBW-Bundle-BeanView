/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView.widgets.datatable;

import mn.le.farcek.common.entity.criteria.FCriteriaBuilder;
import mn.le.farcek.common.entity.criteria.OrderByItem;
import mn.le.farcek.common.entity.criteria.OrderByType;
import mn.le.farcek.common.utils.FStringUtils;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.bundle.beanView.widgets.BVDataTable;

/**
 *
 * @author Farcek
 */
public abstract class DTAbstractField implements Field {

    private final BVDataTable dataTable;

    public DTAbstractField(BVDataTable dataTable) {
        this.dataTable = dataTable;
    }

    private String name;
    private String title;

    private boolean filter;
    private boolean sortable;
    private String filterValue;
    private String sort;

    @Override
    public final BVDataTable getDataTable() {
        return dataTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String nextSort() {
        if (FStringUtils.isEmpty(sort))
            return "asc";
        if ("asc".equals(sort))
            return "desc";

        return "";
    }

    public String getTitle() {
        if (title == null)
            title = name;
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    @Override
    public void loadParam(IActionRequest request) {
        if (isFilter()) {
            String n = String.format("filter_%s_%s", getDataTable().getId(), getName());
            String v = request.getParameter(n);
            if (FStringUtils.notEmpty(v))
                setFilterValue(v);
        }

        if (isSortable()) {
            String n = String.format("sort_%s_%s", getDataTable().getId(), getName());
            String v = request.getParameter(n);
            if (FStringUtils.notEmpty(v))
                setSort(v);
        }

    }

    @Override
    public void bindSort(FCriteriaBuilder<?> builder) {
        String v = getSort();
        if (isSortable() && FStringUtils.notEmpty(v)) {
            OrderByType type = null;
            if ("desc".equals(v)) type = OrderByType.DESC;
            if ("asc".equals(v)) type = OrderByType.ASC;
            builder.add(new OrderByItem(getName(), type));
        }
    }

}
