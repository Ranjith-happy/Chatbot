/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 28, 2020, 6:19:23 PM                    ---
 * ----------------------------------------------------------------
 */
package org.happybot.jalo;

import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.happybot.constants.HappybotConstants;
import org.happybot.jalo.ActivityAnswers;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem ActivityQuestions}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedActivityQuestions extends GenericItem
{
	/** Qualifier of the <code>ActivityQuestions.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>ActivityQuestions.product</code> attribute **/
	public static final String PRODUCT = "product";
	/** Qualifier of the <code>ActivityQuestions.createdBy</code> attribute **/
	public static final String CREATEDBY = "createdBy";
	/** Qualifier of the <code>ActivityQuestions.isActive</code> attribute **/
	public static final String ISACTIVE = "isActive";
	/** Qualifier of the <code>ActivityQuestions.answers</code> attribute **/
	public static final String ANSWERS = "answers";
	/**
	* {@link OneToManyHandler} for handling 1:n ANSWERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<ActivityAnswers> ANSWERSHANDLER = new OneToManyHandler<ActivityAnswers>(
	HappybotConstants.TC.ACTIVITYANSWERS,
	false,
	"questions",
	null,
	false,
	true,
	CollectionType.SET
	).withRelationQualifier("answers");
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(PRODUCT, AttributeMode.INITIAL);
		tmp.put(CREATEDBY, AttributeMode.INITIAL);
		tmp.put(ISACTIVE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.answers</code> attribute.
	 * @return the answers
	 */
	public Set<ActivityAnswers> getAnswers(final SessionContext ctx)
	{
		return (Set<ActivityAnswers>)ANSWERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.answers</code> attribute.
	 * @return the answers
	 */
	public Set<ActivityAnswers> getAnswers()
	{
		return getAnswers( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.answers</code> attribute. 
	 * @param value the answers
	 */
	public void setAnswers(final SessionContext ctx, final Set<ActivityAnswers> value)
	{
		ANSWERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.answers</code> attribute. 
	 * @param value the answers
	 */
	public void setAnswers(final Set<ActivityAnswers> value)
	{
		setAnswers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to answers. 
	 * @param value the item to add to answers
	 */
	public void addToAnswers(final SessionContext ctx, final ActivityAnswers value)
	{
		ANSWERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to answers. 
	 * @param value the item to add to answers
	 */
	public void addToAnswers(final ActivityAnswers value)
	{
		addToAnswers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from answers. 
	 * @param value the item to remove from answers
	 */
	public void removeFromAnswers(final SessionContext ctx, final ActivityAnswers value)
	{
		ANSWERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from answers. 
	 * @param value the item to remove from answers
	 */
	public void removeFromAnswers(final ActivityAnswers value)
	{
		removeFromAnswers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.createdBy</code> attribute.
	 * @return the createdBy
	 */
	public User getCreatedBy(final SessionContext ctx)
	{
		return (User)getProperty( ctx, CREATEDBY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.createdBy</code> attribute.
	 * @return the createdBy
	 */
	public User getCreatedBy()
	{
		return getCreatedBy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.createdBy</code> attribute. 
	 * @param value the createdBy
	 */
	public void setCreatedBy(final SessionContext ctx, final User value)
	{
		setProperty(ctx, CREATEDBY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.createdBy</code> attribute. 
	 * @param value the createdBy
	 */
	public void setCreatedBy(final User value)
	{
		setCreatedBy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.isActive</code> attribute.
	 * @return the isActive
	 */
	public Boolean isIsActive(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISACTIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.isActive</code> attribute.
	 * @return the isActive
	 */
	public Boolean isIsActive()
	{
		return isIsActive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @return the isActive
	 */
	public boolean isIsActiveAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsActive( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @return the isActive
	 */
	public boolean isIsActiveAsPrimitive()
	{
		return isIsActiveAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISACTIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final Boolean value)
	{
		setIsActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final SessionContext ctx, final boolean value)
	{
		setIsActive( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final boolean value)
	{
		setIsActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct(final SessionContext ctx)
	{
		return (Product)getProperty( ctx, PRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ActivityQuestions.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct()
	{
		return getProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final SessionContext ctx, final Product value)
	{
		setProperty(ctx, PRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ActivityQuestions.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final Product value)
	{
		setProduct( getSession().getSessionContext(), value );
	}
	
}
