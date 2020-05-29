/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.facades.coupon.impl;


import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.couponservices.model.AbstractCouponModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.chatbot1905.core.coupon.ChatbotCouponService;
import org.chatbot1905.facades.coupon.ChatbotCouponFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;



public class DefaultChatbotCouponFacade implements ChatbotCouponFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultChatbotCouponFacade.class);

	@Autowired
	private ChatbotCouponService chatbotCouponService;
	private Converter<AbstractCouponModel, CouponData> couponConverter;


	@Override
	public List<CouponData> getAllVouchers()
	{
		final Collection<AbstractCouponModel> voucherModelList = getChatbotCouponService().getAllVouchersForSpinner();
		return getCouponConverter().convertAll(voucherModelList);
	}

	protected ChatbotCouponService getChatbotCouponService()
	{
		return this.chatbotCouponService;
	}

	@Required
	public void setChatbotCouponService(final ChatbotCouponService couponService)
	{
		this.chatbotCouponService = chatbotCouponService;
	}

	protected Converter<AbstractCouponModel, CouponData> getCouponConverter()
	{
		return this.couponConverter;
	}

	@Required
	public void setCouponConverter(final Converter<AbstractCouponModel, CouponData> couponConverter)
	{
		this.couponConverter = couponConverter;
	}

}
