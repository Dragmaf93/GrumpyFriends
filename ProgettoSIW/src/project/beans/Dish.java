package project.beans;

import java.io.Serializable;

public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String name;
	protected String imageUrl;
	protected String description;
	protected Float avgRating;
	protected Category category;

	public Dish(Integer id, String name, String imageUrl, String description, Float avgRating, Category category)
	{
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.description = description;
		this.avgRating = avgRating;
		this.category = category;

	}

	public Dish()
	{
	}

	public Dish(String name, String imageUrl, String description, Category category)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.description = description;
		this.category = category;

	}

	public Integer getId()
	{
		return id;
	}

	public void setID(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Float getAvgRating()
	{
		return avgRating;
	}

	public void setRating(Float avgRating)
	{
		this.avgRating = avgRating;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

//	public String toJson()
//	{
//		return JsonConverter.converterToJson(this);
//	}

	@Override
	public String toString()
	{
		return "Dish [ID=" + id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + ", avgRating="
				+ avgRating + ",category= " + category + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
