/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView;

import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.bean.BeanPropertyFactory;
import mn.le.farcek.common.bean.NoSuchPropertyException;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.bundle.beanView.widgets.BVDataTable;
import mn.le.farcek.jbw.bundle.beanView.widgets.datatable.DTNumberField;
import mn.le.farcek.jbw.bundle.beanView.widgets.datatable.DTStringField;
import mn.le.farcek.jbw.bundle.beanView.widgets.datatable.Field;

/**
 *
 * @author Farcek
 */
@Singleton
public class BeanViewFactory {

    public BVDataTable factoryTable(Class<?> cls) {
        return factoryTable(cls, "default");
    }

    public BVDataTable factoryTable(Class<?> cls, String name) {
        if (cls == null) throw new NullPointerException();

        DataTable annDt = getDataTable(cls, name);
        if (annDt == null) return null;

        BVDataTable dt = new BVDataTable();
        dt.setName(name);

        Field f;

        try {
            f = factoryField(dt, cls, annDt.pk());
        } catch (NoSuchPropertyException ex) {
            return null;
        }

        dt.setPkField(f);

        List<Field> fields = new ArrayList<>();
        fields.add(f);
        for (String p : annDt.fields())
            try {
                f = factoryField(dt, cls, p);
                fields.add(f);
            } catch (NoSuchPropertyException ex) {

            }

        dt.setFields(fields);

        return dt;
    }

    private DataTable getDataTable(Class<?> cls, String name) {
        DataTable annotation = cls.getAnnotation(DataTable.class);
        if (annotation != null && annotation.name().equals(name))
            return annotation;
        DataTables anns = cls.getAnnotation(DataTables.class);
        if (anns != null)
            for (DataTable dr : anns.value())
                if (dr.name().equals(name)) return dr;
        return null;
    }

    private Field factoryField(BVDataTable dt, Class<?> cls, String name) throws NoSuchPropertyException {
        BeanProperty bp = BeanPropertyFactory.factoryProperty(cls, name);

        Class<?> type = bp.getType();

        if (FClassUtils.isNumberClass(type)) {
            DTNumberField f = new DTNumberField(dt);
            f.setName(name);
            f.setTitle(cls.getName() + "." + name);
            f.setFilter(true);
            f.setSortable(true);
            return f;
        }

        DTStringField f = new DTStringField(dt);
        f.setName(name);
        f.setTitle(cls.getName() + "." + name);
        f.setFilter(true);
        f.setSortable(true);
        return f;
    }

}
