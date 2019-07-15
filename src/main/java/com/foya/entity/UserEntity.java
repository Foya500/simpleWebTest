package com.foya.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TB_SYS_USER")
public class UserEntity implements Serializable {
	  private static final long serialVersionUID = 7532854734771407668L;
	    @Id
	    /*
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
	    @SequenceGenerator(sequenceName = "customer_seq", allocationSize = 1, name = "CUST_SEQ")
	    Long id;
	    */
	    @Column(name="USER_ID")
	    private String userId;

	    @Column(name="NAME")
	    private String name;

	    @Column(name="EMAIL")
	    private String email;

	    @Column(name="PASSWORD")
	    private String password;

	    @Column(name = "CRDATE")
	    private Date createDate;
	    
	    @Column(name = "CRUSER")
	    private String createUser;
	    
	    public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}

		/*
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	*/
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }


	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public Date getCreateDate() {
	        return createDate;
	    }

	    public void setCreateDate(Date createDate) {
	        this.createDate = createDate;
	    }


	    @Override
	    public String toString() {
	        return String.format(
	                "AuthUser[userId='%s', name='%s', email='%s', password='%s', Date='%s']",
	                userId, name, email,password,createDate);
	    }


}
