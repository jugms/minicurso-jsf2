/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jugms.jsf;

import java.lang.reflect.Type;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Bean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Joao
 */
public class JsfUtil {

    public static BeanManager getBeanManager() {
        return (BeanManager) ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute("javax.enterprise.inject.spi.BeanManager");

    }

    public static Object getBeanObject(Type classe) {
        BeanManager bm = getBeanManager();
        Bean bean = bm.getBeans(classe).iterator().next();
        CreationalContext ctx = bm.createCreationalContext(bean);
        return bm.getReference(bean, bean.getClass(), ctx);
    }
}
