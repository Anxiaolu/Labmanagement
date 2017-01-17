package cn.edu.sdut.softlab.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.service.CategoryFacade;
import cn.edu.sdut.softlab.service.ItemFacade;

@Named("itemManager")
@RequestScoped
public class ItemManager {

	@Inject
	EntityManager em;

	@Inject
	ItemFacade itemService;

	@Inject
	CategoryFacade categoryservice;

	@Inject
	private UserTransaction utx;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	@Produces
	@Named
	@RequestScoped
	public List<Item> getAllItem() throws Exception {
		try {
			utx.begin();
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Item.class));
			return em.createQuery(cq).getResultList();
		} finally {
			utx.commit();
		}
	}
}
