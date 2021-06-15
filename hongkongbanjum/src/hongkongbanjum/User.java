package hongkongbanjum;

import java.io.Serializable;

/**
 * hongkongbanjum
 * User.java
 *
 * @author : 류슬희
 * @Date   : 2021. 02.18
 * @Version
 */

public class User implements Serializable { //인스턴스 변수를 통해 직렬화 구현
    private String name;     // 이름
    private String id;       // 아이디
    private String password; // 비밀번호
    private String tel;      // 전화번호
    private boolean admin;   //관리자 구분 true: 관리자 false :일반회원

    public User() {}
    
    public User(String name, String id, String password, String tel) {
        super();
        this.name = name;
        this.id = id;
        this.password = password;
        this.tel = tel;
    }
  
	public User(String name, String id, String password, String tel, boolean admin) {
		super();
		this.name = name;
		this.id = id;
		this.password = password;
		this.tel = tel;
		this.admin = admin;
	}
	
	public User(User u) { //클론
		this.name = u.name;
		this.id = u.id;
		this.password = u.password;
		this.tel = u.tel;
		this.admin = u.admin;
	}
	
	public String getName() {
        return name;
    }
	
    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public boolean isAdmin() {
		return admin;
	}
    
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}