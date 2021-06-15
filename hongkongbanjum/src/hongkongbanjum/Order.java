package hongkongbanjum;

import java.io.Serializable;

import java.util.*;

/**
 * hongkongbanjum
 * Order.java
 *
 * @author : 강영훈
 * @Date   : 2021. 02. 18.
 * @Version
 */

public class Order implements Serializable{ //인스턴스 변수를 통해 직렬화 구현
	
	private Integer no;      //주문 번호 
	private String userId;   //주문자의 이름 
	private	String name;     //주문한 메뉴이름
	private	Integer amount;  //주문한 메뉴의 수량
	private	Integer price;   //주문한 메뉴의 가격
		
	public Order() {}
		
	public Order(Integer no, String userId, String name, Integer amount, Integer price) {
		super();
		this.no = no;
		this.userId = userId;
		this.name = name;
		this.amount = amount;
		this.price = price;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
}
