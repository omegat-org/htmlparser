package com.kizna.html.tags;

/**
 * Identifies an image tag 
 */
public class HTMLImageTag extends HTMLTag
{
	/**
	 * The URL where the image is stored.
	 */
	protected String imageURL;
	/**
	 * Constructor creates an HTMLImageNode object, which stores the location
	 * where the image is to be found.
	 * @imageURL location of the image
	 * @imageBegin Beginning position of the image tag
	 * @imageEnd Ending position of the image tag
	 */
	public HTMLImageTag(String imageURL,int imageBegin, int imageEnd,String tagLine)
	{
		super(imageBegin,imageEnd,"",tagLine);
		this.imageURL = imageURL;
	}
	/**
	 * Returns the location of the image
	 */
	public String getImageLocation()
	{
		return imageURL;
	}
	public String toString()
	{
		return "IMAGE TAG : Image at "+imageURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
