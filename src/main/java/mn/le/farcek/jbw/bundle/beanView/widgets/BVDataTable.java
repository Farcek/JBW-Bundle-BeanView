/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView.widgets;

import java.util.List;
import mn.le.farcek.common.entity.FEntity;
import mn.le.farcek.common.entity.criteria.FCriteriaBuilder;
import mn.le.farcek.common.entity.ejb.FListResult;
import mn.le.farcek.common.utils.FStringUtils;
import mn.le.farcek.jbw.api.IService;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.api.utils.Pagination;
import mn.le.farcek.jbw.bundle.beanView.widgets.datatable.Field;

/**
 *
 * @author Farcek
 */
public class BVDataTable {

    private String id;
    private String name;
    private List<Field> fields;
    private Field pkField;
    private List list;
    private int page;
    private int totalCount;

    private int prePageCount = 20;

    private String newUrl;
    private String editUrl;
    private String deleteUrl;

    public BVDataTable() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getPkField() {
        return pkField;
    }

    public void setPkField(Field pkField) {
        this.pkField = pkField;
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public int getTotalColumns() {
        return fields == null ? 0 : fields.size() + 1;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<BVAction> getActions() {
        return null;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPrePageCount() {
        return prePageCount;
    }

    public void setPrePageCount(int prePageCount) {
        this.prePageCount = prePageCount;
    }

    private Pagination pagination;

    public Pagination getPagination() {
        if (pagination == null)
            pagination = new Pagination(getTotalCount(), getPrePageCount(), getPage(), 2, null);
        return pagination;
    }

    public void loadParameters(IActionRequest actionRequest) {
        for (Field f : getFields())
            f.loadParam(actionRequest);

        String n = String.format("p_%s", getId());
        String pString = actionRequest.getParameter(n);
        if (FStringUtils.isEmpty(pString)) {
            n = String.format("cp_%s", getId());
            pString = actionRequest.getParameter(n);
        }

        try {
            setPage(Integer.parseInt(pString));
        } catch (Exception e) {
            setPage(1);
        }

    }

    public void bind(IService service, FCriteriaBuilder<? extends FEntity> builder) {
        builder.setLimit(getPrePageCount());
        builder.setOffset((getPage() - 1) * getPrePageCount());

        for (Field f : getFields()) {
            f.bindFilter(builder);
            f.bindSort(builder);
        }

        FListResult<?> result = service.entitysBy(builder);

        setList(result.getList());

        setTotalCount((int) result.getTotalCount());
    }

}
