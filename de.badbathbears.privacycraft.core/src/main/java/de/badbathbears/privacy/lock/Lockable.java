package de.badbathbears.privacy.lock;

public interface Lockable {
	public static final String DEFAULT = "0000";
	public static String KEY_CODE = "KeyCode";

	public void setKeyCode(String keyCode);
	public String getKeyCode();
}
