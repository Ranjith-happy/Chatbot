/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.core.coupon;

import de.hybris.platform.couponservices.model.AbstractCouponModel;

import java.util.Collection;


public interface ChatbotCouponService
{

	Collection<AbstractCouponModel> getAllVouchersForSpinner();

}
