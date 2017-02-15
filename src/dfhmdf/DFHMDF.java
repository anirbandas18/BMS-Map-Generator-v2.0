package dfhmdf;

import otf.OTF;
/**
 * This is a bean class that stores all the field properties.
 * 
 * @author NIRUPAM,SOUNAK
 * 
 */
public class DFHMDF extends OTF {
	/**
	 * 
	 */
	private int length,occur;
	private String name, pos, attrb, picin, picout, initial, comment, color,fieldSize;


	public String getPicin() {
		return picin;
	}

	public String getPicout() {
		return picout;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Returns the length property value of DFHMDF
	 * 
	 * @return int
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length property value of DFHMDF
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Returns the field-name property value of DFHMDF
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * /** Sets the field-name property value of DFHMDF
	 * 
	 * @param name
	 *            the field-name value
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the pos propert value of DFHMDF
	 * 
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * Sets the pos property value of DFHMDF
	 * 
	 * @param row
	 * @param col
	 */
	public void setPos(int row, int col) {
		this.pos = "(" + row + "," + col + ")";
	}

	/**
	 * Returns the attrb property value of the DFHMDF
	 * 
	 * @return String
	 */
	public String getAttrb() {
		return attrb;
	}

	/**
	 * Sets the attrb property value of the DFHMDF
	 * 
	 * @param attrb
	 */
	public void setAttrb(String attrb) {
		this.attrb = attrb;
	}

	/**
	 * Sets the picin property value of DFHMDF
	 * 
	 * @param picin
	 */
	public void setPicin(String picin) {
		this.picin = picin;
	}

	/**
	 * Sets the picout property value of DFHMDF
	 * 
	 * @param picout
	 */
	public void setPicout(String picout) {
		this.picout = picout;
	}

	/**
	 * Sets the initial property value of DFHMDF
	 * 
	 * @param initial
	 */
	public void setInitial(String initial) {
		this.initial = initial;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DFHMDF [length=" + length + "occur= "+occur+", name=" + name + ", pos=" + pos
				+ ", attrb=" + attrb + ", picin=" + picin + ", picout="
				+ picout + ",color=" + color + ",initial=" + initial
				+ ", comment=" + comment + "]";
	}

	public String getInitial() {
		return initial;
	}

	public String getColor() {
		return color;
	}
	public int getOccur()
	{
		return occur;
	}
	public void setOccur(int occur)
	{
		this.occur=occur;
	}
	public String getfieldSize()
	{
		return fieldSize;
	}
	public void setFieldSize(String fieldSize)
	{
		this.fieldSize=fieldSize;
	}
}
