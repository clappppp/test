package hongkongbanjum;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * hongkongbanjum
 * Utils.java
 *
 * @author : 강영훈, 김기영, 류슬희, 박수연
 * @Date   : 2021. 02. 18.
 * @Version
 */

public class Utils {
    private static Scanner scanner = new Scanner(System.in);

    public static int nextInt() {
        return Integer.parseInt(scanner.nextLine());
    }
    public static String nextLine() {
        return scanner.nextLine();
    }
    

    
    // 메뉴번호, 메뉴이름, 메뉴가격, 들어간 재료
    public static String getMenuInfo(Menu menu) {

       Service service = new Service();
       List<String> menuingredients = new ArrayList<>(); 
       
       for(int i : menu.getIngredients()) {
          menuingredients.add(service.findIngredientNoBy(i).getName());   
       }
       
       String[] strs  = {menu.getNo() + "", menu.getName(), menu.getPrice() + "", menuingredients + ""};

       return getFormatMenu(strs);
    
    }
    public static String getFormatMenu(String[] strs) {
       String ret = "";
       for (String str : strs) {
          char[] words = str.toCharArray();
          int cnt = 20;
          for(char c : words) {
             if(c >= '가' && c <= '힣') {
                if(cnt-- == 2) {
                	break;   
                }
             }
          }
          ret += "%-" + cnt + "s";
       }
       return String.format(ret, (Object[]) strs);
    }
    
    
    public static String getIngredientInfo (Ingredient ingredient) {
       String[] strs = {ingredient.getNo() + "", ingredient.getName(), ingredient.getAmount() + "", ingredient.getPrice() + ""};

       return getFormatIngredient(strs);
    }
    public static String getFormatIngredient(String[] strs) {
       String ret = "";
       for(String str : strs) {
          char[] words = str.toCharArray();
          int cnt = 20;
          for(char c : words) {
             if(c >= '가' && c <= '힣') {
                cnt--;
             }
          }
          ret += "%-" + cnt + 's';
       }
       
       return String.format(ret, (Object[]) strs);
    }
    
    public static String getOrderInfo(Order order) {
       String[] strs = {order.getNo() + "", order.getUserId(), order.getName(), order.getAmount() + "", order.getPrice() + ""};
       
       return getFormatOrder(strs);
    }
    public static String getFormatOrder(String[] strs) {
       String ret = "";
       for(String str : strs) {
          char[] words = str.toCharArray();
          int cnt = 20;
          for(char c : words) {
             if(c >= '가' && c <= '힣') {
                cnt--;
             }
          }
          ret += "%-" + cnt + 's';
       }
       return String.format(ret, (Object[]) strs);
    }
}