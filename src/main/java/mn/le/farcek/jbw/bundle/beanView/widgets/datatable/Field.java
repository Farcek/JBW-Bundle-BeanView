/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView.widgets.datatable;

import mn.le.farcek.common.entity.criteria.FCriteriaBuilder;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.bundle.beanView.widgets.BVDataTable;

/**
 *
 * @author Farcek
 */
public interface Field {

    public BVDataTable getDataTable();

    public String getName();

    public String getTitle();

    public boolean isFilter();

    public boolean isSortable();
    public String getSort();

    public String getFilterValue();

    public void loadParam(IActionRequest request);

    public void bindFilter(FCriteriaBuilder<?> builder);
    public void bindSort(FCriteriaBuilder<?> builder);

}
