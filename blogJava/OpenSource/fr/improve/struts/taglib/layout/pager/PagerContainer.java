package fr.improve.struts.taglib.layout.pager;



public interface PagerContainer {
	public void setLength(int in_length);
	public void setOffset(int in_offset);
	public int getSize();
	public int getIndex();
}

