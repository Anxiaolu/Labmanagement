package cn.edu.sdut.softlab.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import cn.edu.sdut.softlab.model.Category;
import cn.edu.sdut.softlab.service.CategoryFacade;

@Named("categoryManager")
@RequestScoped
public class CategoryManager {

	@Inject
	private transient Logger logger;

	@Inject
	private CategoryFacade categoryService;

	@Inject
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	public List<String> getAllCategoryName() throws Exception {
		List<String> namelist = new ArrayList<>();
		List<Category> categorylist = getAllCategory();
		for (Category c : categorylist) {
			namelist.add(c.getName());
		}
		return namelist;
	}

	private Category newcategory = new Category();

	public Category getNewcategory() {
		return newcategory;
	}

	public void setNewcategory(Category newcategory) {
		this.newcategory = newcategory;
	}

	public CategoryFacade getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryFacade categoryService) {
		this.categoryService = categoryService;
	}

	public List<String> getNameByCategory() throws Exception {
		List<String> catename = new ArrayList<>();
		List<Category> categories = categoryService.findAllCategory();
		Iterator it = categories.iterator();
		while (it.hasNext()) {
			Category c = (Category) it;
			catename.add(c.getName());
		}
		return catename;
	}

	public String addCategory() throws Exception {
		try {
			utx.begin();
			categoryService.create(newcategory);
			logger.log(Level.INFO, "Added {0}", newcategory);
			return "/Item.xhtml?faces-redirect=true";
		} finally {
			utx.commit();
		}
	}

	public Category getCategoryByName(String name) {
		Category category = new Category();
		category = null;
		List<Category> categories = categoryService.findAllCategory();
		Iterator it = categories.iterator();
		while (it.hasNext()) {
			Category c = (Category) it;
			if (c.getName().equals(name)) {
				category = c;
			}
		}
		return category;
	}

	@SuppressWarnings("unchecked")
	public List<Category> getAllCategory() throws Exception {
		try {
			utx.begin();
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Category.class));
			return em.createQuery(cq).getResultList();
		} finally {
			utx.commit();
		}
	}
        
        public List<Integer> getAllCategoryId() throws Exception{
            List<Integer> cateid = new ArrayList<>();
            List<Category> categorys = getAllCategory();
            for(Category c:categorys){
                cateid.add(c.getId());
            }
            return cateid;
        }
        
       
}
