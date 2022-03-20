package Server;

import java.util.ArrayList;

public class Group {

	private int groupId;
	private ArrayList<Client> members;
	
	public int getGroupId() {
		return groupId;
	}
	
	public ArrayList<Client> getMembers() {
		return members;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void setMembers(ArrayList<Client> members) {
		this.members = members;
	}
}
