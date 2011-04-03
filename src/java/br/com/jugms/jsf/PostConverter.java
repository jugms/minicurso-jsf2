/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jugms.jsf;

import br.com.jugms.entities.Post;
import br.com.jugms.model.BlogManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Joao
 */
@Named
public class PostConverter implements Converter {

	@EJB
    private BlogManager blogManager;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {             
        Object o = blogManager.findById(Long.valueOf(value));
        blogManager.destroy();
        return o;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Post post = (Post) value;
        if(post.getId() == null) return "";
        return post.getId().toString();
    }
}
