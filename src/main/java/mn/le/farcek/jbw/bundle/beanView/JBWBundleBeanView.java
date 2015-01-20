/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanView;

import java.util.List;
import mn.le.farcek.jbw.api.Bundle;
import mn.le.farcek.jbw.api.bundle.BundlePebbleExtension;
import mn.le.farcek.jbw.api.bundle.JBWBundle;

/**
 *
 * @author Farcek
 */
@Bundle(name = "beanView")
@BundlePebbleExtension(JBWBeanViewExtension.class)
public class JBWBundleBeanView extends JBWBundle {

    @Override
    protected void setupControllers(List<Class<?>> controllers) {

    }

}
