package hongkongbanjum;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static hongkongbanjum.Utils.*;

/**
 * hongkongbanjum
 * Service.java
 *
 * @author : 강영훈, 김기영, 류슬희, 박수연
 * @Date   : 2021. 02.18.
 * @Version
 */

public class Service {
	Scanner sc = new Scanner(System.in);
	List<User> users = new ArrayList<User>();
	List<Menu> menus = new ArrayList<Menu>();
	List<Ingredient> ingredients = new ArrayList<Ingredient>();
	List<Order> orders = new ArrayList<Order>();
	Ingredient ingredient = new Ingredient();
	User loginUser; //현재 로그인한 회원의 정보를 담기위한 변수
	String[] fileNameArr = {"user.ser", "menu.ser", "ingredient.ser", "order.ser"}; //데이터 영속화를 위한 파일 이름을 담은 배열

	{
		try {
			for(String str : fileNameArr) { //배열을 통한 파일 이름 탐색 for문
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(str)));
				switch (str) {
				case "user.ser":
					users= (List<User>) ois.readObject();
					break;
				case "menu.ser":
					menus = (List<Menu>) ois.readObject();
					break;
				case "ingredient.ser":
					ingredients = (List<Ingredient>) ois.readObject();
					break;
				case "order.ser":
					orders = (List<Order>) ois.readObject();
					break;
				}
				ois.close();
			}
		} catch (FileNotFoundException e) { //최초 실행시 수행할 기본 데이터
			users.add(new User("강영훈", "kang", "1234", "01011111111", true));
			users.add(new User("손님1", "guest1", "1234", "01023456789"));
			ingredients.add(new Ingredient(1, "쌀", 20, 500));
			ingredients.add(new Ingredient(2, "면", 20, 500));
			ingredients.add(new Ingredient(3, "야채", 20, 300));
			ingredients.add(new Ingredient(4, "돼지고기", 20, 500));
			ingredients.add(new Ingredient(5, "해산물", 20, 500));
			ingredients.add(new Ingredient(6, "조미료", 20, 300));
			ingredients.add(new Ingredient(7, "달걀", 20, 300));
			ingredients.add(new Ingredient(8, "춘장", 20, 200));
			ingredients.add(new Ingredient(9, "만두", 20, 500));
			ingredients.add(new Ingredient(10, "식용유", 20, 100));
			menus.add(new Menu(1, "짜장면", 6000, new Integer[] {2, 3, 4, 6, 8, 10 }));
			menus.add(new Menu(2, "짜장면 곱빼기", 7000, new Integer[] { 2, 3, 4, 6, 8, 10 }));
			menus.add(new Menu(3, "짬뽕", 8000, new Integer[] { 2, 3, 4, 5, 6, 10 }));
			menus.add(new Menu(4, "짬뽕 곱빼기", 9000, new Integer[] { 2, 3, 4, 5, 6, 10 }));
			menus.add(new Menu(5, "볶음밥", 6000, new Integer[] { 1, 3, 4, 6, 7, 10 }));
			menus.add(new Menu(6, "볶음밥 곱빼기", 7000, new Integer[] { 1, 3, 4, 6, 7, 10 }));
			menus.add(new Menu(7, "탕수육(소)", 12000, new Integer[] { 3, 4, 6, 10 }));
			menus.add(new Menu(8, "탕수육(중)", 14000, new Integer[] { 3, 4, 6, 10 }));
			menus.add(new Menu(9, "탕수육(대)", 16000, new Integer[] { 3, 4, 6, 10 }));
			orders.add(new Order(1, "guest1", "짜장면", 2, 12000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @MethodName : join
	 * @author : 류슬희
	 */
	
	public void join() {
		String name = getStrInput(" name: ");
		String id = getStrInput(" ID: ");
		String password = getStrInput(" Password: ");
		String tel = getStrInput(" tel: ");

		if (idCheck(id)) {
			System.out.println("중복된 ID입니다.");
		} else {
			users.add(new User(name, id, password, tel));
			System.out.println(name + " ( " + id + " ) " + "님 가입을 축하드립니다.");

		}
		save("user.ser");
	}

	/**
	 * @MethodName : login
	 * @author : 류슬희
	 */
	
	public void login() {
		String id = getStrInput(" ID : ");
		String password = getStrInput(" Password : ");
		User user = findBy(id);
		if (Objects.isNull(user)) { 
			System.out.println("등록되지 않은 ID입니다.");
		} else if (password.equals(user.getPassword())) {
			System.out.println();
			loginUser = new User(user); // session 현재 로그인된 대상
			System.out.println("[" + user.getId() + "]님께서 로그인 하셨습니다.");
			if (user.isAdmin()) { //(true : 관리자, false : 소비자)를 판단하는 조건식
				adminService(); 
			} else {
				userService();
			}
		} else {
			System.out.println("비밀번호가 틀렸습니다.");
		}
	}

	/**
	 * @MethodName : logout
	 * @author : 류슬희
	 */
	
	public void logout() {
		System.out.println("로그아웃 되었습니다.");
	}

	/**
	 * @MethodName : idCheck
	 * @author : 류슬희
	 */
	
	private boolean idCheck(String id) {
		boolean check = true;
		User user = findBy(id);
		if (Objects.isNull(user))
			check = false;
		return check;
	}

	/**
	 * @MethodName : findBy
	 * @author : 류슬희
	 */
	
	private User findBy(String id) { // 동일한 값이 있는지 확인하는 순회 메서드
		for (User User : users) { 
			if (User.getId().equals(id)) {
				return User;
			}
		}
		return null;
	}

	/**
	 * @MethodName : getStrInput
	 * @author : 류슬희
	 */
	
	private String getStrInput(String string) {
		System.out.print(string);
		return sc.nextLine();
	}

	/**
	 * @MethodName : orderList
	 * @author : 강영훈
	 */
	
	public void orderList() {
		String[] listInfo = new String[] { "주문번호", "주문자명", "주문메뉴", "메뉴수량", "주문가격" };
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(Utils.getFormatOrder(listInfo));
		System.out.println("-----------------------------------------------------------------------------------");

		for (Integer i = 0; i < orders.size(); i++) {
			System.out.println(Utils.getOrderInfo(orders.get(i)));
		}
		int a = 0;
		for (int i = 0; i < orders.size(); i++) {
			a += orders.get(i).getPrice();
		}
		System.out.println("총매출 :: " + a);
	}

	/**
	 * @MethodName : getAmount
	 * @author : 김기영
	 */
	
	public Integer getAmount(Menu menu) { // 각 메뉴에 들어있는 재료들의 갯수 구하는 메서드
		int ret = Integer.MAX_VALUE; 
		for (Integer i : menu.getIngredients()) { // 재료의 개수 카운트
			if (ret > findIngredientNoBy(i).getAmount()) {
				ret = findIngredientNoBy(i).getAmount();
			}
		}
		return ret; // 재료를 바탕으로 가용한 요리 갯수를 리턴
	}

	/**
	 * @MethodName : orderAdd
	 * @author : 강영훈
	 */
	
	public void orderAdd() {
		String userId = loginUser.getId();
		int no = orders.isEmpty() ? 1 : orders.get(orders.size() - 1).getNo() + 1; // 주문 번호는 입력하는게 아니라 자동으로 +1씩 증가
		menuList();
		System.out.println();
		System.out.println("주문 하실 음식의  번호를 입력해 주세요 >> ");
		int menuNo = nextInt();
		Menu menu = findMenuNoBy(menuNo);
		if (menu == null) {
			System.out.println("메뉴에 없는 음식입니다.");
			return;
		}
		if (getAmount(menu) == 0) {
			System.out.println("재료소진으로 인해 해당 메뉴를 주문하실 수 없습니다.");
			return;
		}
		System.out.println("입력하신 메뉴의 수량을 입력해 주세요 >> ");
		int amount = nextInt();
		String name = menu.getName();
		if (getAmount(menu) - amount < 0) { //음식에 들어가는 재료들의 가용 개수를 받아와서 주문이 가능한지 판단
			System.out.println("재료가 부족해서 해당 메뉴를 주문하실 수 없습니다.");
			return;
		}
		
		/**
		 * @MethodName : findMenuNoBy
		 * @author : 김기영
		 */
		
		// 입력받은 메뉴의 번호로 findMenuNoBy메서드로 menus를 순회하여 해당 메뉴의 재료배열을 가져와 arr 배열에 주입
		Integer[] arr = findMenuNoBy(menuNo).getIngredients();
		// arr에 담긴 정보를 가지고 findIngredientNoBy메서드로 ingredients를 순회하여 해당 재료의 수량을 감소 시킨다
		for (Integer i : arr) {
			for (int j = 0; j < amount; j++) {  // 입력 받은 메뉴의 수량 만큼 반복문을 돌려 decreaseAmount메서드를 반복실행
				findIngredientNoBy(i).decreaseAmount();
				if (menu.getName().contains("곱빼기")) { 
					findIngredientNoBy(i).decreaseAmount();
				}
			}
		}
		int price = menu.getPrice() * amount;
		orders.add(new Order(no, userId, name, amount, price));

		System.out.println("주문이 완료 되었습니다.");
		save("ingredient.ser");
		save("order.ser");
	}
	
//
//	/**
//	 * @MethodName : orderRemove
//	 * @author : 김기영
//	 */
//	
//	public void orderRemove() {
//		System.out.print("삭제할 주문의 주문번호을 입력하세요 >> ");
//		int no = nextInt();
//		int idx = -1;
//		for (int i = 0; i < orders.size(); i++) { 
//			if (orders.get(i).getNo().equals(no)) {
//				idx = i;
//			}
//		}
//		if (idx == -1) {
//			System.out.println("찾으시는 주문이 없습니다");
//			return;
//		}
//		orders.remove(idx);
//		save("order.ser");
//	}

	/**
	 * @MethodName : menuList
	 * @author : 김기영
	 */
	
	public void menuList() {
		String[] listInfo = new String[] { "메뉴번호", "메뉴이름", "메뉴가격", "재료" };
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.println(Utils.getFormatMenu(listInfo));
		System.out.println("-------------------------------------------------------------------------------------------");
		
		for (int i = 0; i < menus.size(); i++) {
			System.out.println(Utils.getMenuInfo(menus.get(i)));
		}
	}

	/**
	 * @MethodName : menuAdd
	 * @author : 김기영
	 */
	
	public void menuAdd() {

		System.out.print("추가할 음식의 번호 >> ");
		Integer no = nextInt();
		System.out.print("추가할 음식의 이름 >> ");
		String name = nextLine();
		System.out.print("추가할 음식의 가격 >> ");
		Integer price = nextInt();

		List<Integer> list = new ArrayList<>();
		ingredientList();
		while (true) {
			System.out.print("추가할 음식의 재료 (0입력시 입력종료) >> ");
			Integer ingredientNo = nextInt();
			if (ingredientNo == 0) {
				break;
			} else {
				list.add(ingredientNo);
			}
		}
		Integer[] ingredients = new Integer[list.size()];
		list.toArray(ingredients);

		//새로운 ArrayList를 생성하여 음식의 재료 번호를 Integer 타입으로 저장하고 배열 타입으로 반환
		menus.add(new Menu(no, name, price, ingredients));
		save("menu.ser");
	}

	/**
	 * @MethodName : menuModify
	 * @author : 김기영
	 */
	
	public void menuModify() {
        menuList();
        System.out.print("수정할 메뉴의 번호를 입력해주세요. >> ");
        int input = nextInt();

        Menu menu = findMenuNoBy(input);

        if (menu == null) {
            System.out.println("메뉴의 번호를 잘못 입력하셨습니다.");
        } else {
            System.out.println("수정할 음식의 이름 >> ");
            menu.setName(nextLine());
            System.out.println("수정할 음식의 가격 >> ");
            menu.setPrice(nextInt());

            List<Integer> list = new ArrayList<>();
            ingredientList();
            while (true) {
                System.out.print("추가할 음식의 재료 (0입력시 입력종료) >> ");
                Integer ingredientNo = nextInt();
                if (ingredientNo == 0) {
                    break;
                } else {
                    list.add(ingredientNo);
                }
            }
            Integer[] ingredients = new Integer[list.size()];
            findMenuNoBy(input).setIngredients(list.toArray(ingredients)); 
          //입력받은 값은 새로운 ArrayList에 담아 Integer 타입의 배열에 반환 후 findMenuNoBy()를 통해 해당메뉴의 반영
        }
        save("menu.ser");
    }

	/**
	 * @MethodName : menuRemove
	 * @author : 김기영
	 */
	
	public void menuRemove() {
		menuList();
		System.out.print("삭제할 음식의 번호를 입력해주세요. >> ");
		int no = nextInt();
		int index = -1;
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getNo().equals(no)) {
				index = i;
			}
		}
		if (index == -1) {
			System.out.println("음식의 번호가 잘못 되었습니다.");
			return;
		}
		menus.remove(index);
		save("menu.ser");
	}

	/**
	 * @MethodName : findmenuNoBy
	 * @author : 김기영
	 */
	
	public Menu findMenuNoBy(int no) { //입력받은 번호의 메뉴를 찾는 메서드
		Menu menu = null;

		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getNo().equals(no)) {
				menu = menus.get(i);
			}
		}
		return menu;
	}

	/**
	 * @MethodName : ingredientList
	 * @author : 박수연
	 */
	
		public void ingredientList() {
			String[] listInfo = new String[] { "재료번호", "재료이름", "재료수량", "재료가격" };
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(Utils.getFormatIngredient(listInfo));
			System.out.println("---------------------------------------------------------------------------------------");

			for (int i = 0; i < ingredients.size(); i++) {
				System.out.println(Utils.getIngredientInfo(ingredients.get(i)));
			}
		}

		/**
		 * @MethodName : ingredientAdd
		 * @author : 박수연
		 */
		
		public void ingredientAdd() {
			System.out.print("추가할 재료 번호 :: ");
			Integer no = nextInt();
			Ingredient ingredient = findIngredientNoBy(no);
			
			if(ingredient == null) {
				System.out.print("추가할 재료 이름 :: ");
				String name = nextLine();
				System.out.print("추가할 재료 수량 :: ");
				Integer amount = nextInt(); 
				System.out.print("추가할 재료 가격 :: ");
				Integer price = nextInt();
				System.out.println("재료가 추가 되었습니다.");
				ingredients.add(new Ingredient(no, name, amount, price));
				save("ingredient.ser");
								
			}else {
				System.out.println("이미 있는 번호입니다.");
			}
			/*System.out.print("재료 번호 :: ");
			Integer no = nextInt();
			System.out.print("재료 이름 :: ");
			String name = nextLine();
			System.out.print("재료 수량 :: ");
			Integer amount = nextInt();
			System.out.print("재료 가격 :: ");
			Integer price = nextInt();

			ingredients.add(new Ingredient(no, name, amount, price));
			save("ingredient.ser");*/
		}

		// 재료수정 - 수연작성
		public void ingredientModify() {
			ingredientList();
			System.out.print("수정할 재료의 번호를 입력하세요 :: ");
			Integer input = nextInt();
			Ingredient ingredient = findIngredientNoBy(input); //입력받은 재료의 번호를 findIngredientNoBy를 순회하여 찾고 반환

			if (ingredient == null) {
				System.out.println("재료 번호를 찾을 수 없습니다.");
			} else {
				System.out.print("재료 이름 :: ");
				ingredient.setName(nextLine());
				System.out.print("재료 수량 :: ");
				ingredient.setAmount(nextInt());
				System.out.print("재료 가격 :: ");
				ingredient.setPrice(nextInt());
			}
			save("ingredient.ser");
		}

		/**
		 * @MethodName : ingredientRemove
		 * @author : 박수연
		 */
		
		public void ingredientRemove() {
			System.out.print("삭제할 재료의 번호를 입력하세요 :: "); 
			int no = nextInt();
			int idx = -1;
			for (int i = 0; i < ingredients.size(); i++) { 
				if (ingredients.get(i).getNo().equals(no)) {
					idx = i;
				}
			}
			if (idx == -1) {
				System.out.println("삭제할 번호가 없습니다");
				return;
			}
			ingredients.remove(idx);
			save("ingerdient.ser");
		}

		/**
		 * @MethodName : findIngredientNoBy
		 * @author : 박수연
		 */
		
		public Ingredient findIngredientNoBy(int no) { //입력받은 번호의 재료를 찾는 메서드
			Ingredient ingredient = null;
			
			for (int i = 0; i < ingredients.size(); i++) {
				if (ingredients.get(i).getNo().equals(no)) {
					ingredient = ingredients.get(i);
				}
			}
			return ingredient;
		}

		/**
		 * @MethodName : userService
		 * @author : 류슬희
		 */
		
		//사용자 서비스 스위치문
	public void userService() { 
		boolean run = true;
		while (run) {
			System.out.println("--------------------------");
			System.out.println("       고객 서비스        ");
			System.out.println("--------------------------");
			System.out.println(" [1]주문하기 [2]로그아웃  ");
			System.out.println("--------------------------");
			int input = nextInt();
			switch (input) {
			case 1:
				orderAdd();
				break;
			case 2:
				logout();
				return;
			default:
				System.out.println("1-2까지만 입력해주세요.");
			}
		}
	}

	/**
	 * @MethodName : menuService
	 * @author : 김기영
	 */
	
	// 메뉴 서비스 스위치문
	public void menuService() {
		boolean run = true;
		while (run) {
			System.out.println("----------------------------------------------------------------------");
			System.out.println("                                   메뉴                               ");
			System.out.println("----------------------------------------------------------------------");
			System.out.println(" [1]메뉴 조회 [2]메뉴 추가 [3]메뉴 수정 [4]메뉴 삭제 [5]관리자 페이지 ");
			System.out.println("----------------------------------------------------------------------");
			int input = nextInt();
			switch (input) {
			case 1:
				menuList();
				break;
			case 2:
				menuAdd();
				break;
			case 3:
				menuModify();
				break;
			case 4:
				menuRemove();
				break;
			case 5:
				return;
			default:
				System.out.println("1-5까지만 입력해주세요.");
			}
		}
	}

	/**
	 * @MethodName : ingredientService
	 * @author : 박수연
	 */
	
	// 재료서비스 스위치문 
	public void ingredientService() {
		boolean run = true;
		while (run) {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("                                  재료                               ");
			System.out.println("---------------------------------------------------------------------");
			System.out.println(" [1]재료 종류 [2]재료 추가 [3]재료 수정 [4]재료 삭제 [5]관리자 페이지");
			System.out.println("---------------------------------------------------------------------");
			int input = nextInt();
			switch (input) {
			case 1:
				ingredientList();
				break;
			case 2:
				ingredientAdd();
				break;
			case 3:
				ingredientModify();
				break;
			case 4:
				ingredientRemove();
				break;
			case 5:
				return;
			default:
				System.out.println("정확한 숫자를 입력하세요.");
				break;
			}
		}
	}

	/**
	 * @MethodName : adminService
	 * @author : 류슬희
	 */
	
	// 관리자 서비스 스위치문
	public void adminService() {
		boolean run = true;
		while (run) {
			System.out.println("---------------------------------------------------------");
			System.out.println("                       관리자 페이지                     ");
			System.out.println("---------------------------------------------------------");
			System.out.println(" [1]메뉴 서비스 [2]재료 서비스 [3]주문 조회 [4]로그아웃  ");
			System.out.println("---------------------------------------------------------");
			int input = nextInt();
			switch (input) {
			case 1:
				menuService();
				break;
			case 2:
				ingredientService();
				break;
			case 3:
				orderList();
				break;
			case 4:
				logout();
				return;
			default:
				System.out.println("1-3까지만 입력해주세요.");
			}
		}
	}

//	// 주문하기 서비스 메서드 손님한테 보여지는 스위치
//	public void orderService() {
//		boolean run = true;
//		while (run) {
//			System.out.println("-------------------------------------");
//			System.out.println("                 주문                ");
//			System.out.println("-------------------------------------");
//			System.out.println(" [1]주문하기 [2]로그아웃 ");
//			System.out.println("-------------------------------------");
//			int input = nextInt();
//			switch (input) {
//			case 1:
//				orderAdd();
//				break;
//			case 2:
//				logout();
//				break;
//			case 3:
//				return;
//			default:
//				System.out.println("1-3까지만 입력해주세요.");
//			}
//		}
//	}
	
	/**
	 * @MethodName : save
	 * @author : 강영훈
	 */
	
	private void save(String str) { //변동된 데이터들을 FileOutputStream을 통해 각각의 해당되는 .ser파일에 저장
		try {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(str)));
				switch (str) {
				case "user.ser":
					oos.writeObject(users);
					break;
				case "menu.ser":
					oos.writeObject(menus);
					break;
				case "ingredient.ser":
					oos.writeObject(ingredients);
					break;
				case "order.ser":
					oos.writeObject(orders);
					break;
				}
				oos.close();
		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
}
