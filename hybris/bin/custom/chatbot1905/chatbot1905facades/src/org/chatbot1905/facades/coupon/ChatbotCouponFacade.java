/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.facades.coupon;

import de.hybris.platform.commercefacades.coupon.data.CouponData;

import java.util.List;


/**
 * Voucher facade interface. Manages applying vouchers to cart and releasing it.
 */
public interface ChatbotCouponFacade
{

	List<CouponData> getAllVouchers();

}
