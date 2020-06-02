/**
 *
 */
package org.chatbot1905.core.livechat.dao.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chatbot1905.core.livechat.dao.LiveChatDao;


/**
 * @author Pooja
 *
 */
public class DefaultLiveChatDao implements LiveChatDao
{

	private FlexibleSearchService flexibleSearchService;

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	private static final String CUSTOMER_ActiveList = "SELECT {pk} FROM {user} where {isCurrentlyActive}=1 and {user.uid} !=?uid ";

	@Override
	public List<UserModel> getActiveCustomerList(final String uid)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(CUSTOMER_ActiveList);
		params.put("uid", uid);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		final SearchResult<UserModel> result = flexibleSearchService.search(query);
		final List<UserModel> propList = new ArrayList<>(result.getResult());
		return propList;
	}

}
