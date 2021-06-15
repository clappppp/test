package hongkongbanjum;

import static hongkongbanjum.Utils.*;

/**
 * hongkongbanjum
 * HongKongEx.java
 *
 * @author : 류슬희
 * @Date   : 2021. 02.18
 * @Version
 */

public class HongKongEx {
	public static void main(String[] args) {
		Service service  = new Service();
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println("----------------------------------");
			System.out.println("           홍 콩 반 점            ");
			System.out.println("----------------------------------");
			System.out.println(" [1]회원가입  [2]로그인  [3]종료  ");
			int input = nextInt();
			switch (input) {
			case 1:
				service.join();   // 회원가입 메서드 호출
				break;
			case 2:
				service.login();  // 로그인 메서드 호출
				break;		
			case 3:
				System.out.println("종료 되었습니다."); 
				return;           // 메인 메서드 종료
			default:
				System.out.println("1-3까지 선택해주세요."); 
			}
		}
	}
}
