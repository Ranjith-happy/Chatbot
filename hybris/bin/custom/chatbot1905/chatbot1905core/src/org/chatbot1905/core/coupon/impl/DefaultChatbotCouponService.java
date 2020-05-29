/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.core.coupon.impl;

import de.hybris.platform.couponservices.model.AbstractCouponModel;
import de.hybris.platform.couponservices.model.SingleCodeCouponModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.chatbot1905.core.coupon.ChatbotCouponService;
import org.springframework.beans.factory.annotation.Required;


/**
 * Default implementation of {@link ChatbotCouponService}.
 */
public class DefaultChatbotCouponService implements ChatbotCouponService
{

	private FlexibleSearchService flexibleSearchService;
	private static int COUPON_ACTIVE_ID = 1;

	@Override
	public Collection<AbstractCouponModel> getAllVouchersForSpinner()
	{

		List<AbstractCouponModel> matchedCoupons = new ArrayList<>();

		final StringBuilder buf = new StringBuilder();

		buf.append("SELECT  {p:" + SingleCodeCouponModel.PK + "} ");
		buf.append("FROM {" + SingleCodeCouponModel._TYPECODE + " as p } ");
		buf.append("WHERE {p:" + AbstractCouponModel.ACTIVE + "} = " + COUPON_ACTIVE_ID);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(buf.toString());
		matchedCoupons = getFlexibleSearchService().<AbstractCouponModel> search(query).getResult();

		return matchedCoupons;
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
