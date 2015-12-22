package project.beans;

public enum Category
{
	PRIMI(1, "Primi", 1), SECONDI(2, "Secondi", 2), CONTORNI(3, "Contorni", 1);

	protected final int id;
	protected final String category;
	protected final int points;

	private Category(int id, String type, int points)
	{
		this.id = id;
		this.category = type;
		this.points = points;
	}

	public int getId()
	{
		return id;
	}

	public String getType()
	{
		return category;
	}

	public int getPoints()
	{
		return points;
	}

	public static Category getDishCategory(String category)
	{
		switch (category)
		{
		case "Primi":
			return PRIMI;
		case "Secondi":
			return SECONDI;
		case "Contorni":
			return CONTORNI;
		default:
			return null;
		}

	}

	public static Category getDishCategoryByID(String category)
	{
		switch (category)
		{
		case "1":
			return PRIMI;
		case "2":
			return SECONDI;
		case "3":
			return CONTORNI;
		default:
			return null;
		}

	}

//	public String toJSon()
//	{
//		return JsonConverter.converterToJson(this);
//	}

	@Override
	public String toString()
	{
		return "DishCategory [id=" + id + ", type=" + category + ", points=" + points + "]";
	}

}