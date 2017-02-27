package com.android.mynote.object;

public class Bill {
	private int id;
	private String text;
	private int image;
	private int inOrout;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public int getInOrout() {
		return inOrout;
	}

	public void setInOrout(int inOrout) {
		this.inOrout = inOrout;
	}

	public Bill(int id, String text, int image, int inOrout) {
		super();
		this.id = id;
		this.text = text;
		this.image = image;
		this.inOrout = inOrout;
	}

	public Bill() {
		super();
	}

}
