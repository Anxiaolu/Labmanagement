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
package cn.edu.sdut.softlab.controller;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;


/**
 * 作为中间变量暂存数据
 * 
 * @author huanlu
 *
 */
@RequestScoped
@Named
@Default
public class Credentials {

	// 用户部分
	private String username;
	private String password;
	private Integer level;

	private String name;
	
	public Credentials() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
