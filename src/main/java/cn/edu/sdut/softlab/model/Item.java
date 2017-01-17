/*
 * Copyright 2016 Su Baochen and individual contributors by the 
 * @authors tag. See the copyright.txt in the distribution for
 * a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.sdut.softlab.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Su Baochen
 */
@ManagedBean(name="Item") 
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
  @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
  @NamedQuery(name = "Item.findByName", query = "SELECT i FROM Item i WHERE i.name = :name"),
  @NamedQuery(name = "Item.findByCode", query = "SELECT i FROM Item i WHERE i.code = :code"),
  @NamedQuery(name = "Item.findByStatus", query = "SELECT i FROM Item i WHERE i.status = :status"),
  @NamedQuery(name = "Item.findByNumTotal", query = "SELECT i FROM Item i WHERE i.numTotal = :numTotal"),
  @NamedQuery(name = "Item.findByDateBought", query = "SELECT i FROM Item i WHERE i.dateBought = :dateBought"),
  @NamedQuery(name = "Item.findByNameAndCode", query = "SELECT i FROM Item i WHERE i.dateBought = :dateBought and i.code= :code")})
public class Item implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Size(max = 128)
  @Column(name = "name")
  private String name;
  @Size(max = 32)
  @Column(name = "code")
  private String code;
  @Size(max = 64)
  @Column(name = "status")
  private String status;
  @Column(name = "num_total")
  private Integer numTotal;
  @Column(name = "date_bought")
  @Temporal(TemporalType.DATE)
  private Date dateBought;
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  @ManyToOne
  private Category category;
  @OneToMany(mappedBy = "item")
  private Set<ItemAccount> itemAccountSet;

  public Item() {
  }

  public Item(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getNumTotal() {
    return numTotal;
  }

  public void setNumTotal(Integer numTotal) {
    this.numTotal = numTotal;
  }

  public Date getDateBought() {
    return new Date(dateBought.getTime());
  }

  public void setDateBought(Date dateBought) {
    this.dateBought = new Date(dateBought.getTime());
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @XmlTransient
  public Set<ItemAccount> getItemAccountSet() {
    return itemAccountSet;
  }

  public void setItemAccountSet(Set<ItemAccount> itemAccountSet) {
    this.itemAccountSet = itemAccountSet;
  }

  @Override
  public String toString() {
    return "cn.edu.sdut.softlab.model.Item[ id=" + id + " ]";
  }

}
