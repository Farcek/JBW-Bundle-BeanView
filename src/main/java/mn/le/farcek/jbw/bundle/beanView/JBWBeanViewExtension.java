/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView;

import com.google.inject.Inject;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.LocaleAware;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mn.le.farcek.common.utils.FCollectionUtils;
import mn.le.farcek.jbw.api.managers.ITemplateManager;
import mn.le.farcek.jbw.api.template.ITemplate;
import mn.le.farcek.jbw.bundle.beanView.widgets.BVDataTable;

/**
 *
 * @author Farcek
 */
public class JBWBeanViewExtension extends AbstractExtension {

    @Override
    public Map<String, Filter> getFilters() {
        return new FCollectionUtils.HashMapBuilder<String, Filter>()
                .put("beanView", new BeanViewFilter())
                .build();
    }

    @Inject
    ITemplateManager templateManager;

    class BeanViewFilter implements Filter, LocaleAware {

        Locale locale;

        @Override
        public Object apply(Object input, Map<String, Object> args) {
            if (input instanceof BVDataTable) {
                ITemplate factoryRenderer = templateManager.factoryRenderer("bundle://beanView/dataTable");
                StringWriter sw = new StringWriter();
                Map<String, Object> params = new HashMap<>();
                params.put("tbl", input);
                try {
                    factoryRenderer.render(sw, params, locale);
                    return sw.toString();
                } catch (IOException ex) {
                    return ex.getMessage();

                }
            }

            return input;
        }

        @Override
        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        @Override
        public List<String> getArgumentNames() {
            return null;
        }

    }

}
