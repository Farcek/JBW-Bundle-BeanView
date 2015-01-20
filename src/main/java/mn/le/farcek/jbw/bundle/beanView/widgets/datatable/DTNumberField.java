/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView.widgets.datatable;

import mn.le.farcek.common.entity.criteria.BinaryOperator;
import mn.le.farcek.common.entity.criteria.FCriteriaBuilder;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.common.utils.FStringUtils;
import mn.le.farcek.jbw.bundle.beanView.widgets.BVDataTable;

/**
 *
 * @author Farcek
 */
public class DTNumberField extends DTAbstractField {

    public DTNumberField(BVDataTable dataTable) {
        super(dataTable);
    }

    @Override
    public void bindFilter(FCriteriaBuilder<?> builder) {
        String v = getFilterValue();
        if (isFilter() && FStringUtils.notEmpty(v)) {
            Integer intv = FClassUtils.fromString(Integer.class, v);
            builder.add(new BinaryOperator(getName(), intv));
        }
    }

}
