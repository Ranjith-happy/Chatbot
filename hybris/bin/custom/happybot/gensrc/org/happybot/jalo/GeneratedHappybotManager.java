/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 28, 2020, 6:19:23 PM                    ---
 * ----------------------------------------------------------------
 */
package org.happybot.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.HashMap;
import java.util.Map;
import org.happybot.constants.HappybotConstants;
import org.happybot.jalo.ActivityAnswers;
import org.happybot.jalo.ActivityQuestions;

/**
 * Generated class for type <code>HappybotManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHappybotManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	public ActivityAnswers createActivityAnswers(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( HappybotConstants.TC.ACTIVITYANSWERS );
			return (ActivityAnswers)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ActivityAnswers : "+e.getMessage(), 0 );
		}
	}
	
	public ActivityAnswers createActivityAnswers(final Map attributeValues)
	{
		return createActivityAnswers( getSession().getSessionContext(), attributeValues );
	}
	
	public ActivityQuestions createActivityQuestions(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( HappybotConstants.TC.ACTIVITYQUESTIONS );
			return (ActivityQuestions)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ActivityQuestions : "+e.getMessage(), 0 );
		}
	}
	
	public ActivityQuestions createActivityQuestions(final Map attributeValues)
	{
		return createActivityQuestions( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return HappybotConstants.EXTENSIONNAME;
	}
	
}
