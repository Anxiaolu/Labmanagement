package cn.edu.sdut.softlab.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import cn.edu.sdut.softlab.model.Category;
import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.service.CategoryFacade;
import cn.edu.sdut.softlab.service.ItemFacade;

@Named("itemaddmanager")
@RequestScoped
public class ItemAddManager {

	@Inject
	private transient Logger logger;

	@Inject
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Inject
	ItemFacade itemService;

	@Inject
	CategoryFacade categoryservice;

	private String categoryname;

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	private Item newItem = new Item();

	public Item getNewItem() {
		if (newItem.getCategory() == null) {
			newItem.setCategory(new Category());
		}
		return newItem;
	}

	public void setNewItem(Item newItem) {
		this.newItem = newItem;
	}

	@Produces
	@Named
	@RequestScoped
	public List<Category> AllCategory() throws Exception {
		return categoryservice.findAll(Category.class);
	}


	public Category findCategoryByName(String name) {
		Category category = new Category();
		category = null;
		List<Category> categories = categoryservice.findAllCategory();
		Iterator it = categories.iterator();
		while (it.hasNext()) {
			Category c = (Category) it.next();
			if (c.getName().equals(name)) {
				category = c;
			}
		}
		return category;
	}


	/**
	 * 向数据库中添加前台页面输入的Item对象
	 *
	 * @return 重定向到当前页面（刷新页面）
	 * @throws Exception
	 */
	public String addItem() throws Exception {
		try {
                    utx.begin();
                    if (!newItem.getName().equals("")) {
                        newItem.setDateBought(new Date());
                    //newItem.setCategory(categoryservice.findCategoryByName(categoryname));
                    itemService.create(newItem);
                    logger.log(Level.INFO, "Added {0}", newItem);
                    return "/Item.xhtml?faces-redirect=true";
                    }
                    else{
                        return "/Error.xhtml?faces-redirect=true";
                    }
		} finally {
			utx.commit();
		}
	}

}
