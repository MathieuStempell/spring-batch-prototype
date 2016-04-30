package mstempell.springframework.batch.proto.bean;

public class Game {

	private int id;
	private int userId;
	private String gameName;
	private String systemName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	@Override
	public String toString() {
		return "Game [id=" + id + ", userId=" + userId + ", gameName=" + gameName + ", systemName=" + systemName + "]";
	}
}
