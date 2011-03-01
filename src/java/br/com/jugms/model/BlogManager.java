package br.com.jugms.model;

import javax.persistence.Query;
import br.com.jugms.entities.Post;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import static javax.persistence.PersistenceContextType.EXTENDED;

/**
 *
 * @author Joao
 */
@Named
@Stateful
@ViewAccessScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BlogManager {

    private Post post;
    private List<Post> posts;
    private String authorSearch;
    private String titleSearch;
    private String searchParams;
    @PersistenceContext(type = EXTENDED)
    private EntityManager em;

    @PostConstruct
    public void init() {
        System.out.println("init " + this);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String savePost() {
        System.out.println("save " + this);
        em.merge(post);
        listAll();
        post = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("blogged!"));
        return "list";
    }

    public void listAll() {
        posts = em.createQuery("select p from Post p").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removePost(Post post) {
        System.out.println("remove " + this);
        //Com view scoped e PersistenceContext Extended funciona como o esperado
        em.remove(post);
        listAll();
    }

    @Remove
    public void destroy() {
        System.out.println("remove ejb " + this);
    }

    public Object findById(Long id) {
        return em.find(Post.class, id);
    }

    public void search() {
        Query query = em.createQuery("select p from Post p where p.author like :author and p.title like :title");
        query.setParameter("author", authorSearch + "%");
        query.setParameter("title", titleSearch + "%");
        posts = query.getResultList();
        searchParams = "author = " + authorSearch + " title = " + titleSearch;
    }

    public Post getPost() {
        if (post == null) {
            System.out.println("create post " + this);
            post = new Post();
        }
        return post;
    }

    public void setPost(Post post) {
        System.out.println("setPost " + this);
        this.post = post;
    }

    public List<Post> getPosts() {
        if (posts == null) {
            System.out.println("load posts " + this);
            listAll();
        }
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getAuthorSearch() {
        return authorSearch;
    }

    public void setAuthorSearch(String authorSearch) {
        this.authorSearch = authorSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(String searchParams) {
        this.searchParams = searchParams;
    }
}
