package hongkongbanjum;

import java.io.Serializable;
/*
 * @author  : 김기영
 * @version : 1.0
 * @date    : 2021.02.18
 */
import java.util.Arrays;

/**
 * hongkongbanjum
 * Menu.java
 *
 * @author : 김기영
 * @Date   : 2021. 02. 18.
 * @Version
 */

public class Menu implements Serializable{ //인스턴스 변수를 통해 직렬화 구현
	private Integer no;             // 메뉴 번호
	private String name;            // 메뉴 이름
	private Integer price;          // 메뉴 가격
	private Integer[] ingredients;  // 재료
	
	public Menu() {	}

	public Menu(Integer no, String name, Integer price, Integer[] ingredients) {
		super();
		this.no = no;
		this.name = name;
		this.price = price;
		this.ingredients = ingredients;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(Integer[] ingredients) {
		this.ingredients = ingredients;
	}
	
}