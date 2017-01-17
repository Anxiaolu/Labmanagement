package cn.edu.sdut.softlab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import javax.ws.rs.NotFoundException;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.service.CategoryFacade;
import cn.edu.sdut.softlab.service.ItemFacade;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("itemManager")
@RequestScoped
public class ItemManager {

	@Inject
	private transient Logger logger;

	@Inject
	EntityManager em;

	@Inject
	ItemFacade itemService;

	@Inject
	Credentials credentials;

	@Inject
	CategoryFacade categoryservice;

	@Inject
	private UserTransaction utx;

	private String name;

	// 从前台获取暂存的物品
	private Item currentItem = new Item();

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}

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

	/**
	 * 由查找到的item对象集合,遍历获取name集合.
	 * @return 生成的name属性集合
	 * @throws Exception
	 */
	public List<String> getAllItemName() throws Exception {
		List<String> itemname = new ArrayList<>();
		List<Item> itemlist = getAllItem();
		for (Item i : itemlist) {
			itemname.add(i.getName());
		}
		return itemname;
	}

	/**
	 * 根据前台输入的名字查找数据库删除对应的item对象
	 * @return 指定url地址（对相应页面的刷新）
	 * @throws Exception
	 */
	public String removeItem() throws Exception {
		Item temporitem = itemService.findByName(credentials.getName());
		if (temporitem != null) {
			currentItem = temporitem;
		}
		utx.begin();
		itemService.remove(currentItem);
		// em.remove(em.merge(currenstuff));		//尝试用过entitymanager进行提交
		// em.flush();
		utx.commit();
		logger.log(Level.INFO, "Added {0}");
		return "/RemoveItem.xhtml?faces-redirect=true";
	}

	/**
	 * 根据前台输入的名字查找数据库中对应的物品然后比对修改
	 * @return 指定url地址（对相应页面的刷新）
	 * @throws Exception
	 */
	public String editItem() throws Exception {
		utx.begin();
		Item updateitem = itemService.findByName(currentItem.getName());
		em.clear();
		if (updateitem == null)
			throw new NotFoundException();
		if (currentItem.getName() != null)
			updateitem.setName(currentItem.getName());
		if (currentItem.getCode() != null)
			updateitem.setCode(currentItem.getCode());
		if (currentItem.getStatus() != null)
			updateitem.setStatus(currentItem.getStatus());
		if (currentItem.getNumTotal() != null)
			updateitem.setNumTotal(currentItem.getNumTotal());
		em.merge(updateitem);
		utx.commit();
		return "/Item_query.xhtml?faces-redirect=true";
	}
}
