package hongkongbanjum;

import java.io.Serializable;

/**
 * hongkongbanjum
 * Ingredient.java
 *
 * @author : 박수연
 * @Date   : 2021. 02.18
 * @Version
 */

public class Ingredient implements Serializable { //인스턴스 변수를 통해 직렬화 구현
	private Integer no;      // 재료번호
	private String name;     // 재료이름
	private Integer amount;  // 재료수량
	private Integer price;   // 재료가격

	public Ingredient() {}

	public Ingredient(Integer no, String name, Integer amount, Integer price) {
		super();
		this.no = no;
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

	Integer increaseAmount() {  // 재료수량+
		return ++amount;
	}

	Integer decreaseAmount() {  // 재료수량-
		 return --amount;
	}
}
