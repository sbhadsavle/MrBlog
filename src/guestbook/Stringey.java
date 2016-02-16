package guestbook;

import java.util.Date;



import com.google.appengine.api.users.User;

import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

@Entity

public class Stringey {
	
    @Id Long id;
	String string;
	
    private Stringey() {}
	
	public Stringey(String string){
		this.string = string;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Stringey){
			return ((Stringey)o).string.equals(this.string);
		}
		return false;
	}
	

}
