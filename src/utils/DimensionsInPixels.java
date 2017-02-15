package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;
/*
 * Anirban Das
 */
public class DimensionsInPixels 
{
	private int TEXT_HOR_LEN,TEXT_VER_LEN,MAX_LENGTH;
	private String messages[],MAX_STR;
	private Font def;
	private Dimension textDim;
	public DimensionsInPixels()
	{
		def=new JLabel("").getFont();
	}
	private void getLongestString()
	{
		MAX_STR=messages[0];
		MAX_LENGTH=MAX_STR.length();
		int i;
		for(i=1;i<messages.length;i++)
			{
				if(messages[i].length()>MAX_LENGTH)
				{
					MAX_LENGTH=messages[i].length();
					MAX_STR=messages[i];
				}
			}
	}
	public Dimension getTextDimensionsInPixels(String messages[])
	{
		this.messages=messages;
		getLongestString();
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		TEXT_HOR_LEN = (int)(def.getStringBounds(MAX_STR, frc).getWidth());
		TEXT_VER_LEN = (int)(def.getStringBounds(MAX_STR, frc).getHeight());
		textDim=new Dimension(TEXT_HOR_LEN,TEXT_VER_LEN);
		return textDim;
	}
	public Dimension getTextDimensionsInPixels(Font def,String messages[])
	{
		this.messages=messages;
		getLongestString();
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		TEXT_HOR_LEN = (int)(def.getStringBounds(MAX_STR, frc).getWidth());
		TEXT_VER_LEN = (int)(def.getStringBounds(MAX_STR, frc).getHeight());
		textDim=new Dimension(TEXT_HOR_LEN,TEXT_VER_LEN);
		return textDim;
	}
}
