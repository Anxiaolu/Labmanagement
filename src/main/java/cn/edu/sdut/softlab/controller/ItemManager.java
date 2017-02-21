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
import java.util.Date;
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

    //前台修改物品信息暂存
    private String itemname;
    private String code;
    private Date dateBought;
    private Integer numTotal;
    private Integer categoryid;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateBought() {
        return dateBought;
    }

    public void setDateBought(Date dateBought) {
        this.dateBought = dateBought;
    }

    

    public Integer getNumTotal() {
        return numTotal;
    }

    public void setNumTotal(Integer numTotal) {
        this.numTotal = numTotal;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    

    // 从前台获取待删物品
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
        }
        finally {
            utx.commit();
        }
    }

    /**
     * 由查找到的item对象集合,遍历获取name集合.
     *
     * @return 生成的name属性集合
     * @throws Exception
     */
    @RequestScoped
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
     *
     * @return 指定url地址（对相应页面的刷新）
     * @throws Exception
     */
    @RequestScoped
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
     *
     * @return 指定url地址（对相应页面的刷新）
     * @throws Exception
     */
    @RequestScoped
    public String editItem() throws Exception {
        utx.begin();
        Item updateitem = itemService.findByName(this.getItemname());
        em.clear();
        if (updateitem == null) {
            throw new NotFoundException();
        }
        if (updateitem.getName() != null) {
            updateitem.setName(this.getItemname());
        }
        if (updateitem.getCode() != null) {
            updateitem.setCode(this.getCode());
        }
        if (updateitem.getNumTotal() != null) {
            updateitem.setNumTotal(this.getNumTotal());
        }
        if (updateitem.getDateBought() != null) {
            updateitem.setDateBought(this.dateBought);
        }
        if (updateitem.getCategory() != null) {
           //updateitem.setCategory(categoryservice.findCategoryById(this.getCategory().getId()));
           updateitem.setCategory(categoryservice.findCategoryById(this.categoryid));
        }
        if (updateitem.getNumTotal() > 0) {
            updateitem.setStatus("AVALIABLE");
        } else {
            updateitem.setStatus("NOT_AVALIABLE");
        }
        em.merge(updateitem);
        utx.commit();
        return "/Item_query.xhtml?faces-redirect=true";
    }

    @RequestScoped
    public List<Item> getAvaliableItem() throws Exception {
        List<Item> items = this.getAllItem();
        List<Item> itemsavb = new ArrayList<>();
        for (Item i : items) {
            if (i.getStatus().equals("AVALIABLE")) {
                itemsavb.add(i);
            }
        }
        return itemsavb;
    }

    @RequestScoped
    public List<Item> getNotAvaliableItem() throws Exception {
        List<Item> items = this.getAllItem();
        List<Item> itemsnoavb = new ArrayList<>();
        for (Item i : items) {
            if (i.getStatus().equals("NOT_AVALIABLE")) {
                itemsnoavb.add(i);
            }
        }
        return itemsnoavb;
    }
}
