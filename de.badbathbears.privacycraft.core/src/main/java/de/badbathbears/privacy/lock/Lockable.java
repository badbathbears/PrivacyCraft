package de.badbathbears.privacy.lock;


public interface Lockable {
	
	public static enum LockType {
		ITEM, BLOCK, BOTH;
	}
	
	public static final String DEFAULT_CODE = "0000";
	
	public static final String SET_INDEX = "IsSet";
	public static final String KEY_INDEX = "KeyCode";

	//the code to open the lock
	public void setKeyCode(String keyCode);
	public String getKeyCode();
	
	//is a code set
	public void setSet(boolean set);
	public boolean isSet();
	public LockType getType();
}
