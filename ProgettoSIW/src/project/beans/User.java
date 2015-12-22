package project.beans;

import java.io.Serializable;

public class User implements Serializable {

	protected String username;
	protected String password;
	protected String email;
	protected String profileName;
	protected String imageUrl;
	protected String social;
	
	public User(String username, String password, String email, String profileName, String imageUrl,
			String social)
	{
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.profileName = profileName;
		this.imageUrl = imageUrl;
		this.social = social;
	}

	public User()
	{
		profileName = "";
		imageUrl = "";
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getProfileName()
	{
		return profileName;
	}

	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getSocial() {
		return social;
	}
	
	public String toJSON()
	{
		return "{username:'" + username + "', password:'" + password + "', profileName:'" + profileName + "', imageUrl:'" + imageUrl
				+ "', email:'" + email+"', social:'"+social+"'}";

	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", email=" + email + ", profileName=" + profileName
				+ ", imageUrl=" + imageUrl + ", social=" + social + "]";
	}

	
}
